package com.example.newsapp.data.remote;

import com.example.newsapp.models.News;


import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("top-headlines")
    Single<News> getNews(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Single<News> getNewsSearch(
            @Query("q") String keyword,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey
    );
}
