package com.example.youthhub.resModel.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tags {
    @SerializedName("tg_tag_id")
    @Expose
    private Integer tgTagId;
    @SerializedName("tg_name")
    @Expose
    private String tgName;

    public Integer getTgTagId() {
        return tgTagId;
    }

    public void setTgTagId(Integer tgTagId) {
        this.tgTagId = tgTagId;
    }

    public String getTgName() {
        return tgName;
    }

    public void setTgName(String tgName) {
        this.tgName = tgName;
    }
}
