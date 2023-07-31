package com.example.foryou.retrofit.retrofitmodel;

import com.google.gson.annotations.SerializedName;

public class UserID {
    @SerializedName("id")
    private String user_id;

    public UserID(String user_id) {
        this.user_id = user_id;
    }
}
