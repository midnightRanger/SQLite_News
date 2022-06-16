package com.example.mad_practice_18;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class UserModel implements Parcelable {
    public String Login;
    public String Password;
    public Boolean IsAdmin;

    public UserModel(String login, String password, Boolean isAdmin) {
        Login = login;
        Password = password;
        IsAdmin = isAdmin;
    }

    protected UserModel(Parcel in) {
        Login = in.readString();
        Password = in.readString();
        IsAdmin = in.readByte() != 0;
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Login);
        parcel.writeString(Password);
        parcel.writeByte((byte) (IsAdmin ? 1 : 0));
    }
}
