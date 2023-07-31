package com.example.foryou.retrofit.retrofitmodel;

import com.google.gson.annotations.SerializedName;

public class UserSignUpModel {
    public UserSignUpModel(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

}
