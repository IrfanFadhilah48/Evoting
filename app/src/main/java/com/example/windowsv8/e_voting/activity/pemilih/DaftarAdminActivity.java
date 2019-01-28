package com.example.windowsv8.e_voting.activity.pemilih;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.windowsv8.e_voting.R;
import com.example.windowsv8.e_voting.adapter.AdminAdapter;
import com.example.windowsv8.e_voting.api.ApiClient;
import com.example.windowsv8.e_voting.api.ApiInterface;
import com.example.windowsv8.e_voting.model.DaftarAdmin;
import com.example.windowsv8.e_voting.model.ResponseDaftarAdmin;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarAdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    AdminAdapter adminAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_admin);

        recyclerView = findViewById(R.id.recyclerViewDaftarAdmin);
        progressBar = findViewById(R.id.progressBarDaftarAdmin);

        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseDaftarAdmin> call = service.getAdmin();

        call.enqueue(new Callback<ResponseDaftarAdmin>() {
            @Override
            public void onResponse(Call<ResponseDaftarAdmin> call, Response<ResponseDaftarAdmin> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    loadData(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResponseDaftarAdmin> call, Throwable t) {

            }
        });
    }

    private void loadData(ArrayList<DaftarAdmin> data) {
        layoutManager = new LinearLayoutManager(this);
        adminAdapter = new AdminAdapter(data, this);
        adminAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adminAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
