package com.example.windowsv8.e_voting.activity.pemilih;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.windowsv8.e_voting.R;
import com.example.windowsv8.e_voting.activity.pemilih.MainActivity;
import com.example.windowsv8.e_voting.adapter.PerhitunganAdapter;
import com.example.windowsv8.e_voting.api.ApiClient;
import com.example.windowsv8.e_voting.api.ApiInterface;
import com.example.windowsv8.e_voting.model.Perhitungan;
import com.example.windowsv8.e_voting.model.ResponsePerhitungan;

import java.util.ArrayList;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerhitunganSuaraActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PieChartView pieChartView;
    private TextView textView;
    PerhitunganAdapter perhitunganAdapter;
    ArrayList<Perhitungan> perhitungann = new ArrayList<>();
    PieChartData pieChartData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        progressBar = findViewById(R.id.progressBarPerhitunganSuara);
        recyclerView = findViewById(R.id.recyclerViewPerhitunganSuara);
        textView = findViewById(R.id.textViewJumlahPerhitunganSuara);
        pieChartView = findViewById(R.id.pieChartPerhitunganSuara);

        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponsePerhitungan> call = service.getPerhitunganSuara();
        //setPieChart();

        call.enqueue(new Callback<ResponsePerhitungan>() {
            @Override
            public void onResponse(Call<ResponsePerhitungan> call, Response<ResponsePerhitungan> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    loadData(response.body().getPerhitungan());
                    setPieChart(response.body().getJumlahsuara());
                    textView.setText(response.body().getJumlahsuara());
                }
            }

            @Override
            public void onFailure(Call<ResponsePerhitungan> call, Throwable t) {

            }
        });
    }

    private void setPieChart(String jumlahsuara) {
        ArrayList pieData = new ArrayList<>();
        int[] color = getResources().getIntArray(R.array.colors);
        for (int i = 0; i < perhitungann.size(); i++){
            pieData.add(new SliceValue(Float.parseFloat(perhitungann.get(i).getJumlahsuara())/100, color[i]).setLabel(String.format("%.2f",(Float.parseFloat(perhitungann.get(i).getJumlahsuara())/Float.parseFloat(jumlahsuara))*100)));
            pieChartData = new PieChartData(pieData);
            pieChartData.setHasLabels(true).setValueLabelTextSize(16);
        }
        pieChartView.setPieChartData(pieChartData);
    }

    private void loadData(ArrayList<Perhitungan> perhitungan) {
        perhitungann = perhitungan;
        layoutManager = new LinearLayoutManager(this);
        perhitunganAdapter = new PerhitunganAdapter(perhitungan, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(perhitunganAdapter);
        perhitunganAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
