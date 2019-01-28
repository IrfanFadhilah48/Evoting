package com.example.windowsv8.e_voting.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ResponseDaftarPemilih {

    @SerializedName("pemilih")
    @Expose
    private ArrayList<DaftarPemilih> pemilih = null;

    public ArrayList<DaftarPemilih> getPemilih() {
        return pemilih;
    }

    public void setPemilih(ArrayList<DaftarPemilih> pemilih) {
        this.pemilih = pemilih;
    }
}
