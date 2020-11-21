package com.example.youthhub.resModel.profile;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Organisationcategory implements Parcelable {
    @SerializedName("ogc_name")
    private String ogmName;

    @SerializedName("ogc_category_id")
    private String ogmOrganisationId;

    public Organisationcategory() {

    }

    public String getOgmName() {
        return ogmName;
    }

    public void setOgmName(String ogmName) {
        this.ogmName = ogmName;
    }

    public String getOgmOrganisationId() {
        return ogmOrganisationId;
    }

    public void setOgmOrganisationId(String ogmOrganisationId) {
        this.ogmOrganisationId = ogmOrganisationId;
    }
    @Override
    public String toString(){
        return
                ogmName;
    }
    public Organisationcategory(Parcel in) {
        this.ogmName = in.readString();
        this.ogmOrganisationId = in.readString();
    }

    public static final Creator<Organisationcategory> CREATOR = new Creator<Organisationcategory>() {
        @Override
        public Organisationcategory createFromParcel(Parcel in) {
            return new Organisationcategory(in);
        }

        @Override
        public Organisationcategory[] newArray(int size) {
            return new Organisationcategory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ogmName);
        dest.writeString(this.ogmOrganisationId);
    }
}
