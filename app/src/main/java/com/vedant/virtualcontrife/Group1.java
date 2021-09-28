package com.vedant.virtualcontrife;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Group1 {
    @SerializedName("_id")
    private String id;
    @SerializedName("group_name")
    private String Name;
    @SerializedName("description")
    private String descrption;
    @SerializedName("code")
    private String code;
    @SerializedName("members")
    private List<Member> mMember;

    public Group1(String name, String descrption, String code, List<Member> mMember) {
        Name = name;
        this.descrption = descrption;
        this.code = code;
        this.mMember = mMember;
    }

    public String getName() {
        return Name;
    }

    public String getDescrption() {
        return descrption;
    }

    public String getCode() {
        return code;
    }

    public List<Member> getmMember() {
        return mMember;
    }
}
