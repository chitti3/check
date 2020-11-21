package com.example.youthhub.resModel.profile.journey;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Endorsedby implements Parcelable {
    @SerializedName("jur_name")
    private String jurname;

    @SerializedName("jur_designation")
    private String jurdesignation;

    @SerializedName("jur_company")
    private String jurcompany;

    public String getJurname() {
        return jurname;
    }

    public void setJurname(String jurname) {
        this.jurname = jurname;
    }

    public String getJurdesignation() {
        return jurdesignation;
    }

    public void setJurdesignation(String jurdesignation) {
        this.jurdesignation = jurdesignation;
    }

    public String getJurcompany() {
        return jurcompany;
    }

    public void setJurcompany(String jurcompany) {
        this.jurcompany = jurcompany;
    }

    public String getJurnamecode() {
        return jurnamecode;
    }

    public void setJurnamecode(String jurnamecode) {
        this.jurnamecode = jurnamecode;
    }

    @SerializedName("jur_name_code")
    private String jurnamecode;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.jurname);
        dest.writeString(this.jurdesignation);
        dest.writeString(this.jurcompany);
        dest.writeString(this.jurnamecode);

    }

    public Endorsedby() {
    }

    protected Endorsedby(Parcel in) {
        this.jurname = in.readString();
        this.jurdesignation = in.readString();
        this.jurcompany = in.readString();
        this.jurnamecode = in.readString();
    }

    public static final Parcelable.Creator<Endorsedby> CREATOR = new Parcelable.Creator<Endorsedby>() {
        @Override
        public Endorsedby createFromParcel(Parcel source) {
            return new Endorsedby(source);
        }

        @Override
        public Endorsedby[] newArray(int size) {
            return new Endorsedby[size];
        }
    };

   }
