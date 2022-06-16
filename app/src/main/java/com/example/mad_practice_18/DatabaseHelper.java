package com.example.mad_practice_18;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "news", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createAll(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        reinitialize(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int i, int i1) {
        reinitialize(db);
    }

    private void createAll(SQLiteDatabase db) {
        db.execSQL("create table Users(login primary key, password, is_admin)");
        db.execSQL("create table News(_id integer primary key, header, release_date, main_text, author);");
        db.execSQL("insert into Users(login, password, is_admin) values ('admin', 'admin', 1);");
    }

    private void dropAll(SQLiteDatabase db) {
        db.execSQL("drop Table if exists News;");
        db.execSQL("drop Table if exists Users;");
    }

    private void reinitialize(SQLiteDatabase db) {
        dropAll(db);
        createAll(db);
    }

    public void addNews(String header, String text, String release_date, String author) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("header", header);
        values.put("release_date", release_date);
        values.put("main_text", text);
        values.put("author", author);
        db.insert("News", null, values);
    }

    public ArrayList<NewsModel> getAllNewsList() {
        ArrayList<NewsModel> results = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Select * from News;";
        Cursor data = db.rawQuery(sql, null);
        while (data.moveToNext()) {
            int _id = data.getInt(0);
            String header = data.getString(1);
            String release_date = data.getString(2);
            String text = data.getString(3);
            String author = data.getString(4);

            NewsModel model = new NewsModel(_id, header, release_date, text, author);
            results.add(model);
        }
        data.close();
        return results;
    }

    public UserModel findUser(String login, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Select * from Users;";
        Cursor data = db.rawQuery(sql, null);
        while (data.moveToNext()) {
            String s1 = data.getString(0);
            String s2 = data.getString(1);
            if (login.equals(s1) && password.equals(s2)) {
                boolean isAdmin = data.getInt(2) == 1;
                return new UserModel(s1, s2, isAdmin);
            }
        }
        data.close();
        return null;
    }

    public void addUser(String login, String password, boolean isAdmin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("login", login);
        values.put("password", password);
        values.put("is_admin", isAdmin);
        db.insert("Users", null, values);
    }

    public void updateNews(NewsModel news) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("header", news.Header);
        cv.put("main_text", news.MainText);
        cv.put("release_date", news.Date);
        cv.put("author", news.Author);
        db.update("News", cv, "_id = " + news.Id, null);
    }

    public void deleteNews(NewsModel news) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("News", "_id = " + news.Id, null);
    }
}