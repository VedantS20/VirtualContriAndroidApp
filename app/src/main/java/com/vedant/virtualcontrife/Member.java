package com.vedant.virtualcontrife;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Member {
    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String Name;
    @SerializedName("user")
    private List<User2> mUser;

    public Member(String name, List<User2> mUser) {
        Name = name;
        this.mUser = mUser;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public List<User2> getmUser() {
        return mUser;
    }
}
