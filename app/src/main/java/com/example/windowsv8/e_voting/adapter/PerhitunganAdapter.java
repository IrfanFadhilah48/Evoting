package com.example.windowsv8.e_voting.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windowsv8.e_voting.R;
import com.example.windowsv8.e_voting.model.Perhitungan;

import java.util.ArrayList;

public class PerhitunganAdapter extends RecyclerView.Adapter<PerhitunganAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Perhitungan> perhitungans;

    public PerhitunganAdapter(ArrayList<Perhitungan> perhitungan, Context context){
        super();
        this.perhitungans = perhitungan;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_perhitungan, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Perhitungan perhitungan = perhitungans.get(position);
        int[] color = mContext.getResources().getIntArray(R.array.colors);

        holder.textViewNomor.setText(perhitungan.getIdCalon());
        holder.textViewJumlah.setText(perhitungan.getJumlahsuara());
        holder.imageView.setBackgroundColor(color[position]);
    }

    @Override
    public int getItemCount() {
        return perhitungans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNomor, textViewJumlah;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNomor = itemView.findViewById(R.id.textViewNomorCalonPerhitungan);
            textViewJumlah = itemView.findViewById(R.id.textViewJumlahSuaraPerhitungan);
            imageView = itemView.findViewById(R.id.colorCalonPerhitungan);
        }
    }
}
