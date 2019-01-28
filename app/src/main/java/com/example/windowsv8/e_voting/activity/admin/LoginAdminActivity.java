package com.example.windowsv8.e_voting.activity.admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.windowsv8.e_voting.model.ResponseLoginAdmin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginAdminActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private AppCompatButton button;
    String username, password;

    SharedPreferences sharedPreferences;
    public static final String shared_preference_admin = "shared_preference_admin";
    public static final String TAG_ID_ADMIN = "id";
    public static final String TAG_NAMA_ADMIN = "nama";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        editTextUsername = findViewById(R.id.editTextUsrnameLoginAdmin);
        editTextPassword = findViewById(R.id.editTextPasswordLoginAdmin);
        button = findViewById(R.id.buttonLoginAdmin);

        sharedPreferences = getSharedPreferences(shared_preference_admin, Context.MODE_PRIVATE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editTextUsername.getText().toString();
                password = editTextPassword.getText().toString();

                ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponseLoginAdmin> call = service.loginRequestAdmin(username, password);

                call.enqueue(new Callback<ResponseLoginAdmin>() {
                    @Override
                    public void onResponse(Call<ResponseLoginAdmin> call, Response<ResponseLoginAdmin> response) {
                        if (response.isSuccessful()){
                            if (response.body().getResponse().equals(1)){
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(TAG_ID_ADMIN, response.body().getId());
                                editor.putString(TAG_NAMA_ADMIN, response.body().getName());
                                editor.apply();

                                startActivity(new Intent(LoginAdminActivity.this, MenuAdminActivity.class));
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseLoginAdmin> call, Throwable t) {

                    }
                });
            }
        });
    }
}
