package com.example.windowsv8.e_voting.activity.pemilih;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.windowsv8.e_voting.R;
import com.example.windowsv8.e_voting.api.ApiClient;
import com.example.windowsv8.e_voting.api.ApiInterface;
import com.example.windowsv8.e_voting.model.Pemilih;

import java.util.ArrayList;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JumlahPemilihActivity extends AppCompatActivity {

    private PieChartView pieChartView;
    private TextView textView, textView1, textView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jumlah_pemilih);

        pieChartView = findViewById(R.id.chart);
        textView = findViewById(R.id.textViewJumlahPemilihActivity);
        textView1 = findViewById(R.id.textViewLakiPemilihActivity);
        textView2 = findViewById(R.id.textViewWanitaPemilihActivity);

        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<Pemilih> call = service.getJumlahPemilih();

        call.enqueue(new Callback<Pemilih>() {
            @Override
            public void onResponse(Call<Pemilih> call, Response<Pemilih> response) {
                if (response.isSuccessful()){
                    setPieChart(response.body().getJumlah(), response.body().getLakiLaki(), response.body().getPerempuan());
                }
            }

            @Override
            public void onFailure(Call<Pemilih> call, Throwable t) {

            }
        });
    }

    private void setPieChart(String jumlah, String lakiLaki, String perempuan) {
        ArrayList pieData = new ArrayList();
        pieData.add(new SliceValue(Float.parseFloat(lakiLaki)/Float.parseFloat(jumlah), Color.BLUE).setLabel(String.valueOf(String.format("%.2f",(Float.parseFloat(lakiLaki)/Float.parseFloat(jumlah))*100))+"%"));
        pieData.add(new SliceValue(Float.parseFloat(perempuan)/Float.parseFloat(jumlah), Color.MAGENTA).setLabel(String.valueOf(String.format("%.2f",(Float.parseFloat(perempuan)/Float.parseFloat(jumlah))*100))+"%"));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(16);
        pieChartView.setPieChartData(pieChartData);

        textView.setText(jumlah + " Orang");
        textView1.setText(lakiLaki + " Orang");
        textView2.setText(perempuan + " Orang");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
