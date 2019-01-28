package com.example.windowsv8.e_voting.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pemilih {
    @SerializedName("jumlah")
    @Expose
    private String jumlah;
    @SerializedName("laki-laki")
    @Expose
    private String lakiLaki;
    @SerializedName("perempuan")
    @Expose
    private String perempuan;

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getLakiLaki() {
        return lakiLaki;
    }

    public void setLakiLaki(String lakiLaki) {
        this.lakiLaki = lakiLaki;
    }

    public String getPerempuan() {
        return perempuan;
    }

    public void setPerempuan(String perempuan) {
        this.perempuan = perempuan;
    }
}
