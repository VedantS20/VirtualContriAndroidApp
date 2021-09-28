package com.vedant.virtualcontrife;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

  //  private static final String AUTH = "Basic dmlydHVhbENvbnRyaTpWQ0AwMjEyIQ==";
    private static final String BASE_URL = "http://13.127.143.19:5000/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient(final Context context) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request = chain.request();
                                Request.Builder requestbuilder = request.newBuilder()
                                       .addHeader("Authorization", "Bearer "+SharedPrefManager.getInstance(context).getToken())
                                        .method(request.method(), request.body());
                                Request request1 = requestbuilder.build();
                                Response response = chain.proceed(request1);
                                return response;
                            }
                        }
                ).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

    }

    public static synchronized RetrofitClient getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RetrofitClient(context);
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
