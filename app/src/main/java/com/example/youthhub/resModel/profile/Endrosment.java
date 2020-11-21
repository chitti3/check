package com.example.youthhub.resModel.profile;

import com.google.gson.annotations.SerializedName;

public class Endrosment {
    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    @Override
    public String toString(){
        return
                "BasicResponse{" +
                        "message = '" + message + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}
