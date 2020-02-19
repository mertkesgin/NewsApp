package com.example.newsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.newsapp.R;
import com.example.newsapp.Utils.Utils;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private Toolbar toolbar;
    private TextView detailTitle, detailAuthor, detailTime,detailPublisAt,detailToolbarTitle;
    private ImageView detailImage;
    private ProgressBar detail_progressBar;

    private WebView webView;

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private boolean isHideToolbarView = false;
    private LinearLayout titleAppBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        initViews();
        initToolbar();
        setDetailInformation();
    }

    private void setDetailInformation() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");
        String date = intent.getStringExtra("date");
        String url = intent.getStringExtra("url");
        String urlToImage = intent.getStringExtra("urlToImage");

        detailTitle.setText(title);
        detailToolbarTitle.setText(title);
        detailAuthor.setText(author);
        detailTime.setText(Utils.DateToTimeFormat(date));
        detailPublisAt.setText(Utils.DateFormat(date));

        Picasso.get().load(urlToImage).into(detailImage, new Callback() {
            @Override
            public void onSuccess() {
                detail_progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });

        initWebView(url);
    }

    private void initToolbar() {

        toolbar = findViewById(R.id.detailToolbar);
        detailToolbarTitle = findViewById(R.id.detailToolbarTitle);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
        appBarLayout = findViewById(R.id.appbar);
        titleAppBar = findViewById(R.id.title_appbar);

        collapsingToolbarLayout.setTitle("");

        appBarLayout.addOnOffsetChangedListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initViews() {

        detailTitle = findViewById(R.id.detailTitle);
        detailTime = findViewById(R.id.detailTime);
        detailPublisAt = findViewById(R.id.detailPublishedAt);
        detailAuthor = findViewById(R.id.detailAuthor);
        detailImage = findViewById(R.id.detailImage);
        detail_progressBar = findViewById(R.id.detail_progress_bar);
        webView = findViewById(R.id.webView);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
