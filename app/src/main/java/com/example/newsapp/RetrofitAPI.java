package com.example.newsapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPI {
    @GET
    Call<NewsDetails> getAllNews(@Url String url);

    @GET
    Call<NewsDetails> getNewsByCategory(@Url String url);
}
