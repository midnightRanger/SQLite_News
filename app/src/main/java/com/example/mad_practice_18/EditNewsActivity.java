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
public class EditNewsActivity extends AppCompatActivity {

    DatabaseHelper database;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_news);

        database = new DatabaseHelper(this);
        Button saveChanges;
        NewsModel news = getIntent().getParcelableExtra("news_to_edit");
        user = getIntent().getParcelableExtra("author");
        EditText header = findViewById(R.id.header_et);
        EditText text = findViewById(R.id.main_text_et);
        TextView date = findViewById(R.id.date_tv);
        TextView currentAuthor = findViewById(R.id.current_author_tv);
        TextView nextAuthor = findViewById(R.id.author_tv);
        saveChanges = findViewById(R.id.save_changes_btn);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd.MMM.yyyy", Locale.getDefault());
        String changesDate = df.format(c);

        header.setText(news.Header);
        text.setText(news.MainText);
        date.setText(String.format("Дата публикации/изменения: %s", news.Date));
        currentAuthor.setText(String.format("Было: %s", news.Author));
        nextAuthor.setText(String.format("Станет: %s", user.Login));

        saveChanges.setOnClickListener(v -> {
            String h = header.getText().toString().trim();
            String t = text.getText().toString().trim();
            if (h.isEmpty() || t.isEmpty()) {
                Toast.makeText(this, "Необходимо заполнить все поля",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            news.Header = h;
            news.MainText = t;
            news.Date = changesDate;
            news.Author = user.Login;
            database.updateNews(news);

            Intent intent = new Intent(this, AdminNewsActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, NewsInfoActivity.class);
        startActivity(intent);
        finish();
    }
}