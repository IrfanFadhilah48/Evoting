package com.example.windowsv8.e_voting.activity.admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.windowsv8.e_voting.R;
import com.example.windowsv8.e_voting.api.ApiClient;
import com.example.windowsv8.e_voting.api.ApiInterface;
import com.example.windowsv8.e_voting.model.ResponseTambahCalon;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahCalonActivity extends AppCompatActivity {

    private EditText editTextNomor, editTextNama, editTextDeskripsi, editTextTanggal;
    private ImageView imageView;
    private AppCompatButton button;
    private DatePickerDialog datePickerDialog;
    Calendar calendar;
    String convertTanggal, pathImage;

    public static final int REQUEST_GALERY = 2;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_calon);

        editTextNomor = findViewById(R.id.editTextNomorTambahCalon);
        editTextNama = findViewById(R.id.editTextNamaTambahCalon);
        editTextDeskripsi = findViewById(R.id.editTextDeskripsiTambahCalon);
        editTextTanggal = findViewById(R.id.editTextTanggalTambahCalon);
        imageView = findViewById(R.id.imageViewTambahCalon);
        button = findViewById(R.id.buttonTambahCalon);
        calendar = Calendar.getInstance();

        editTextTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
    }

    private void showCalendar() {
        datePickerDialog =  new DatePickerDialog(TambahCalonActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                Date now = Calendar.getInstance().getTime();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
                simpleDateFormat.format(now);
                convertTanggal = simpleDateFormat.format(calendar.getTime());
                Log.e("datanya", convertTanggal);

                SimpleDateFormat text = new SimpleDateFormat("dd MMMM yyyy");
                text.format(now);
                editTextTanggal.setText(text.format(calendar.getTime()));

                //editTextTanggal.setText(convertTanggal);
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void chooseImage() {
        Intent openGallery = new Intent();
        openGallery.setType("image/*");
        openGallery.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(openGallery, "Pilih Gmbar"), REQUEST_GALERY);
    }

    private void upload() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Tunggu Sebentar . . . ");
        progressDialog.show();

        File file = new File(pathImage);
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part fileUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), editTextNomor.getText().toString());
        RequestBody nama = RequestBody.create(MediaType.parse("text/plain"), editTextNama.getText().toString());
        RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"), editTextDeskripsi.getText().toString());
        RequestBody tanggal = RequestBody.create(MediaType.parse("text/plain"), convertTanggal);

        Call<ResponseTambahCalon> call = service.uploadTambahCalon(fileUpload, id, nama, deskripsi, tanggal);
        call.enqueue(new Callback<ResponseTambahCalon>() {
            @Override
            public void onResponse(Call<ResponseTambahCalon> call, Response<ResponseTambahCalon> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    if (response.body().getResponse().equals(1)){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        kosongValue();
                        startActivity(new Intent(TambahCalonActivity.this, MenuAdminActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseTambahCalon> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void kosongValue() {
        imageView.setImageResource(R.drawable.no_preview);
        editTextNama.setText(null);
        editTextNomor.setText(null);
        editTextTanggal.setText(null);
        editTextDeskripsi.setText(null);
        editTextNomor.clearFocus();
        editTextNama.clearFocus();
        editTextDeskripsi.clearFocus();
        editTextTanggal.clearFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALERY){
            if (resultCode == RESULT_OK){
                Uri uri = data.getData();
                pathImage = getRealPath(uri, getApplicationContext());
                imageView.setImageBitmap(BitmapFactory.decodeFile(getRealPath(uri, getApplicationContext())));
            }
        }
    }

    private String getRealPath(Uri uri, Context context) {
        String result = null;
        String[] imageProjection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri,imageProjection, null, null, null);
        int columnIndex = cursor.getColumnIndexOrThrow(imageProjection[0]);
        cursor.moveToFirst();
        result = cursor.getString(columnIndex);
        return result;
    }

}
