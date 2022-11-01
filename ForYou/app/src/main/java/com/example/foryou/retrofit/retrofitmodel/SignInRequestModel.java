package com.example.foryou.retrofit.retrofitmodel;

import com.google.gson.annotations.SerializedName;

public class SignInRequestModel {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public SignInRequestModel(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
