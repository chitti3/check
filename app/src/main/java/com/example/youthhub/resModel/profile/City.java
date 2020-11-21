package com.example.youthhub.resModel.profile;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City implements Parcelable {
    @SerializedName("ci_city_id")
    @Expose
    private String ciCityId;
    @SerializedName("ci_name")
    @Expose
    private String ciName;

    public String getCiCityId() {
        return ciCityId;
    }

    public void setCiCityId(String ciCityId) {
        this.ciCityId = ciCityId;
    }


    public String getCiName() {
        return ciName;
    }

    public void setCiName(String ciName) {
        this.ciName = ciName;
    }
    @Override
    public String toString(){
        return
                ciName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ciName);
        dest.writeString(this.ciCityId);
    }
}
