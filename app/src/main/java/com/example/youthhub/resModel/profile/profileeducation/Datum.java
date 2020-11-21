package com.example.youthhub.resModel.profile.profileeducation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("qam_qualification_id")
    @Expose
    private String qamQualificationId;
    @SerializedName("qam_title")
    @Expose
    private String qamTitle;

    public String getQamQualificationId() {
        return qamQualificationId;
    }

    public void setQamQualificationId(String qamQualificationId) {
        this.qamQualificationId = qamQualificationId;
    }

    public String getQamTitle() {
        return qamTitle;
    }

    public void setQamTitle(String qamTitle) {
        this.qamTitle = qamTitle;
    }
}
