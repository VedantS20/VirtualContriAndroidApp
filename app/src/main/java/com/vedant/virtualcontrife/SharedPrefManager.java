package com.vedant.virtualcontrife;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.$Gson$Preconditions;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "shared_pref";
    private static SharedPrefManager mInstance;
    private Context Ctx;

    public SharedPrefManager(Context ctx) {
        Ctx = ctx;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void saveloginres(JSONObject jsonObject) throws JSONException {
        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("loginobj" ,jsonObject.getJSONObject("user").toString());
        editor.putString("name", jsonObject.getJSONObject("user").getString("name"));
        editor.putString("email", jsonObject.getJSONObject("user").getString("email"));
        editor.putString("phone", jsonObject.getJSONObject("user").getString("phone"));
        editor.putString("accesstoken", jsonObject.getString("accessToken"));
        editor.putString("refreshtoken" , jsonObject.getString("refreshToken"));
        editor.apply();
    }
    public void savequizid(JSONObject jsonObject) throws JSONException {
        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("quizid" , jsonObject. getString("data"));
        editor.apply();
    }
    public String getquizid()
    {
        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("quizid" , null);
    }
    public void  saveQuestionid(JSONObject jsonObject) throws JSONException {
        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("questionid" , jsonObject.getString("date"));
        editor.apply();
    }
    public String  getQuestionid()
    {
        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("questionid" , null);
    }
    public void saveSchid(JSONObject jsonObject) throws JSONException {
        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("scheduleid", jsonObject.getJSONObject("date").getString("_id"));
        editor.apply();
    }
    public String getSchid()
    {
        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("scheduleid" , null);
    }
    public String getToken() {
        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("accesstoken", "token");
    }
    public String getRefreshToken() {
        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("refreshtoken", null);
    }
//    public void MakeRefreshNull()
//    {
//        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("refreshtoken" , null);
//        editor.apply();
//    }
    public User getUser()
    {
        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        User user = new User(sharedPreferences.getString("name" , "name") ,
                sharedPreferences.getString("email" , "email"),
                sharedPreferences.getString("phone" , "phone")
        );
        return user;
    }
    public void saveGroupdetails(JSONObject jsonObject) throws JSONException {
        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("jsonarray", jsonObject.getJSONArray("data").toString());
        editor.putString("jsonobj" , jsonObject.toString());
        editor.apply();
    }
//    public void savegroup(GroupResponse groupResponse)
//    {
//        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("Groupobj" , groupResponse.toString());
//        editor.apply();
//    }
//    public ArrayList<Datum> getgrp()
//    {
//        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        String data = sharedPreferences.getString("Groupobj" , null);
//        Gson gson = new Gson();
//        GroupResponse groupResponse = gson.fromJson(data,GroupResponse.class);
//        ArrayList<Datum> datum = new ArrayList<>();
//        for(int i=0 ; i<groupResponse.getData().size();i++)
//        {
//            datum.add(i,groupResponse.getData().get(i));
//        }
//        return datum;
//    }
//    public GroupResponse getgrp1()
//    {
//        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        String data = sharedPreferences.getString("Groupobj" , null);
//        Gson gson = new Gson();
//       GroupResponse groupResponse = gson.fromJson(data,GroupResponse.class);
//       return groupResponse;
//    }
//    public ArrayList<Group1> getgroup() throws JSONException {
//        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        String data = sharedPreferences.getString("jsonarray", null);
//        JSONArray jsonArray = new JSONArray(data);
//        ArrayList<Group1> mgroup = new ArrayList<>();
//       // mgroup = gson.fromJson(data , mgroup.getClass());
//        return mgroup;
//    }
//    public Group1 getgroupdet(int index) throws JSONException {
//        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        String data = sharedPreferences.getString("jsonarray", null);
//        JSONArray jsonArray = new JSONArray(data);
//        Gson gson = new Gson();
//        Group1 group1 = null;
//        for(index = 0 ; index<jsonArray.length();index++)
//        {
//            Type type = new TypeToken<List<Member>>(){}.getType();
//            List<Member> mem =  gson.fromJson(jsonArray.getJSONObject(index).getJSONArray("members").toString() ,type);
//            group1 = new Group1(
//                    jsonArray.getJSONObject(index).getString("group_name"),
//                    jsonArray.getJSONObject(index).getString("description"),
//                    jsonArray.getJSONObject(index).getString("code"),
//                    mem
//                    );
//        }
//        return group1;
//    }
    public User2 getloginres() throws JSONException {
        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String logobj = sharedPreferences.getString("loginobj" , "obj");
        JSONObject jsonObject = new JSONObject(logobj);
        Gson gson = new Gson();
        User2 user2 = gson.fromJson(String.valueOf(jsonObject), User2.class);
        return user2;

    }
    public GroupResponse getres() throws JSONException {
        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String obj = sharedPreferences.getString("jsonobj" , "obj");
        JSONObject jsonObject = new JSONObject(obj);
        Gson gson = new Gson();
        GroupResponse groupResponse = new GroupResponse();
        groupResponse = gson.fromJson(String.valueOf(jsonObject), GroupResponse.class);
        return groupResponse;
    }
    public Boolean isLoggedin()
    {
        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("refreshtoken", null) != null;
    }
//    public ArrayList<String> getgroupdetails() throws JSONException {
//        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        String data = sharedPreferences.getString("jsonarray", "data");
//        JSONArray jsonArray = new JSONArray(data);
//        ArrayList<String> arrayList = new ArrayList<>();
//        for (int i = 0; i < jsonArray.length(); i++) {
//            arrayList.add(i, jsonArray.getJSONObject(i).getString("group_name"));
//        }
//        return arrayList;
//    }

//    public ArrayList<String> getgroupmembers() throws JSONException {
//        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        String data = sharedPreferences.getString("jsonarray", "data");
//        JSONArray jsonArray = new JSONArray(data);
//        ArrayList<String> arrayList = new ArrayList();
//        for(int i=0;i<jsonArray.length();i++)
//        {
//            for(int j=0 ; j<jsonArray.getJSONObject(i).getJSONArray("members").length();j++) {
//                arrayList.add(i, jsonArray.getJSONObject(i).getJSONArray("members").getJSONObject(j).getString("name"));
//            }
//        }
//        return arrayList;
//    }
    public void Clear() {
        SharedPreferences sharedPreferences = Ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
