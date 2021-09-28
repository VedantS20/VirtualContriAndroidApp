package com.vedant.virtualcontrife;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage_fragment extends Fragment {
    TextInputLayout UsernameBox, PasswordBox;
    TextInputEditText Username, Password;
    MaterialButton Login, Signup;
    private static final String AUTH = "Basic dmlydHVhbENvbnRyaTpWQ0AwMjEyIQ==";

//    @Override
//    public void onStart() {
//        super.onStart();
//        if(SharedPrefManager.getInstance(getContext()).isLoggedin())
//        {
//            Log.d("TAG", "onStart:  "+SharedPrefManager.getInstance(getContext()).getRefreshToken());
//            Call<ResponseBody> call = RetrofitClient.getInstance(getContext()).getApi().LoginUsingRefreshToken(AUTH ,"refresh_token" ,SharedPrefManager.getInstance(getContext()).getRefreshToken());
//            call.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    if(response.code() == 200)
//                    {
//                        String res = null;
//                        try {
//                            res = response.body().string();
//                            JSONObject jsonObject = new JSONObject(res);
//                            SharedPrefManager.getInstance(getContext()).saveloginres(jsonObject);
//                           getFragmentManager().beginTransaction().replace(R.id.fragment_container , new group_fragment()).commit();
//                        } catch (IOException | JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    else
//                    {
//                        Toast.makeText(getContext() , "Error Login" , Toast.LENGTH_SHORT).show();
//                        try {
//                            Log.d("TAG", "onResponse: "+response.errorBody().string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        if(SharedPrefManager.getInstance(getContext()).isLoggedin())
//        {
//            Log.d("TAG", "onStart:  " + SharedPrefManager.getInstance(getContext()).getRefreshToken());
//            Call<ResponseBody> call = RetrofitClient.getInstance(getContext()).getApi().LoginUsingRefreshToken(AUTH, "refresh_token", SharedPrefManager.getInstance(getContext()).getRefreshToken());
//            call.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    if (response.code() == 200) {
//                        String res = null;
//                        try {
//                            res = response.body().string();
//                            JSONObject jsonObject = new JSONObject(res);
//                            SharedPrefManager.getInstance(getContext()).saveloginres(jsonObject);
//                            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new group_fragment()).commit();
//                        } catch (IOException | JSONException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        Toast.makeText(getContext(), "Error Login", Toast.LENGTH_SHORT).show();
//                        Log.d("TAG", "onResponse: "+SharedPrefManager.getInstance(getContext()).getRefreshToken());
//                        try {
//                            Log.d("TAG", "onResponse: " + response.errorBody().string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.loginmainfragment, container, false);
        MainActivity activity = (MainActivity) getActivity();
        activity.HideButton();
        UsernameBox = v.findViewById(R.id.email_box_login);
        PasswordBox = v.findViewById(R.id.password_box_login);
        Username = v.findViewById(R.id.email_login);
        Password = v.findViewById(R.id.password_login);
        Login = v.findViewById(R.id.login_button);
            Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String User = Username.getText().toString();
                    String Pass = Password.getText().toString();
                    if (User.isEmpty())
                        UsernameBox.setError("Enter Valid Username!");
                    if (Pass.isEmpty())
                        PasswordBox.setError("Enter Valid Password!");
                    final group_fragment grp_frag = new group_fragment();
                    Call<ResponseBody> call = RetrofitClient.getInstance(getContext()).getApi().Login(AUTH, User, Pass, "password");
                    Log.d("TAG", "onClick: " + call.request().toString());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                if (response.code() == 200) {
                                    String res = response.body().string();
                                    JSONObject jsonObject = new JSONObject(res);
                                    SharedPrefManager.getInstance(getContext()).saveloginres(jsonObject);
                                    Log.d("TAG", "onResponselp: " + SharedPrefManager.getInstance(getContext()).getloginres().getName());
                                    Toasty.success(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, grp_frag).commit();
                                } else {
                                    Toasty.warning(getContext(), "Enter Valid Email or Password", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toasty.error(getContext(), "Failed to Connect", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
//            Log.d("TAG", "onStart:  " + SharedPrefManager.getInstance(getContext()).getRefreshToken());
//            Call<ResponseBody> call = RetrofitClient.getInstance(getContext()).getApi().LoginUsingRefreshToken(AUTH, "refresh_token", SharedPrefManager.getInstance(getContext()).getRefreshToken());
//            call.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    if (response.code() == 200) {
//                        String res = null;
//                        try {
//                            res = response.body().string();
//                            JSONObject jsonObject = new JSONObject(res);
//                            SharedPrefManager.getInstance(getContext()).saveloginres(jsonObject);
//                            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new group_fragment()).commit();
//                        } catch (IOException | JSONException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        Toast.makeText(getContext(), "Error Login", Toast.LENGTH_SHORT).show();
//                        Log.d("TAG", "onResponse: "+SharedPrefManager.getInstance(getContext()).getRefreshToken());
//                        try {
//                            Log.d("TAG", "onResponse: " + response.errorBody().string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
        Signup = v.findViewById(R.id.signup_button_loginpage);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Registration_fragment()).addToBackStack("reg").commit();
            }
        });
        return v;
    }
}
