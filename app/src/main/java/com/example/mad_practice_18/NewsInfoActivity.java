package com.example.mad_practice_18;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class NewsInfoActivity extends AppCompatActivity {

    DatabaseHelper database;
    NewsModel news;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_info);

        database = new DatabaseHelper(this);
        TextView header = findViewById(R.id.header_tv);
        TextView mainText = findViewById(R.id.main_text_tv);
        TextView date = findViewById(R.id.date_tv);
        TextView author = findViewById(R.id.author_tv);
        Button edit = findViewById(R.id.edit_btn);
        Button delete = findViewById(R.id.delete_btn);
        news = getIntent().getParcelableExtra("news_info");
        user = getIntent().getParcelableExtra("author");

        header.setText(news.Header);
        mainText.setText(news.MainText);
        date.setText(news.Date);
        author.setText(news.Author);

        edit.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditNewsActivity.class);
            intent.putExtra("news_to_edit", news);
            intent.putExtra("author", user);
            startActivity(intent);
            finish();
        });

        delete.setOnClickListener(view -> {
            database.deleteNews(news);
            Intent intent = new Intent(this, AdminNewsActivity.class);
            startActivity(intent);
            finish();
        });
    }
}