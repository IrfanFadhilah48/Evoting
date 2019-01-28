package com.example.windowsv8.e_voting.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUpload {

    @SerializedName("success")
    @Expose
    private Integer response;
    @SerializedName("message")
    @Expose
    private String message;

    public ResponseUpload() {
    }

    public Integer getResponse() {
        return response;
    }

    public void setResponse(Integer response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
