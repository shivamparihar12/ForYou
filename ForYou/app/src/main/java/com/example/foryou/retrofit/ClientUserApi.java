package com.example.foryou.retrofit;

import com.example.foryou.retrofit.retrofitmodel.SignInRequestModel;
import com.example.foryou.retrofit.retrofitmodel.UserSignUpModel;
import com.example.foryou.retrofit.retrofitmodel.SignupCallbackResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ClientUserApi {

    @POST("/user/signup")
    Call<SignupCallbackResponseModel> registerUser(@Body UserSignUpModel userSignUpModel);

    @POST("/user/signin")
    Call<SignupCallbackResponseModel> signInUser(@Body SignInRequestModel signInRequestModel);
}
