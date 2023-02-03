package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {

    String title, desc, content, imageURL, url;
    TextView txtTitle, txtSubDesc, txtContent;
    ImageView imgNews;
    Button btnRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        Intent in = getIntent();
        title = in.getStringExtra("title");
        desc = in.getStringExtra("desc");
        content = in.getStringExtra("content");
        imageURL = in.getStringExtra("image");
        url = in.getStringExtra("url");

        txtTitle = findViewById(R.id.idTVTitle);
        txtSubDesc = findViewById(R.id.idTVSubDesc);
        txtContent = findViewById(R.id.idTVContent);
        imgNews = findViewById(R.id.idIVNews);
        btnRead = findViewById(R.id.idButtonNews);

        txtTitle.setText(title);
        txtSubDesc.setText(desc);
        txtContent.setText(content);
        Picasso.get().load(imageURL).into(imgNews);

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


    }
}