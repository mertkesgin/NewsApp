package com.example.newsapp.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapp.models.Article;
import com.example.newsapp.models.News;
import com.example.newsapp.repository.NewsRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class NewsViewModel extends ViewModel {

    private NewsRepository newsRepository;
    private CompositeDisposable disposable;
    private ExecutorService executors;

    private final MutableLiveData<News> _news = new MutableLiveData<>();
    private final MutableLiveData<News> _searchNew = new MutableLiveData<>();
    private final MutableLiveData<List<Article>> _savedNews = new MutableLiveData<>();

    public NewsViewModel(NewsRepository newsRepository){
        this.newsRepository = newsRepository;
        disposable = new CompositeDisposable();
        executors = Executors.newFixedThreadPool(2);
    }

    public LiveData<News> getNews() {
        disposable.add(newsRepository.getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<News>() {
                    @Override
                    public void onSuccess(News news) {
                        _news.postValue(news);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));

        return _news;
    }

    public LiveData<News> getSearchNews(String searchQuery) {
        disposable.add(newsRepository.getNewsSearch(searchQuery)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<News>() {
            @Override
            public void onSuccess(News news) {
                _searchNew.postValue(news);
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
        return _searchNew;
    }

    public LiveData<List<Article>> getSavedArticles(){
        disposable.add(newsRepository.getArticles()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<Article>>() {
            @Override
            public void accept(List<Article> articles) throws Exception {
                _savedNews.postValue(articles);
            }
        }));
        return _savedNews;
    }

    public void addArticle(Article article){
        executors.execute(() -> {
            newsRepository.insertArticle(article);
        });
    }

    public void deleteArticle(Article article){
        executors.execute(() -> {
            newsRepository.deleteArticle(article);
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }
}
