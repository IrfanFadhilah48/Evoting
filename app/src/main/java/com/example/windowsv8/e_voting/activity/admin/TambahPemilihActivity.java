package com.example.windowsv8.e_voting.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.windowsv8.e_voting.R;
import com.example.windowsv8.e_voting.api.ApiClient;
import com.example.windowsv8.e_voting.api.ApiInterface;
import com.example.windowsv8.e_voting.model.ResponseUpload;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahPemilihActivity extends AppCompatActivity {

    private EditText editText;
    private AppCompatButton button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pemilih);

        editText = findViewById(R.id.editTextNpmTambahPemilih);
        button = findViewById(R.id.buttonTambahPemilih);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData(editText.getText().toString());
            }
        });
    }

    private void uploadData(String npm) {
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseUpload> call = service.uploadTambahPemilih(npm);

        call.enqueue(new Callback<ResponseUpload>() {
            @Override
            public void onResponse(Call<ResponseUpload> call, Response<ResponseUpload> response) {
                if (response.isSuccessful()){
                    if (Objects.requireNonNull(response.body()).getResponse().equals(1)){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TambahPemilihActivity.this, MenuAdminActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseUpload> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
