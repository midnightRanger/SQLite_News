package com.example.mad_practice_18;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReaderNewsActivity extends AppCompatActivity implements RecyclerNewsAdapter.NewsOnClickListener {

    DatabaseHelper database;
    RecyclerView newsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_news);
        database = new DatabaseHelper(this);

        ArrayList<NewsModel> newsList = database.getAllNewsList();

        newsRecycler = findViewById(R.id.news_list_rv);
        newsRecycler.setAdapter(new RecyclerNewsAdapter(getApplicationContext(), newsList, this));
    }

    @Override
    public void onClick(int position) {
        //doing nothing
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AuthorizationActivity.class);
        startActivity(intent);
        finish();
    }
}