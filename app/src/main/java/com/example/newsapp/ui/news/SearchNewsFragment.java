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
import android.widget.EditText;
import android.widget.ImageView;

import com.example.newsapp.R;
import com.example.newsapp.adapter.SearchNewsAdapter;
import com.example.newsapp.data.local.ArticleDatabase;
import com.example.newsapp.repository.NewsRepository;
import com.example.newsapp.ui.NewsViewModel;
import com.example.newsapp.ui.NewsViewModelProviderFactory;


public class SearchNewsFragment extends Fragment {

    private View view;

    private NewsViewModel newsViewModel;
    private NewsViewModelProviderFactory viewModelProviderFactory;
    private NewsRepository newsRepository;

    private RecyclerView rvSearchNews;
    private SearchNewsAdapter adapter;
    private EditText etSearch;
    private ImageView btnSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_news, container, false);
        initViews();
        return view;
    }

    private void initViews() {
        etSearch = view.findViewById(R.id.et_search);
        btnSearch = view.findViewById(R.id.btn_search);
        rvSearchNews = view.findViewById(R.id.rvSearchNews);
        rvSearchNews.setHasFixedSize(true);
        rvSearchNews.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
        searchNews();
    }

    private void searchNews(){
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = etSearch.getText().toString().trim();
                if (!searchQuery.isEmpty()){
                    newsViewModel.getSearchNews(searchQuery).observe(getActivity(),news -> {
                        adapter = new SearchNewsAdapter(news.getArticle(),getActivity());
                        rvSearchNews.setAdapter(adapter);
                    });
                }
            }
        });
    }

    private void setupViewModel() {
        newsRepository = new NewsRepository(ArticleDatabase.getInstance(getContext()));
        viewModelProviderFactory = new NewsViewModelProviderFactory(newsRepository);
        newsViewModel = new ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel.class);
    }
}