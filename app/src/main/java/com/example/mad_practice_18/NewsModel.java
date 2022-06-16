package com.example.mad_practice_18;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsModel implements Parcelable {
    public int Id;
    public String Header;
    public String Date;
    public String MainText;
    public String Author;

    public NewsModel(int id, String header, String date, String mainText, String author) {
        Id = id;
        Header = header;
        Date = date;
        MainText = mainText;
        Author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(Id);
        out.writeString(Header);
        out.writeString(MainText);
        out.writeString(Date);
        out.writeString(Author);
    }

    public static final Parcelable.Creator<NewsModel> CREATOR = new Parcelable.Creator<NewsModel>() {
        public NewsModel createFromParcel(Parcel in) {
            return new NewsModel(in);
        }

        public NewsModel[] newArray(int size) {
            return new NewsModel[size];
        }
    };

    private NewsModel(Parcel in) {
        Id = in.readInt();
        Header = in.readString();
        MainText = in.readString();
        Date = in.readString();
        Author = in.readString();
    }
}
