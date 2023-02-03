package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.CategoryClickInterface {

    RecyclerView newsRV, categoryRV;
    ProgressBar progress;
    ArrayList<Articles> articlesArrayList;
    ArrayList<CategoryData> categoryRVModelArrayList;
    CategoryAdapter categoryRVAdapter;
    NewsAdapter newsRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRV = findViewById(R.id.rvNews);
        categoryRV = findViewById(R.id.rvCategory);
        progress = findViewById(R.id.progressLoading);

        articlesArrayList = new ArrayList<>();
        categoryRVModelArrayList = new ArrayList<>();
        newsRVAdapter = new NewsAdapter(articlesArrayList, this);
        categoryRVAdapter = new CategoryAdapter(categoryRVModelArrayList, this, this::onCategoryClick);

        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        getCategory();
    }

    private void getCategory(){
        categoryRVModelArrayList.add(new CategoryData("All", "https://images.unsplash.com/photo-1432821596592-e2c18b78144f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8R2VuZXJhbHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryData("Technology", "https://images.unsplash.com/photo-1550751827-4bd374c3f58b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fFRlY2hub2xvZ3l8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryData("Science", "https://images.unsplash.com/photo-1614935151651-0bea6508db6b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTF8fFNjaWVuY2V8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryData("Sports", "https://images.unsplash.com/photo-1461896836934-ffe607ba8211?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8U3BvcnRzfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryData("General", "https://images.unsplash.com/photo-1512314889357-e157c22f938d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8R2VuZXJhbHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryData("Business", "https://images.unsplash.com/photo-1665686306574-1ace09918530?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fEJ1c2luZXNzfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryData("Entertainment", "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8RW50ZXJ0YWlubWVudHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryData("Health", "https://images.unsplash.com/photo-1532938911079-1b06ac7ceec7?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fEhlYWx0aHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVAdapter.notifyDataSetChanged();
        getNews("All");
        newsRVAdapter.notifyDataSetChanged();
    }

    private void getNews(String category){
        progress.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=sa&category=" + category + "&apiKey=68c3115736714eb28ec7cc28a9406ae0";
        String url ="https://newsapi.org/v2/top-headlines?country=sa&apiKey=68c3115736714eb28ec7cc28a9406ae0";
        String baseURL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsDetails> call;
        if(category.equals("All")){
            call = retrofitAPI.getAllNews(url);
        }else{
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsDetails>() {
            @Override
            public void onResponse(Call<NewsDetails> call, Response<NewsDetails> response) {
                NewsDetails newsModel = response.body();
                progress.setVisibility(View.INVISIBLE);
                ArrayList<Articles> articles = newsModel.getArticles();
                for(int i=0; i<articles.size(); i++){
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(), articles.get(i).getDescription(),
                            articles.get(i).getUrlToImage(), articles.get(i).getUrl(), articles.get(i).getContent()));
                }
                newsRVAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<NewsDetails> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail to get news", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModelArrayList.get(position).getCategory();
        getNews(category);
    }
}