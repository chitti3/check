package com.example.youthhub.resModel.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OwnTag {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private OwnData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public OwnData getData() {
        return data;
    }

    public void setData(OwnData data) {
        this.data = data;
    }
}
