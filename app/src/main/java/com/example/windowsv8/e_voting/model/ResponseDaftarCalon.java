package com.example.windowsv8.e_voting.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponseDaftarCalon {
    @SerializedName("calon")
    @Expose
    private ArrayList<Calon> calon = null;

    public ArrayList<Calon> getCalon() {
        return calon;
    }

    public void setCalon(ArrayList<Calon> calon) {
        this.calon = calon;
    }

}
