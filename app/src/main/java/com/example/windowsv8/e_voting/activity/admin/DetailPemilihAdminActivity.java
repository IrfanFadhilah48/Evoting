package com.example.windowsv8.e_voting.activity.admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.windowsv8.e_voting.R;
import com.example.windowsv8.e_voting.api.ApiClient;
import com.example.windowsv8.e_voting.api.ApiInterface;
import com.example.windowsv8.e_voting.model.DaftarPemilih;
import com.example.windowsv8.e_voting.model.ResponseTambahCalon;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;
import com.msoftworks.easynotify.EasyNotify;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPemilihAdminActivity extends AppCompatActivity {

    private TextView textViewNpm, textViewNama, textViewJkel;
    private ImageView imageView, imageView1;
    private ProgressBar progressBar;
    private Button button;
    String npm, nama, jkel, path, profile, token, id_admin;

    SharedPreferences sharedPreferences;
    public static final String shared_preference_admin = "shared_preference_admin";
    public static final String TAG_ID_ADMIN = "id";
    public static final String TAG_NAMA_ADMIN = "nama";

    public static final String API_KEY = "AAAAGRQSZYQ:APA91bGZmdyreP6bXUrVNsNWKDPNN3SM1EzouleKbCYv49IxGP9mvK2QuuXCb9dXkugLI7ffPdiQkjEO0MtBbvg2NZPUBeeBEg6EqNV42N_DVr0cLHNght8kLjHSqM7xFT3_co_fGQW5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemilih_admin);

        textViewNpm = findViewById(R.id.textViewNpmDetailPemilih);
        textViewNama = findViewById(R.id.textViewNamaDetailPemilih);
        textViewJkel = findViewById(R.id.textViewJkelDetailPemilih);
        imageView = findViewById(R.id.imageViewFotoDetailPemilih);
        imageView1 = findViewById(R.id.imageViewFoto2DetailPemilih);
        progressBar = findViewById(R.id.progressBarDetailPemilih);
        button = findViewById(R.id.buttonVerifikasiDetailPemilih);

        sharedPreferences = getSharedPreferences(shared_preference_admin, Context.MODE_PRIVATE);
        id_admin = sharedPreferences.getString(TAG_ID_ADMIN, null);
        FirebaseMessaging.getInstance().subscribeToTopic("allDevices");

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {

            }
        });
        progressBar.setVisibility(View.INVISIBLE);

        Bundle extraNpm = getIntent().getExtras();
        npm = extraNpm.getString("npm");
        textViewNpm.setText(npm);

        Bundle extraNama = getIntent().getExtras();
        nama = extraNama.getString("nama");
        textViewNama.setText(nama);

        Bundle extraJkel = getIntent().getExtras();
        jkel = extraJkel.getString("jkel");
        textViewJkel.setText(jkel);

        Bundle extraPath = getIntent().getExtras();
        path = extraPath.getString("image");
        //Log.e("image", extraPath.getString("path"));
        Glide.with(this)
                .load(path)
                .apply(new RequestOptions().centerCrop())
                .into(imageView);

        Bundle extraProfile = getIntent().getExtras();
        profile = extraProfile.getString("profile");
        Glide.with(this)
                .load(profile)
                .apply(new RequestOptions().fitCenter())
                .into(imageView1);

        Bundle extraToken = getIntent().getExtras();
        token = extraToken.getString("token");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                verifikasiAdmin();
//                sendNotification();
                manualSendNotification();
            }
        });
    }

    private void verifikasiAdmin() {
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseTambahCalon> call = service.verifikasiAdmin(npm, sharedPreferences.getString(TAG_ID_ADMIN, null));
        call.enqueue(new Callback<ResponseTambahCalon>() {
            @Override
            public void onResponse(Call<ResponseTambahCalon> call, Response<ResponseTambahCalon> response) {
                if (response.isSuccessful()){
                    if (response.body().getResponse().equals(1)){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DetailPemilihAdminActivity. this, DaftarPemilihAdminActivity.class));
                        finish();
                    }else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseTambahCalon> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendNotification() {
        EasyNotify easyNotify = new EasyNotify(API_KEY);
//        easyNotify.setSendBy(EasyNotify.TOPIC);
//        easyNotify.setTopic("allDevices");
        Log.e("token", token);
        easyNotify.setSendBy(EasyNotify.TOKEN);
        easyNotify.setToken(token);
        easyNotify.setTitle("Verifikasi Data Pemilih");
        easyNotify.setBody("Anda sudah bisa memilih calon, Silahkan login ulang kembali ke dalam aplikasi");
        easyNotify.setSound("default");
        easyNotify.setClickAction("MAINACTIVITY");
//        easyNotify.setClickAction("LOGINACTIVITY");
        easyNotify.nPush();
        easyNotify.setEasyNotifyListener(new EasyNotify.EasyNotifyListener() {
            @Override
            public void onNotifySuccess(String s) {
                Toast.makeText(getApplicationContext(), "berhasil", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNotifyError(String s) {
                Toast.makeText(getApplicationContext(), "gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void manualSendNotification() {
        JSONObject json = new JSONObject();
        JSONObject message = new JSONObject();
        try {
            message.put("title","Verifikasi Data Pemilih");
            message.put("text", "Anda sudah bisa memilih calon, Silahkan login ulang kembali ke dalam aplikasi");
            json.put("to",token);
            json.put("priority", "high");
            json.put("notification", message);

            ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseBody> call = service.sendNotification(json.toString());

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "berhasil", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "gagal", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
