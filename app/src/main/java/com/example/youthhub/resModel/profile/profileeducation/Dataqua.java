package com.example.youthhub.resModel.profile.profileeducation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dataqua {
    private QualificationProvider qualification_provider;

    public QualificationProvider getQualification_provider ()
    {
        return qualification_provider;
    }

    public void setQualification_provider (QualificationProvider qualification_provider)
    {
        this.qualification_provider = qualification_provider;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [qualification_provider = "+qualification_provider+"]";
    }
}
