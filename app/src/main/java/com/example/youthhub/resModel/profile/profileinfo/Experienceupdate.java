package com.example.youthhub.resModel.profile.profileinfo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Experienceupdate implements Parcelable {
    @SerializedName("name")
    private String name;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return
                "EmuResponsibilitiesItem{" +
                        "name = '" + name + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public Experienceupdate() {
    }

    protected Experienceupdate(Parcel in) {
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Experienceupdate> CREATOR = new Parcelable.Creator<Experienceupdate>() {
        @Override
        public Experienceupdate createFromParcel(Parcel source) {
            return new Experienceupdate(source);
        }

        @Override
        public Experienceupdate[] newArray(int size) {
            return new Experienceupdate[size];
        }
    };
}
