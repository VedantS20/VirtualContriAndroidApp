package com.vedant.virtualcontrife;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @POST("sign-up")
    Call<ResponseBody> RegisterUser(
            @Body JsonObject regjsonobj
    );

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> Login(
            @Header("Authorization") String BasicToken,
            @Field("username") String Username,
            @Field("password") String Password,
            @Field("grant_type") String Grant_Type
    );

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> LoginUsingRefreshToken(
            @Header("Authorization") String BasicToken,
            @Field("grant_type") String Grant_Type,
            @Field("refresh_token") String RefreshToken
    );

    @GET("groups")
    Call<ResponseBody> getGroups();
    //@Header("Authorization") String AccessToken

    @POST("groups")
    Call<ResponseBody> createGroup(
           // @Header("Authorization") String AccessToken,
            @Body JsonObject createobj
    );

    @POST("join-group")
    Call<ResponseBody> JoinGroup(
    //  @Header("Authorization") String AccessToken,
      @Body JsonObject joinobj
    );

    @POST("quiz")
    Call<ResponseBody> CreateQuiz(
            @Body JsonObject jsonObject
    );

    @POST("quiz/{quizid}/questions")
    Call<ResponseBody> Addquestion(
            @Path("quizid") String quizid,
            @Body JsonObject jsonObject
    );
    @POST("schedules")
    Call<ResponseBody> Createschedule(
      @Body JsonObject jsonObject
    );

    @PUT("schedules/{quizid}")
    Call<ResponseBody> PutSchedule(
            @Path("quizid") String quizid,
            @Body JsonObject jsonObject
    );

    @GET("schedules")
    Call<ResponseBody> getMySchedules(
            @Query("mySchedule") boolean mysch
    );
}