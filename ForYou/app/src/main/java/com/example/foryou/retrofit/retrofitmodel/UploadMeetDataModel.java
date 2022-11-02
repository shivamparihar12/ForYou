package com.example.foryou.retrofit.retrofitmodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UploadMeetDataModel {
    @SerializedName("array")
    private ArrayList<String> arrayList;

    @SerializedName("id")
    private String userID;

    @SerializedName("date")
    private String date;

    @SerializedName("title")
    private String title;

    public UploadMeetDataModel(ArrayList<String> arrayList, String userID, String date, String title) {
        this.arrayList = arrayList;
        this.userID = userID;
        this.date = date;
        this.title = title;
    }

}
