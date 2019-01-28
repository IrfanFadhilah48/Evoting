package com.example.windowsv8.e_voting.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.windowsv8.e_voting.R;
import com.example.windowsv8.e_voting.activity.pemilih.MainActivity;
import com.example.windowsv8.e_voting.api.ApiClient;
import com.example.windowsv8.e_voting.api.ApiInterface;
import com.example.windowsv8.e_voting.model.Calon;
import com.example.windowsv8.e_voting.model.ResponsePemungutan;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PemilihanAdapter extends RecyclerView.Adapter<PemilihanAdapter.ViewHolder> {

    private ArrayList<Calon> calons;
    private Context mContext;

    SharedPreferences sharedPreferences;
    public static final String shared_preference_user = "shared_preference_user";
    public static final String TAG_SESSION = "session_status";
    public final static String TAG_NPM = "npm";
    public final static String TAG_NAME = "name";
    public final static String TAG_IMAGE = "image";
    public final static String TAG_PROFILE = "profile";
    public final static String TAG_VERIF = "verifikasi";
    public final static String TAG_PILIH = "pilih";

    public PemilihanAdapter(ArrayList<Calon> calon, Context context){
        super();
        this.calons = calon;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pemungutan, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Calon calon = calons.get(position);
        holder.textViewNomor.setText(calon.getId());
        Glide.with(mContext).load(calon.getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return calons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNomor;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNomor = itemView.findViewById(R.id.textViewNomorPemungutan);
            imageView = itemView.findViewById(R.id.imageViewPemungutan);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int i = getAdapterPosition();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                    dialog.setTitle("Pesan");
                    dialog.setMessage("Apakah anda yakin memilih nomor urut " + calons.get(i).getId() + " saudara " +calons.get(i).getNama() + " ?");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callPilih(calons.get(i).getId());
                            updateData();
                            //Toast.makeText(mContext, "Terima Kasih pilihan anda sudah tersimpan", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }
            });
        }
    }

    private void updateData() {
        sharedPreferences = mContext.getSharedPreferences(shared_preference_user, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_PILIH, "1");
        editor.apply();

        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = service.getUpdateMemilih(sharedPreferences.getString(TAG_NPM, ""));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void callPilih(String id) {
        sharedPreferences = mContext.getSharedPreferences(shared_preference_user, Context.MODE_PRIVATE);
        Log.e("npm", sharedPreferences.getString(TAG_NPM, "") + id);
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponsePemungutan> call = service.getPemilihan(sharedPreferences.getString(TAG_NPM, ""), id);

        call.enqueue(new Callback<ResponsePemungutan>() {
            @Override
            public void onResponse(Call<ResponsePemungutan> call, Response<ResponsePemungutan> response) {
                Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext, MainActivity.class));
                ((Activity)mContext).finish();
            }

            @Override
            public void onFailure(Call<ResponsePemungutan> call, Throwable t) {
                Toast.makeText(mContext, "Ggal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
