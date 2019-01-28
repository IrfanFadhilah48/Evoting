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
import com.example.windowsv8.e_voting.model.Calon;

import java.util.ArrayList;

public class CalonAdapter extends RecyclerView.Adapter<CalonAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Calon> calons;

    public CalonAdapter(ArrayList<Calon> calon, Context context){
        super();
        this.calons = calon;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_calon, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Calon calon = calons.get(position);
        holder.textViewNomor.setText(calon.getId());
        holder.textViewNama.setText(calon.getNama());
        Glide.with(context).load(calon.getImage()).apply(RequestOptions.centerCropTransform()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return calons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNomor, textViewNama;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNomor = itemView.findViewById(R.id.textViewNomorDaftarCalon);
            textViewNama = itemView.findViewById(R.id.textViewNamaDaftarCalon);
            imageView = itemView.findViewById(R.id.imageViewDaftarCalon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
