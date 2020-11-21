package com.example.youthhub.resModel.profile.profileeducation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QualificationProvider {
    private String qap_name;

    private String qap_provider_id;

    public String getQap_name ()
    {
        return qap_name;
    }

    public void setQap_name (String qap_name)
    {
        this.qap_name = qap_name;
    }

    public String getQap_provider_id ()
    {
        return qap_provider_id;
    }

    public void setQap_provider_id (String qap_provider_id)
    {
        this.qap_provider_id = qap_provider_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [qap_name = "+qap_name+", qap_provider_id = "+qap_provider_id+"]";
    }
}
