package com.vedant.virtualcontrife;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Group {
    private Uri imageId;
    private String groupName;
    private String description;

    public Group(Uri imageId, String groupName, String description) {
        this.imageId = imageId;
        this.groupName = groupName;
        this.description = description;
    }

    public Uri getImageId() {
        return imageId;
    }

    public void setImageId(Uri imageId) {
        this.imageId = imageId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
