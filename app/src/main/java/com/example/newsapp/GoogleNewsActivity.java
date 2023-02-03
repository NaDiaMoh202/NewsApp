package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class GoogleNewsActivity extends AppCompatActivity {

    WebView webView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_news);

        webView1 = findViewById(R.id.webView1);

        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.getSettings().setDomStorageEnabled(true);
        webView1.loadUrl("https://news.google.com/topics/CAAqJggKIiBDQkFTRWdvSUwyMHZNRFZxYUdjU0FtVnVHZ0pWVXlnQVAB?hl=en-US&gl=US&ceid=US%3Aen");

    }
}