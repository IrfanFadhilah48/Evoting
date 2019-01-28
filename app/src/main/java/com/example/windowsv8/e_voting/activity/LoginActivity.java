package com.example.windowsv8.e_voting.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.windowsv8.e_voting.activity.admin.LoginAdminActivity;
import com.example.windowsv8.e_voting.activity.pemilih.CekNPMActivity;
import com.example.windowsv8.e_voting.activity.pemilih.MainActivity;
import com.example.windowsv8.e_voting.model.ResponseLogin;
import com.example.windowsv8.e_voting.R;
import com.example.windowsv8.e_voting.api.ApiClient;
import com.example.windowsv8.e_voting.api.ApiInterface;
import com.example.windowsv8.e_voting.model.ResponseTambahCalon;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtNpm, edtPassword;
    AppCompatButton buttonLogin, buttonCek, buttonAdmin;
    SharedPreferences sharedPreferences;
    Boolean session = false;
    public static final String shared_preference_user = "shared_preference_user";
    public static final String TAG_SESSION = "session_status";
    public final static String TAG_NPM = "npm";
    public final static String TAG_NAME = "name";
    public final static String TAG_IMAGE = "image";
    public final static String TAG_PROFILE = "profile";
    public final static String TAG_VERIF = "verifikasi";
    public final static String TAG_PILIH = "pilih";
    public final static String TAG_TOKEN = "token";
    public String npmSP, nameSP, imageSP, profileSP, verifSP, pilihSP, token;
    View view;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtNpm = findViewById(R.id.editTextNpmLogin);
        edtPassword = findViewById(R.id.editTextPasswordLogin);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonCek = findViewById(R.id.buttonCek);
        buttonAdmin = findViewById(R.id.buttonPindahAdmin);

        sharedPreferences = getSharedPreferences(shared_preference_user, Context.MODE_PRIVATE);
        session = sharedPreferences.getBoolean(TAG_SESSION, false);
        npmSP = sharedPreferences.getString(TAG_NPM, null);
        nameSP = sharedPreferences.getString(TAG_NAME, null);
        imageSP = sharedPreferences.getString(TAG_IMAGE, null);
//        FirebaseMessaging.getInstance().subscribeToTopic("allDevices");

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                Log.e("token",token);
                if (session){
                    if (token.equals(sharedPreferences.getString(TAG_TOKEN, null))){
                        Toast.makeText(getApplicationContext(), "Token sama", Toast.LENGTH_SHORT).show();
                        Log.e("token",token);
                    }else {
                        updateUserToken();
                    }
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        });

//        if (session){
//            startActivity(new Intent(this, MainActivity.class));
//        }

        buttonLogin.setOnClickListener(this);
        buttonCek.setOnClickListener(this);
        buttonAdmin.setOnClickListener(this);

        requestPermission();
    }

    private void updateUserToken() {
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseTambahCalon> call = service.updateToken(sharedPreferences.getString(TAG_NPM, ""), token);

        call.enqueue(new Callback<ResponseTambahCalon>() {
            @Override
            public void onResponse(Call<ResponseTambahCalon> call, Response<ResponseTambahCalon> response) {
                if (response.isSuccessful()){
                    if (response.body().getResponse().equals(1)){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(TAG_TOKEN, token);
                        editor.apply();
                    }else {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonLogin:
                login();
                break;
            case R.id.buttonCek:
                startActivity(new Intent(this, CekNPMActivity.class));
                finish();
                break;
            case R.id.buttonPindahAdmin:
                startActivity(new Intent(this, LoginAdminActivity.class));
                finish();
                break;
        }
    }

    private void login() {
        final String npm = edtNpm.getText().toString();
        final String password = edtPassword.getText().toString();
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseLogin> request = service.loginRequest(npm, password);
        request.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                updateUserToken();
                if (response.isSuccessful()){
                    if (response.body().getResponse().equals(1)){
                        if (response.body().getAktivasi().equals("1")){
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(TAG_SESSION, true);
                            editor.putString(TAG_NPM, response.body().getNpm());
                            editor.putString(TAG_NAME, response.body().getNama());
                            editor.putString(TAG_IMAGE, response.body().getImage());
                            editor.putString(TAG_PROFILE, response.body().getProfile());
                            editor.putString(TAG_TOKEN, response.body().getToken());
                            editor.putString(TAG_VERIF, response.body().getVerifikasi());
                            editor.putString(TAG_PILIH, response.body().getMemilih());
                            editor.apply();

                            finish();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }else {
                            Toast.makeText(getApplicationContext(), "Silahkan Melakukan Aktivasi Akun Terlebih dahulu",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
//                        Snackbar.make(view, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Snackbar.make(view, "GAGAL MENGHUBUNGKAN KE SERVER", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void requestPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()){

                        }
                        if (report.isAnyPermissionPermanentlyDenied()){
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Butuh Permissions");
        builder.setMessage("Silahkan mengizinkan semua permission");
        builder.setPositiveButton("Goto Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                openSettings();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent,100);
    }
}
