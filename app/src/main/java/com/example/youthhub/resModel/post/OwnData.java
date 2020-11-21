package com.example.youthhub.resModel.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OwnData {
    @SerializedName("tags")
    @Expose
    private Tags tags;

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }
}
