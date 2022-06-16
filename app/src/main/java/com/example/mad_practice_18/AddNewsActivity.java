package com.example.mad_practice_18;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class AddNewsActivity extends AppCompatActivity {

    DatabaseHelper database;
    String publicationDate;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        user = getIntent().getParcelableExtra("author");
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd.MMM.yyyy", Locale.getDefault());
        publicationDate = df.format(c);

        database = new DatabaseHelper(this);
        TextView date = findViewById(R.id.date_tv);
        TextView author = findViewById(R.id.author_tv);
        Button addNews = findViewById(R.id.add_news_btn);

        addNews.setOnClickListener(v -> addNews());
        date.setText(publicationDate);
        author.setText(user.Login);
    }

    public void addNews() {
        EditText header = findViewById(R.id.header_et);
        EditText text = findViewById(R.id.main_text_et);

        String h = header.getText().toString().trim();
        String t = text.getText().toString().trim();
        if (h.isEmpty() || t.isEmpty()) {
            Toast.makeText(this, "Необходимо заполнить все поля",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        database.addNews(h, t, publicationDate, user.Login);
        Intent intent = new Intent(this, AdminNewsActivity.class);
        startActivity(intent);
        finish();
    }
}