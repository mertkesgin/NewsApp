package com.example.newsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.newsapp.Adapter.Adapter;
import com.example.newsapp.Api.ApiClient;
import com.example.newsapp.Models.Article;
import com.example.newsapp.Models.News;
import com.example.newsapp.R;
import com.example.newsapp.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Article> articleList = new ArrayList<>();

    private Context mContext = MainActivity.this;

    private EditText editTextSearch;
    private ImageView btnSearch;

    public static final String API_KEY = "ba43c5c3d37a46c1a9af727a5af41134";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initRecyclerView();

        initViews();

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson("");
            }
        });

        retrieveJson("");
    }

    private void initViews() {
        editTextSearch = findViewById(R.id.editTextSearch);
        btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveJson(editTextSearch.getText().toString());
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

    }

    public void retrieveJson(String query){

        String country = Utils.getCountry();

        swipeRefreshLayout.setRefreshing(true);

        Call<News> call;
        if (query.length() > 0){
            call = ApiClient.getInstance().getApi().getNewsSearch(query,country,"publishAt",API_KEY);
        }else {
            call = ApiClient.getInstance().getApi().getNews(country,API_KEY);
        }
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                swipeRefreshLayout.setRefreshing(false);
                articleList.clear();
                articleList = response.body().getArticle();
                adapter = new Adapter(articleList,mContext);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(mContext,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
