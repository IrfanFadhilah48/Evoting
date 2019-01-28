package com.example.windowsv8.e_voting.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.windowsv8.e_voting.R;
import com.example.windowsv8.e_voting.activity.admin.DetailPemilihAdminActivity;
import com.example.windowsv8.e_voting.model.DaftarPemilih;

import java.util.ArrayList;

public class PemilihAdapter extends RecyclerView.Adapter<PemilihAdapter.ViewHolder> {

    ArrayList<DaftarPemilih> daftarPemilihs;
    Context mContext;

    public PemilihAdapter(ArrayList<DaftarPemilih> daftarPemilih, Context context){
        super();
        this.daftarPemilihs = daftarPemilih;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pemilih, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DaftarPemilih daftarPemilih = daftarPemilihs.get(position);
        holder.textViewNpm.setText(daftarPemilih.getNpm());
        holder.textViewNama.setText(daftarPemilih.getNama());

        Glide.with(mContext)
                .load(daftarPemilih.getPath())
                .apply(new RequestOptions().centerCrop())
                .into(holder.imageView);

        if (daftarPemilih.getVerifikasi().contains("1")){
            holder.imageViewChecklist.setVisibility(View.VISIBLE);
        }
        else {
            holder.imageViewChecklist.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return daftarPemilihs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView, imageViewChecklist;
        private TextView textViewNpm, textViewNama;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewDaftarPemilih);
            imageViewChecklist = itemView.findViewById(R.id.checkListDaftarPemilih);
            textViewNpm = itemView.findViewById(R.id.textViewNpmDaftarPemilih);
            textViewNama = itemView.findViewById(R.id.textViewNamaDaftarPemilih);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getAdapterPosition();
                    Intent intent = new Intent(mContext, DetailPemilihAdminActivity.class);
                    intent.putExtra("npm", daftarPemilihs.get(i).getNpm());
                    intent.putExtra("nama", daftarPemilihs.get(i).getNama());
                    intent.putExtra("jkel", daftarPemilihs.get(i).getJkel());
                    intent.putExtra("image", daftarPemilihs.get(i).getPath());
                    intent.putExtra("profile", daftarPemilihs.get(i).getProfile());
                    intent.putExtra("token", daftarPemilihs.get(i).getToken());
                    intent.putExtra("activate", daftarPemilihs.get(i).getActivate());
                    intent.putExtra("verif", daftarPemilihs.get(i).getVerifikasi());
                    Log.e("datanya", daftarPemilihs.get(i).getProfile() + daftarPemilihs.get(i).getPath());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
