package com.example.foryou.retrofit.retrofitmodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserMeetDataList {
    @SerializedName("data")
    private ArrayList<UserMeetData> userMeetDataArrayList;

    public ArrayList<UserMeetData> getUserMeetDataArrayList() {
        return userMeetDataArrayList;
    }

    public UserMeetDataList(ArrayList<UserMeetData> userMeetDataArrayList) {
        this.userMeetDataArrayList = userMeetDataArrayList;
    }
}
