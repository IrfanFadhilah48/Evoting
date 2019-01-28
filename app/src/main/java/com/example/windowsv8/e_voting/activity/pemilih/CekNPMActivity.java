package com.example.windowsv8.e_voting.activity.pemilih;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.windowsv8.e_voting.activity.LoginActivity;
import com.example.windowsv8.e_voting.model.ResponseCek;
import com.example.windowsv8.e_voting.R;
import com.example.windowsv8.e_voting.api.ApiClient;
import com.example.windowsv8.e_voting.api.ApiInterface;
import com.example.windowsv8.e_voting.fragment.DaftarPesertaFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CekNPMActivity extends AppCompatActivity{

    private EditText edtNpm;
    private AppCompatButton buttonCek;
    private View linearLayout;
    private String npm;
    public static String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_peserta);

        edtNpm = findViewById(R.id.editTextNpmCek);
        buttonCek = findViewById(R.id.buttonCekPeserta);
        linearLayout = findViewById(R.id.ll_daftar);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
            }
        });

        buttonCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                npm = edtNpm.getText().toString();
                cekNpm(npm, token);
            }
        });

    }

    private void cekNpm(final String npm, final String token) {
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseCek> request = service.cekRequest(npm);
        request.enqueue(new Callback<ResponseCek>() {
            @Override
            public void onResponse(Call<ResponseCek> call, Response<ResponseCek> response) {
                if (response.isSuccessful()){
                    if (response.body().getResponse().equals(1)){
                        if (response.body().getAktivasi().equals("1")){
                            //Toast.makeText(getApplicationContext(), "NPM anda sudah diaktivasi", Toast.LENGTH_SHORT).show();
                            Snackbar.make(linearLayout, "NPM anda sudah diaktivasi", Snackbar.LENGTH_SHORT).show();
                        }
                        else {
                            FragmentTransaction fragment = getSupportFragmentManager().beginTransaction();
                            fragment.replace(R.id.container_peserta, new DaftarPesertaFragment().newInstance(npm, token), "Simple_frag")
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .addToBackStack(null)
                                    .commit();

                            linearLayout.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCek> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("Simple_frag");
        if (fragment != null){
            getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).remove(fragment).commit();
            linearLayout.setVisibility(View.VISIBLE);
        }
        else{
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        //linearLayout.setVisibility(View.VISIBLE);
        //finish();
        //startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
