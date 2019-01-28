package com.example.windowsv8.e_voting.activity.admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.windowsv8.e_voting.R;

import java.util.Calendar;

public class MenuAdminActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cardView, cardView1, cardView2, cardView3, cardView4, cardView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        cardView = findViewById(R.id.cardViewHomeAdminDaftarCalon);
        cardView1 = findViewById(R.id.cardViewHomeAdminTambahCalon);
        cardView2 = findViewById(R.id.cardViewHomeAdminVerifikasi);
        cardView3 = findViewById(R.id.cardViewHomeAdminTambahPemilih);
        cardView4 = findViewById(R.id.cardViewHomeAdminJumlahPemilih);
        cardView5 = findViewById(R.id.cardViewHomeAdminPerhitungan);

        cardView.setOnClickListener(this);
        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
        cardView4.setOnClickListener(this);
        cardView5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardViewHomeAdminDaftarCalon:
                startActivity(new Intent(this, DaftarCalonAdminActivity.class));
                finish();
                break;
            case R.id.cardViewHomeAdminTambahCalon:
                startActivity(new Intent(this, TambahCalonActivity.class));
                finish();
                break;
            case R.id.cardViewHomeAdminVerifikasi:
                startActivity(new Intent(this, DaftarPemilihAdminActivity.class));
                finish();
                break;
            case R.id.cardViewHomeAdminTambahPemilih:
                startActivity(new Intent(this, TambahPemilihActivity.class));
                finish();
                break;
            case R.id.cardViewHomeAdminJumlahPemilih:
                break;
            case R.id.cardViewHomeAdminPerhitungan:
                break;
        }
    }
}
