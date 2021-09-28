package com.vedant.virtualcontrife;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    group_fragment fragment = new group_fragment();
    ImageView Backbutton;
    ShimmerFrameLayout shimmerFrameLayout;
    View v;
    private static final String AUTH = "Basic dmlydHVhbENvbnRyaTpWQ0AwMjEyIQ==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Backbutton = findViewById(R.id.backbutton);
        shimmerFrameLayout = findViewById(R.id.shrimmer_layout);
        v = findViewById(R.id.orange_view);
//        if(savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginPage_fragment() ).commit();
//        }
        if (SharedPrefManager.getInstance(this).isLoggedin()) {
            v.setBackgroundColor(Color.parseColor("#e5e4e2"));
            shimmerFrameLayout.startShimmer();
            Log.d("TAG", "onStart:  " + SharedPrefManager.getInstance(this).getRefreshToken());
            Call<ResponseBody> call = RetrofitClient.getInstance(this).getApi().LoginUsingRefreshToken(AUTH, "refresh_token", SharedPrefManager.getInstance(this).getRefreshToken());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        String res = null;
                        try {
                            res = response.body().string();
                            JSONObject jsonObject = new JSONObject(res);
                            SharedPrefManager.getInstance(MainActivity.this).saveloginres(jsonObject);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new group_fragment()).commit();
                           v.setBackgroundColor(Color.parseColor("#ea462a"));
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Error Login", Toast.LENGTH_SHORT).show();
                        v.setBackgroundColor(Color.parseColor("#ea462a"));
                        Log.d("TAG", "onResponse: " + SharedPrefManager.getInstance(MainActivity.this).getRefreshToken());
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginPage_fragment()).commit();
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        try {
                            Log.d("TAG", "onResponse: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginPage_fragment()).commit();
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
        }
        Backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void HideButton() {
        Backbutton.setVisibility(View.INVISIBLE);
    }

    public void ShowButton() {
        Backbutton.setVisibility(View.VISIBLE);
    }

//     @Override
//     protected void onResume() {
//         super.onResume();
//         shimmerFrameLayout.startShimmer();
//     }
//
//     @Override
//     protected void onPause() {
//         super.onPause();
//         shimmerFrameLayout.stopShimmer();
//     }

    @Override
    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (!(fragment instanceof Backpressed) || !((Backpressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }
        getSupportFragmentManager().popBackStack();
    }
}