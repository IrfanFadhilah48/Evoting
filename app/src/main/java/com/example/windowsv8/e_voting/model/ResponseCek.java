package com.example.windowsv8.e_voting.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseCek {

    @SerializedName("berhasil")
    @Expose
    private Integer response;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("aktivasi")
    @Expose
    private String aktivasi;

    public ResponseCek() {

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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAktivasi() {
        return aktivasi;
    }

    public void setAktivasi(String aktivasi) {
        this.aktivasi = aktivasi;
    }
}
