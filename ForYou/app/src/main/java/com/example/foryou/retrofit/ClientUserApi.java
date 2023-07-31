package com.example.foryou.retrofit;

import android.util.Pair;

import com.example.foryou.retrofit.retrofitmodel.SignData;
import com.example.foryou.retrofit.retrofitmodel.SignInRequestModel;
import com.example.foryou.retrofit.retrofitmodel.SignupCallbackResponseModel;
import com.example.foryou.retrofit.retrofitmodel.UploadMeetDataModel;
import com.example.foryou.retrofit.retrofitmodel.UserID;
import com.example.foryou.retrofit.retrofitmodel.UserMeetData;
import com.example.foryou.retrofit.retrofitmodel.UserMeetDataList;
import com.example.foryou.retrofit.retrofitmodel.UserSignUpModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ClientUserApi {

    @POST("/user/signup")
    Call<SignupCallbackResponseModel> registerUser(@Body UserSignUpModel userSignUpModel);

    @POST("/user/signin")
    Call<SignData> signInUser(@Body SignInRequestModel signInRequestModel);

    @POST("/crud/post-pair")
    Call<Pair<String, String>> uploadMeetData(@Body UploadMeetDataModel uploadMeetDataModel);

    @POST("/crud/get-arrays")
    Call<UserMeetDataList> getUserData(@Body UserID userID);
}
