package com.example.windowsv8.e_voting.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponsePerhitungan {
    @SerializedName("jumlahsuara")
    @Expose
    private String jumlahsuara;
    @SerializedName("jumlahcalon")
    @Expose
    private String jumlahcalon;
    @SerializedName("pilihan")
    @Expose
    private Pilihan pilihan;
    @SerializedName("perhitungan")
    @Expose
    private ArrayList<Perhitungan> perhitungan;

    public String getJumlahsuara() {
        return jumlahsuara;
    }

    public void setJumlahsuara(String jumlahsuara) {
        this.jumlahsuara = jumlahsuara;
    }

    public String getJumlahcalon() {
        return jumlahcalon;
    }

    public void setJumlahcalon(String jumlahcalon) {
        this.jumlahcalon = jumlahcalon;
    }

    public Pilihan getPilihan() {
        return pilihan;
    }

    public void setPilihan(Pilihan pilihan) {
        this.pilihan = pilihan;
    }

    public ArrayList<Perhitungan> getPerhitungan() {
        return perhitungan;
    }

    public void setPerhitungan(ArrayList<Perhitungan> perhitungan) {
        this.perhitungan = perhitungan;
    }
}
