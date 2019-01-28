package com.example.windowsv8.e_voting.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.windowsv8.e_voting.R;
import com.example.windowsv8.e_voting.adapter.PemilihAdapter;
import com.example.windowsv8.e_voting.api.ApiClient;
import com.example.windowsv8.e_voting.api.ApiInterface;
import com.example.windowsv8.e_voting.model.DaftarPemilih;
import com.example.windowsv8.e_voting.model.Pemilih;
import com.example.windowsv8.e_voting.model.ResponseDaftarPemilih;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarPemilihAdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    PemilihAdapter pemilihAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pemilih_admin);

        recyclerView = findViewById(R.id.recyclerViewDaftarPemilihAdmin);
        progressBar = findViewById(R.id.progressBarDaftarPemilihAdmin);

        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseDaftarPemilih> call = service.getPemilih();

        call.enqueue(new Callback<ResponseDaftarPemilih>() {
            @Override
            public void onResponse(Call<ResponseDaftarPemilih> call, Response<ResponseDaftarPemilih> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    loadData(response.body().getPemilih());
                }
            }

            @Override
            public void onFailure(Call<ResponseDaftarPemilih> call, Throwable t) {

            }
        });
    }

    private void loadData(ArrayList<DaftarPemilih> pemilih) {
        layoutManager = new LinearLayoutManager(this);
        pemilihAdapter = new PemilihAdapter(pemilih, this);
        pemilihAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(pemilihAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MenuAdminActivity.class));
        finish();
    }
}
