package com.example.windowsv8.e_voting.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin {

    @SerializedName("success")
    @Expose
    private Integer response;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("npm")
    @Expose
    private String npm;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("profile")
    @Expose
    private String profile;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("aktivasi")
    @Expose
    private String aktivasi;
    @SerializedName("verifikasi")
    @Expose
    private String verifikasi;
    @SerializedName("memilih")
    @Expose
    private String memilih;
    @SerializedName("admin")
    @Expose
    private String admin;

    public Integer getResponse() {
        return response;
    }

    public void setResponse(Integer response) {
        this.response = response;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getAdmin() {
        return admin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAktivasi() {
        return aktivasi;
    }

    public void setAktivasi(String aktivasi) {
        this.aktivasi = aktivasi;
    }

    public String getVerifikasi() {
        return verifikasi;
    }

    public void setVerifikasi(String verifikasi) {
        this.verifikasi = verifikasi;
    }

    public String getMemilih() {
        return memilih;
    }

    public void setMemilih(String memilih) {
        this.memilih = memilih;
    }
}
