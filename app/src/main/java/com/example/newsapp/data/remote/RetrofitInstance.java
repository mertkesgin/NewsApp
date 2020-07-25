package com.example.newsapp.data.remote;

import com.example.newsapp.utils.Utils;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.newsapp.utils.Constant.BASE_URL;

public class RetrofitInstance {

    private static RetrofitInstance apiClient;
    private static Retrofit retrofit;

    public RetrofitInstance() {
        if (retrofit ==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
    }

    public static synchronized RetrofitInstance getInstance(){
        if (apiClient == null){
            apiClient = new RetrofitInstance();
        }
        return apiClient;
    }

    public ApiService getApi(){
        return retrofit.create(ApiService.class);
    }
}
