package com.example.foryou.retrofit.retrofitmodel;

import com.google.gson.annotations.SerializedName;

public class SignupCallbackResponseModel {
//    @SerializedName("data")
//    private ArrayList<SignupCallbackResponseModel>  signupCallbackResponseModelArrayList;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("_id")
    private String userID;

    @SerializedName("__v")
    private String __v;

    public SignupCallbackResponseModel(String name, String email, String password, String userID, String __v) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userID = userID;
        this.__v = __v;
    }

//    public SignupCallbackResponseModel(ArrayList<SignupCallbackResponseModel> signupCallbackResponseModelArrayList){
//        this.signupCallbackResponseModelArrayList=signupCallbackResponseModelArrayList;
//    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserID() {
        return userID;
    }

    public String get__v() {
        return __v;
    }
}
