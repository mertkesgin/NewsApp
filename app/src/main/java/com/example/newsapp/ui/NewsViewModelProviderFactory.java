package com.example.newsapp.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.newsapp.repository.NewsRepository;

public class NewsViewModelProviderFactory implements ViewModelProvider.Factory {

    private final NewsRepository newsRepository;

    public NewsViewModelProviderFactory(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NewsViewModel(newsRepository);
    }
}
