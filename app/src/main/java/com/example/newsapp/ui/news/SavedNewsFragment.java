package com.example.newsapp.ui.news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapp.R;
import com.example.newsapp.adapter.NewsAdapter;
import com.example.newsapp.adapter.SavedNewsAdapter;
import com.example.newsapp.data.local.ArticleDatabase;
import com.example.newsapp.models.Article;
import com.example.newsapp.repository.NewsRepository;
import com.example.newsapp.ui.NewsViewModel;
import com.example.newsapp.ui.NewsViewModelProviderFactory;

import java.util.List;

public class SavedNewsFragment extends Fragment {

    private NewsViewModel newsViewModel;
    private NewsViewModelProviderFactory viewModelProviderFactory;
    private NewsRepository newsRepository;

    private View view;
    private RecyclerView rvArticle;
    private SavedNewsAdapter savedNewsAdapter;
    private List<Article> articleList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_saved_news, container, false);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        rvArticle = view.findViewById(R.id.rvSavedNews);
        rvArticle.setHasFixedSize(true);
        rvArticle.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
        setupSavedNewsObserver();
        setupItemTouchHelper();
    }

    private void setupItemTouchHelper() {
        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                newsViewModel.deleteArticle(articleList.get(position));
                savedNewsAdapter.notifyDataSetChanged();
            }
        });
        touchHelper.attachToRecyclerView(rvArticle);
    }

    private void setupSavedNewsObserver() {
        newsViewModel.getSavedArticles().observe(getActivity(),articles -> {
            articleList = articles;
            savedNewsAdapter = new SavedNewsAdapter(articleList,getActivity());
            rvArticle.setAdapter(savedNewsAdapter);
        });
    }

    private void setupViewModel() {
        newsRepository = new NewsRepository(ArticleDatabase.getInstance(getContext()));
        viewModelProviderFactory = new NewsViewModelProviderFactory(newsRepository);
        newsViewModel = new ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel.class);
    }
}