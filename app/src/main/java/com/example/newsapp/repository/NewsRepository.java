package com.example.newsapp.repository;

import com.example.newsapp.data.local.ArticleDatabase;
import com.example.newsapp.models.Article;
import com.example.newsapp.models.News;
import com.example.newsapp.data.remote.RetrofitInstance;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

import static com.example.newsapp.utils.Constant.API_KEY;
import static com.example.newsapp.utils.Constant.COUNTRY;
import static com.example.newsapp.utils.Constant.SORT_BY;

public class NewsRepository {

    private ArticleDatabase database;


    public NewsRepository(ArticleDatabase database) {
        this.database = database;
    }

    public Single<News> getNews(){
        return RetrofitInstance.getInstance().getApi().getNews(COUNTRY,API_KEY);
    }

    public Single<News> getNewsSearch(String searchQuery){
        return RetrofitInstance.getInstance().getApi().getNewsSearch(searchQuery,COUNTRY,SORT_BY,API_KEY);
    }

    public Flowable<List<Article>> getArticles(){
        return database.articleDao().getAllArticles();
    }

    public void insertArticle(Article article){
        database.articleDao().insert(article);
    }

    public void deleteArticle(Article article){
        database.articleDao().delete(article);
    }
}
