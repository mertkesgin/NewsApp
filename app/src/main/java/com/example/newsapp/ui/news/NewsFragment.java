package com.example.newsapp.ui.news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapp.R;
import com.example.newsapp.adapter.NewsAdapter;
import com.example.newsapp.data.local.ArticleDatabase;
import com.example.newsapp.repository.NewsRepository;
import com.example.newsapp.ui.NewsViewModel;
import com.example.newsapp.ui.NewsViewModelProviderFactory;


public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;
    private NewsViewModelProviderFactory viewModelProviderFactory;
    private NewsRepository newsRepository;

    private View view;
    private RecyclerView rvArticle;
    private NewsAdapter newsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        rvArticle = view.findViewById(R.id.rvArticle);
        rvArticle.setHasFixedSize(true);
        rvArticle.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
        setupNewsObserver();
    }

    private void setupNewsObserver() {
        newsViewModel.getNews().observe(getActivity(),news -> {
            newsAdapter = new NewsAdapter(news.getArticle(),getActivity());
            rvArticle.setAdapter(newsAdapter);
        });
    }

    private void setupViewModel() {
        newsRepository = new NewsRepository(ArticleDatabase.getInstance(getContext()));
        viewModelProviderFactory = new NewsViewModelProviderFactory(newsRepository);
        newsViewModel = new ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel.class);
    }
}