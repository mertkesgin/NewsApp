package com.example.newsapp.ui.details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.R;
import com.example.newsapp.data.local.ArticleDatabase;
import com.example.newsapp.models.Article;
import com.example.newsapp.repository.NewsRepository;
import com.example.newsapp.ui.NewsViewModel;
import com.example.newsapp.ui.NewsViewModelProviderFactory;
import com.example.newsapp.utils.Utils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class DetailsFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener {

    private View view;
    private NewsViewModel newsViewModel;
    private NewsViewModelProviderFactory viewModelProviderFactory;
    private NewsRepository newsRepository;

    private Article article;

    private Toolbar toolbar;
    private TextView detailTitle, detailAuthor, detailTime,detailPublisAt,detailToolbarTitle;
    private ImageView detailImage,img_save;
    private ProgressBar detail_progressBar;

    private WebView webView;

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private boolean isHideToolbarView = false;
    private LinearLayout titleAppBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details, container, false);
        initViews();
        initToolbar();
        return view;
    }

    private void initToolbar() {
        toolbar = view.findViewById(R.id.detailToolbar);
        detailToolbarTitle = view.findViewById(R.id.detailToolbarTitle);
        collapsingToolbarLayout = view.findViewById(R.id.collapsingToolbarLayout);
        appBarLayout = view.findViewById(R.id.appbar);
        titleAppBar = view.findViewById(R.id.title_appbar);

        collapsingToolbarLayout.setTitle("");
        appBarLayout.addOnOffsetChangedListener(this);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).setTitle("");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews() {
        detailTitle = view.findViewById(R.id.detailTitle);
        detailTime = view.findViewById(R.id.detailTime);
        detailPublisAt = view.findViewById(R.id.detailPublishedAt);
        detailAuthor = view.findViewById(R.id.detailAuthor);
        detailImage = view.findViewById(R.id.detailImage);
        detail_progressBar = view.findViewById(R.id.detail_progress_bar);
        webView = view.findViewById(R.id.webView);
        img_save = view.findViewById(R.id.img_save);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        article = DetailsFragmentArgs.fromBundle(getArguments()).getArticle();
        setNewDetails();
        setupViewModel();
        setupSaveArtcile();
    }

    private void setupSaveArtcile() {
        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsViewModel.addArticle(article);
                Toast.makeText(getContext(), "Article has been saved.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setNewDetails() {
        detailTitle.setText(article.getTitle());
        detailToolbarTitle.setText(article.getTitle());
        detailAuthor.setText(article.getAuthor());
        detailTime.setText(Utils.DateToTimeFormat(article.getPublishedAt()));
        detailPublisAt.setText(Utils.DateFormat(article.getPublishedAt()));
        Picasso.get().load(article.getUrlToImage()).into(detailImage, new Callback() {
            @Override
            public void onSuccess() {
                detail_progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        initWebView(article.getUrl());
    }

    private void initWebView(String url) {
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        detail_progressBar.setVisibility(View.INVISIBLE);
    }

    private void setupViewModel() {
        newsRepository = new NewsRepository(ArticleDatabase.getInstance(getContext()));
        viewModelProviderFactory = new NewsViewModelProviderFactory(newsRepository);
        newsViewModel = new ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel.class);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        if (percentage == 1f && isHideToolbarView) {
            titleAppBar.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;

        } else if (percentage < 1f && !isHideToolbarView) {
            titleAppBar.setVisibility(View.INVISIBLE);
            isHideToolbarView = !isHideToolbarView;
        }
    }
}