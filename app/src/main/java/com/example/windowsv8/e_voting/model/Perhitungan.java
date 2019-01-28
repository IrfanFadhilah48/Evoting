package com.example.windowsv8.e_voting.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Perhitungan {
    @SerializedName("id")
    @Expose
    private String idCalon;
    @SerializedName("jumlah")
    @Expose
    private String jumlahsuara;

    public String getIdCalon() {
        return idCalon;
    }

    public void setIdCalon(String idCalon) {
        this.idCalon = idCalon;
    }

    public String getJumlahsuara() {
        return jumlahsuara;
    }

    public void setJumlahsuara(String jumlahsuara) {
        this.jumlahsuara = jumlahsuara;
    }
}
