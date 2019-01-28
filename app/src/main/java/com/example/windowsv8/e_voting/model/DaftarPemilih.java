package com.example.windowsv8.e_voting.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DaftarPemilih {
    @SerializedName("npm")
    @Expose
    private String npm;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("jkel")
    @Expose
    private String jkel;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("profile")
    @Expose
    private String profile;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("activate")
    @Expose
    private String activate;
    @SerializedName("verifikasi")
    @Expose
    private String verifikasi;
    @SerializedName("pilih")
    @Expose
    private String pilih;

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

    public String getJkel() {
        return jkel;
    }

    public void setJkel(String jkel) {
        this.jkel = jkel;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public String getVerifikasi() {
        return verifikasi;
    }

    public void setVerifikasi(String verifikasi) {
        this.verifikasi = verifikasi;
    }

    public String getPilih() {
        return pilih;
    }

    public void setPilih(String pilih) {
        this.pilih = pilih;
    }
}
