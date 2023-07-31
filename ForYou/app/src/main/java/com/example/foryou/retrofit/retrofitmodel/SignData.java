package com.example.foryou.retrofit.retrofitmodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SignData {
    @SerializedName("data")
    private ArrayList<SignupCallbackResponseModel> signupCallbackResponseModelList;

    public ArrayList<SignupCallbackResponseModel> getSignupCallbackResponseModelList() {
        return signupCallbackResponseModelList;
    }

    public SignData(ArrayList<SignupCallbackResponseModel> signupCallbackResponseModelList) {
        this.signupCallbackResponseModelList=signupCallbackResponseModelList;
    }
}
