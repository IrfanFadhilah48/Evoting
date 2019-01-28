package com.example.windowsv8.e_voting.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pilihan {

    @SerializedName("pilih1")
    @Expose
    private String pilih1;
    @SerializedName("pilih2")
    @Expose
    private String pilih2;
    @SerializedName("pilih3")
    @Expose
    private String pilih3;
    @SerializedName("pilih4")
    @Expose
    private String pilih4;
    @SerializedName("pilih5")
    @Expose
    private String pilih5;
    @SerializedName("pilih6")
    @Expose
    private String pilih6;
    @SerializedName("pilih7")
    @Expose
    private String pilih7;
    @SerializedName("pilih8")
    @Expose
    private String pilih8;

    public String getPilih1() {
        return pilih1;
    }

    public void setPilih1(String pilih1) {
        this.pilih1 = pilih1;
    }

    public String getPilih2() {
        return pilih2;
    }

    public void setPilih2(String pilih2) {
        this.pilih2 = pilih2;
    }

    public String getPilih3() {
        return pilih3;
    }

    public void setPilih3(String pilih3) {
        this.pilih3 = pilih3;
    }

    public String getPilih4() {
        return pilih4;
    }

    public void setPilih4(String pilih4) {
        this.pilih4 = pilih4;
    }

    public String getPilih5() {
        return pilih5;
    }

    public void setPilih5(String pilih5) {
        this.pilih5 = pilih5;
    }

    public String getPilih6() {
        return pilih6;
    }

    public void setPilih6(String pilih6) {
        this.pilih6 = pilih6;
    }

    public String getPilih7() {
        return pilih7;
    }

    public void setPilih7(String pilih7) {
        this.pilih7 = pilih7;
    }

    public String getPilih8() {
        return pilih8;
    }

    public void setPilih8(String pilih8) {
        this.pilih8 = pilih8;
    }
}
