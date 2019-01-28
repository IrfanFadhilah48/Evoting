package com.example.windowsv8.e_voting.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.windowsv8.e_voting.model.ResponseUpload;
import com.example.windowsv8.e_voting.R;
import com.example.windowsv8.e_voting.activity.LoginActivity;
import com.example.windowsv8.e_voting.api.ApiClient;
import com.example.windowsv8.e_voting.api.ApiInterface;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarPesertaFragment extends Fragment {

    private EditText editTextNpm, editTextPassword, editTextNama;
    private ImageView imageView, imageViewProfile;
    private Button buttonUpload;
    private RadioGroup radioGroup;
    private RadioButton radioButton, radioButton1;
    public String pathImage, pathImageProfile, dataNpm, token;
    private ApiInterface service;
    public static final int REQUEST_GALERY = 2;
    public static final int REQUEST_GALERY_PROFILE = 3;
    View root;
    ProgressDialog progressDialog;

    public static DaftarPesertaFragment newInstance(String npm, String token) {
        Bundle args = new Bundle();
        DaftarPesertaFragment fragment = new DaftarPesertaFragment();
        args.putString("npm", npm);
        args.putString("token", token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = getLayoutInflater().inflate(R.layout.fragment_daftar, container, false);
        editTextNpm = root.findViewById(R.id.editTextNpmDaftar);
        editTextPassword = root.findViewById(R.id.editTextPasswordDaftar);
        editTextNama = root.findViewById(R.id.editTextNamaDaftar);
        imageView = root.findViewById(R.id.imageViewDaftar);
        imageViewProfile = root.findViewById(R.id.imageViewProfileDaftar);
        radioGroup = root.findViewById(R.id.radioGroup);
        radioButton = root.findViewById(R.id.radioButton0);
        radioButton1 = root.findViewById(R.id.radioButton1);
        buttonUpload = root.findViewById(R.id.buttonDaftarPeserta);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dataNpm = getArguments().getString("npm");
        token = getArguments().getString("token");
        Log.e("token", token);
        editTextNpm.setText(dataNpm);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageProfile();
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
            }
        });
    }

    private void uploadData() {

        if (dataNpm.contains(editTextNpm.getText().toString().trim())){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Tunggu Sebentar. . .");
            progressDialog.show();

            File file = new File(pathImage);
            File file1 = new File(pathImageProfile);

            service = ApiClient.getClient().create(ApiInterface.class);

            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            RequestBody requestBodyProfile = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
            MultipartBody.Part fileUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
            MultipartBody.Part fileUploadProfile = MultipartBody.Part.createFormData("photo", file1.getName(), requestBodyProfile);
            RequestBody npm = RequestBody.create(MediaType.parse("text/plain"), editTextNpm.getText().toString());
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), editTextNama.getText().toString());
            RequestBody password = RequestBody.create(MediaType.parse("text/plain"), editTextPassword.getText().toString());
            RequestBody sendtoken = RequestBody.create(MediaType.parse("text/plain"), token);

            int selectedId = radioGroup.getCheckedRadioButtonId();

            if (selectedId == radioButton.getId()){
                RequestBody jkel = RequestBody.create(MediaType.parse("text/plain"), radioButton.getText().toString());
                Call<ResponseUpload> request = service.uploadPeserta(fileUpload, fileUploadProfile, npm, password, name, jkel, sendtoken);
                request.enqueue(new Callback<ResponseUpload>() {
                    @Override
                    public void onResponse(Call<ResponseUpload> call, Response<ResponseUpload> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()){
                            if (response.body().getResponse().equals(1)){
                                kosongValue();
                                Snackbar.make(root, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                                countDownIntent();
                            }
                            else {
                                Snackbar.make(root, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUpload> call, Throwable t) {
                        progressDialog.dismiss();
                        Snackbar.make(root, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }else if (selectedId == radioButton1.getId()){
                RequestBody jkel = RequestBody.create(MediaType.parse("text/plain"), radioButton1.getText().toString());
                Call<ResponseUpload> request = service.uploadPeserta(fileUpload, fileUploadProfile, npm, password, name, jkel, sendtoken);
                request.enqueue(new Callback<ResponseUpload>() {
                    @Override
                    public void onResponse(Call<ResponseUpload> call, Response<ResponseUpload> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()){
                            if (response.body().getResponse().equals(1)){
                                kosongValue();
                                Snackbar.make(root, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                                countDownIntent();
                            }
                            else {
                                Snackbar.make(root, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUpload> call, Throwable t) {
                        progressDialog.dismiss();
                        Snackbar.make(root, t.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
            }else {
                Snackbar.make(root, "Harap memilih salah satu jenis kelamin", Snackbar.LENGTH_LONG).show();
            }
        }
        else {
            Snackbar.make(root, "Jangan Mengganti Kolom Isi Npm", Snackbar.LENGTH_LONG).show();
        }

    }

    private void countDownIntent() {
        CountDownTimer timer = null;
        timer = new CountDownTimer(1500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        };
        timer.start();
    }

    private void kosongValue() {
        imageView.setImageBitmap(null);
        imageViewProfile.setImageBitmap(null);
        editTextNpm.setText("");
        editTextPassword.setText("");
        editTextNama.setText("");
        radioGroup.clearCheck();
        root.isFocusableInTouchMode();
    }

    private void chooseImage() {
        Intent openGallery = new Intent();
        openGallery.setType("image/*");
        openGallery.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(openGallery, "Pilih Gambar"), REQUEST_GALERY);
    }

    private void chooseImageProfile() {
        Intent openGallery = new Intent();
        openGallery.setType("image/*");
        openGallery.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(openGallery, "Pilih Gambar"), REQUEST_GALERY_PROFILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALERY){
            if (resultCode == Activity.RESULT_OK){
                Uri uri = data.getData();
                pathImage = getRealPath(getActivity(), uri);
                Log.e("pathImage", pathImage);
                imageView.setImageBitmap(BitmapFactory.decodeFile(getRealPath(getActivity(), uri)));
            }
        }
        else if (requestCode == REQUEST_GALERY_PROFILE){
            if (resultCode == Activity.RESULT_OK){
                Uri uri = data.getData();
                pathImageProfile = getRealPath(getActivity(), uri);
                Log.e("pathImage", pathImageProfile);
                imageViewProfile.setImageBitmap(BitmapFactory.decodeFile(getRealPath(getActivity(), uri)));
            }
        }
    }

    private String getRealPath(FragmentActivity activity, Uri uri) {
        String result = null;
        String[] imageProjecttion = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(uri, imageProjecttion, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(imageProjecttion[0]);
        cursor.moveToFirst();
        result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}
