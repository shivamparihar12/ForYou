package com.example.foryou.retrofit.retrofitmodel;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.versionedparcelable.ParcelField;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class UserMeetData implements Parcelable {
    @SerializedName("_id")
    private String _id;

    @SerializedName("array")
    private ArrayList<String> emoList;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("date")
    private String date;

    @SerializedName("title")
    private String title;

    @SerializedName("__v")
    private String __v;

    protected UserMeetData(Parcel in) {
        _id = in.readString();
        emoList = in.createStringArrayList();
        user_id = in.readString();
        date = in.readString();
        title = in.readString();
        __v = in.readString();
    }

    public static final Creator<UserMeetData> CREATOR = new Creator<UserMeetData>() {
        @Override
        public UserMeetData createFromParcel(Parcel in) {
            return new UserMeetData(in);
        }

        @Override
        public UserMeetData[] newArray(int size) {
            return new UserMeetData[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public ArrayList<String> getEmoList() {
        return emoList;
    }

    public String getUser_id() {
        return user_id;
    }

    public String get__v() {
        return __v;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public UserMeetData(String _id, ArrayList<String> emoList, String user_id, String date, String title, String __v) {
        this._id = _id;
        this.emoList = emoList;
        this.user_id = user_id;
        this.date = date;
        this.title = title;
        this.__v = __v;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeStringList(emoList);
        parcel.writeString(user_id);
        parcel.writeString(date);
        parcel.writeString(title);
        parcel.writeString(__v);
    }
}
