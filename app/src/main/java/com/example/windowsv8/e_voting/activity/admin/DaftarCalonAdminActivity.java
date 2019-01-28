package com.example.windowsv8.e_voting.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.windowsv8.e_voting.R;
import com.example.windowsv8.e_voting.adapter.CalonAdapter;
import com.example.windowsv8.e_voting.api.ApiClient;
import com.example.windowsv8.e_voting.api.ApiInterface;
import com.example.windowsv8.e_voting.model.Calon;
import com.example.windowsv8.e_voting.model.ResponseDaftarCalon;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarCalonAdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CalonAdapter calonAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_calon_admin);

        recyclerView = findViewById(R.id.recyclerViewDaftarCalonAdmin);
        progressBar = findViewById(R.id.progressBarDaftarCalonAdmin);

        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseDaftarCalon> call = service.getCalon();
        call.enqueue(new Callback<ResponseDaftarCalon>() {
            @Override
            public void onResponse(Call<ResponseDaftarCalon> call, Response<ResponseDaftarCalon> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    loadData(response.body().getCalon());
                }
            }

            @Override
            public void onFailure(Call<ResponseDaftarCalon> call, Throwable t) {

            }
        });
    }

    private void loadData(ArrayList<Calon> calon) {
        calonAdapter = new CalonAdapter(calon, this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        calonAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(calonAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MenuAdminActivity.class));
        finish();
    }
}
