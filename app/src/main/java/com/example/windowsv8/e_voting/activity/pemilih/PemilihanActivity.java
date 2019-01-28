package com.example.windowsv8.e_voting.activity.pemilih;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.windowsv8.e_voting.R;
import com.example.windowsv8.e_voting.activity.pemilih.MainActivity;
import com.example.windowsv8.e_voting.adapter.PemilihanAdapter;
import com.example.windowsv8.e_voting.api.ApiClient;
import com.example.windowsv8.e_voting.api.ApiInterface;
import com.example.windowsv8.e_voting.model.Calon;
import com.example.windowsv8.e_voting.model.ResponseDaftarCalon;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PemilihanActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PemilihanAdapter adapterPemungutan;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemilihan);

        progressBar = findViewById(R.id.progressBarPemilihan);
        recyclerView = findViewById(R.id.recyclerViewPemilihan);

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
        adapterPemungutan = new PemilihanAdapter(calon, this);
        layoutManager = new GridLayoutManager(this, 2);
        adapterPemungutan.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterPemungutan);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
