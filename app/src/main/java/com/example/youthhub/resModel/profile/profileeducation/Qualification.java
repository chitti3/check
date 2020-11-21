package com.example.youthhub.resModel.profile.profileeducation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Qualification {
    private Dataqua data;

    private String status;

    public Dataqua getData ()
    {
        return data;
    }

    public void setData (Dataqua data)
    {
        this.data = data;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", status = "+status+"]";
    }
}
