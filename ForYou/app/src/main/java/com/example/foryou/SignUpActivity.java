package com.example.foryou;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foryou.databinding.ActivitySignUpBinding;
import com.example.foryou.retrofit.RetrofitClient;
import com.example.foryou.retrofit.retrofitmodel.SignData;
import com.example.foryou.retrofit.retrofitmodel.SignInRequestModel;
import com.example.foryou.retrofit.retrofitmodel.SignupCallbackResponseModel;
import com.example.foryou.retrofit.retrofitmodel.UserSignUpModel;
import com.example.foryou.utility.OnSwipeTouchListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signupLayout.setVisibility(View.VISIBLE);
        binding.signinLayout.setVisibility(View.GONE);
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.etName.getText().toString();
                String email = binding.etEmail.getText().toString();
                String password = binding.etPassword.getText().toString();
                String rePassword = binding.etRepassword.getText().toString();

                if (name.isEmpty())
                    Toast.makeText(SignUpActivity.this, "Name cannot be null", Toast.LENGTH_SHORT).show();
                else if (email.isEmpty())
                    Toast.makeText(SignUpActivity.this, "email cannot be null", Toast.LENGTH_SHORT).show();
                else if (password.isEmpty())
                    Toast.makeText(SignUpActivity.this, "password cannot be null", Toast.LENGTH_SHORT).show();
                else if (!rePassword.equals(password))
                    Toast.makeText(SignUpActivity.this, "password and Re-password doesn't match", Toast.LENGTH_SHORT).show();
                else {
                    name = name.trim();
                    email = email.trim();
                    password = password.trim();

                    UserSignUpModel userSignUpModel = new UserSignUpModel(name, email, password);
                    RegisterUser(userSignUpModel);

                }
            }
        });

        //for registering user
        binding.registerSwipeRight.setOnTouchListener(new OnSwipeTouchListener(SignUpActivity.this) {
            @Override
            public void onSwipeRight() {
                binding.signupLayout.setVisibility(View.VISIBLE);
                binding.signinLayout.setVisibility(View.GONE);
            }
        });
//        binding.registerSwipeRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.signupLayout.setVisibility(View.VISIBLE);
//                binding.signinLayout.setVisibility(View.GONE);
//            }
//        });

        //for signing in
        binding.swipeLeftLogin.setOnTouchListener(new OnSwipeTouchListener(SignUpActivity.this) {
            @Override
            public void onSwipeLeft() {
                binding.signupLayout.setVisibility(View.GONE);
                binding.signinLayout.setVisibility(View.VISIBLE);
            }
        });
//        binding.swipeLeftLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.signupLayout.setVisibility(View.GONE);
//                binding.signinLayout.setVisibility(View.VISIBLE);
//            }
//        });

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.signinEmail.getText().toString();
                String password = binding.signinPassword.getText().toString();
                String rePassword = binding.signinEtRepassword.getText().toString();

                if (email.isEmpty())
                    Toast.makeText(SignUpActivity.this, "email cannot be null", Toast.LENGTH_SHORT).show();
                else if (rePassword.isEmpty() || password.isEmpty())
                    Toast.makeText(SignUpActivity.this, "password cannot be null", Toast.LENGTH_SHORT).show();
                else if (!rePassword.equals(password))
                    Toast.makeText(SignUpActivity.this, "password and Re-password doesn't match", Toast.LENGTH_SHORT).show();
                else {
                    email = email.trim();
                    password = password.trim();
                    rePassword = rePassword.trim();

                    SignInRequestModel signInRequestModel = new SignInRequestModel(email, password);
                    SignInUser(signInRequestModel);
                }
            }
        });


    }

    private void SignInUser(SignInRequestModel signInRequestModel) {
        Call<SignData> signInUser = RetrofitClient.getService().signInUser(signInRequestModel);
        signInUser.enqueue(new Callback<SignData>() {
            @Override
            public void onResponse(Call<SignData> call, Response<SignData> response) {
                SignupCallbackResponseModel signupCallbackResponseModel = new SignupCallbackResponseModel(
                        response.body().getSignupCallbackResponseModelList().get(0).getName(),
                        response.body().getSignupCallbackResponseModelList().get(0).getEmail(),
                        response.body().getSignupCallbackResponseModelList().get(0).getPassword(),
                        response.body().getSignupCallbackResponseModelList().get(0).getUserID(),
                        response.body().getSignupCallbackResponseModelList().get(0).get__v());

                Log.d("USERID", "haha" + response.body().getSignupCallbackResponseModelList().get(0).getUserID());
                saveToSharedPref(response.body().getSignupCallbackResponseModelList().get(0).getUserID());
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            }

            @Override
            public void onFailure(Call<SignData> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                binding.signinEmail.setText("");
                binding.signinPassword.setText("");
                binding.signinEtRepassword.setText("");
            }
        });
//        Call<SignupCallbackResponseModel> signInUser = RetrofitClient.getService().signInUser(signInRequestModel);
//        signInUser.enqueue(new Callback<SignupCallbackResponseModel>() {
//            @Override
//            public void onResponse(Call<SignupCallbackResponseModel> call, Response<SignupCallbackResponseModel> response) {
//                SignupCallbackResponseModel signupCallbackResponseModel = response.body();
//                Log.d("signin log",response.body().getName());
////                saveToSharedPref(signupCallbackResponseModel.getUserID());
//                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
////                onDestroy();
//            }
//
//            @Override
//            public void onFailure(Call<SignupCallbackResponseModel> call, Throwable t) {
//                Toast.makeText(SignUpActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                binding.signinEmail.setText("");
//                binding.signinPassword.setText("");
//                binding.signinEtRepassword.setText("");
//            }
//        });
    }


    private void RegisterUser(UserSignUpModel userSignUpModel) {
        Call<SignupCallbackResponseModel> registerUser = RetrofitClient.getService().registerUser(userSignUpModel);
        registerUser.enqueue(new Callback<SignupCallbackResponseModel>() {
            @Override
            public void onResponse(Call<SignupCallbackResponseModel> call, Response<SignupCallbackResponseModel> response) {
                SignupCallbackResponseModel signupCallbackResponseModel = response.body();
                saveToSharedPref(signupCallbackResponseModel.getUserID());
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            }

            @Override
            public void onFailure(Call<SignupCallbackResponseModel> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.etRepassword.setText("");
                binding.etName.setText("");
                binding.etRepassword.setText("");
                binding.etName.setText("");
            }
        });
//        registerUser.enqueue(new Callback<ArrayList<SignupCallbackResponseModel>>() {
//            @Override
//            public void onResponse(Call<ArrayList<SignupCallbackResponseModel>> call, Response<ArrayList<SignupCallbackResponseModel>> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<SignupCallbackResponseModel>> call, Throwable t) {
//
//            }
//        });
//        registerUser.enqueue(new Callback<SignupCallbackResponseModel>() {
//            @Override
//            public void onResponse(Call<ArrayList<SignupCallbackResponseModel>> call, Response<ArrayList<SignupCallbackResponseModel>> response) {
//                SignupCallbackResponseModel signupCallbackResponseModel = response[0].body();
//                saveToSharedPref(signupCallbackResponseModel.getUserID());
//                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
//            }
//
//            @Override
//            public void onFailure(Call<<SignupCallbackResponseModel>> call, Throwable t) {
//                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                binding.signinEmail.setText("");
//                binding.signinPassword.setText("");
//                binding.etName.setText("");
//            }
//        });

    }

    private void saveToSharedPref(String UserID) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SignUpActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        assert UserID != null;
        editor.putString("USER_KEY", String.valueOf(UserID));
        editor.apply();
    }
}