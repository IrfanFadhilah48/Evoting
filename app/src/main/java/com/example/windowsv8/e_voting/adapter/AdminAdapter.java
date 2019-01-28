package com.example.windowsv8.e_voting.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.windowsv8.e_voting.R;
import com.example.windowsv8.e_voting.model.DaftarAdmin;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {

    Context mContext;
    ArrayList<DaftarAdmin> daftarAdmins;

    public AdminAdapter(ArrayList<DaftarAdmin> daftarAdmins, Context mContext) {
        super();
        this.mContext = mContext;
        this.daftarAdmins = daftarAdmins;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_admin, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DaftarAdmin daftarAdmin = daftarAdmins.get(position);

        holder.textViewNama.setText(daftarAdmin.getName());
        holder.textViewNomor.setText(daftarAdmin.getContact());

        Glide.with(mContext).load(daftarAdmin.getImage()).apply(new RequestOptions().centerCrop()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return daftarAdmins.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNama, textViewNomor;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNama = itemView.findViewById(R.id.textViewNamaDaftarAdmin);
            textViewNomor = itemView.findViewById(R.id.textViewKontakDaftarAdmin);
            imageView = itemView.findViewById(R.id.imageViewDaftarAdmin);
        }
    }
}
