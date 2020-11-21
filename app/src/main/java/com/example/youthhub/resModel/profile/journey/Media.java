package com.example.youthhub.resModel.profile.journey;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Media implements Parcelable{


    @SerializedName("juc_type")
    @Expose
    private String jucType;
    @SerializedName("juc_image")
    @Expose
    private String jucImage;
    @SerializedName("juc_video_id")
    @Expose
    private String jucVideoId;
    @SerializedName("juc_content_id")
    @Expose
    private String jucContentId;

    public String getJucType() {
        return jucType;
    }

    public void setJucType(String jucType) {
        this.jucType = jucType;
    }

    public String getJucImage() {
        return jucImage;
    }

    public void setJucImage(String jucImage) {
        this.jucImage = jucImage;
    }

    public String getJucVideoId() {
        return jucVideoId;
    }

    public void setJucVideoId(String jucVideoId) {
        this.jucVideoId = jucVideoId;
    }

    public String getJucContentId() {
        return jucContentId;
    }

    public void setJucContentId(String jucContentId) {
        this.jucContentId = jucContentId;
    }
    public Media() {
    }

    protected Media(Parcel in) {
        this.jucContentId = in.readString();
        this.jucImage = in.readString();
        this.jucType = in.readString();
        this.jucVideoId = in.readString();

    }

    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.jucType);
        dest.writeString(this.jucImage);
        dest.writeString(this.jucContentId);
        dest.writeString(this.jucVideoId);

    }
}
