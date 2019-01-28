package com.example.windowsv8.e_voting.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ResponseDaftarAdmin {
    @SerializedName("data")
    @Expose
    private ArrayList<DaftarAdmin> data = null;

    public ArrayList<DaftarAdmin> getData() {
        return data;
    }

    public void setData(ArrayList<DaftarAdmin> data) {
        this.data = data;
    }
}
