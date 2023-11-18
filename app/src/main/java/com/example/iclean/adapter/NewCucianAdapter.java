package com.example.iclean.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.iclean.R;
import com.example.iclean.dto.CucianDTO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewCucianAdapter extends RecyclerView.Adapter<NewCucianAdapter.ViewHolder> implements Filterable {

    private List<CucianDTO> mData;
    private LayoutInflater mInflater;
    private NewCucianAdapter.ItemClickListener mClickListener;

    // data dipassing kepada konstruktor
    public NewCucianAdapter(Context context, List<CucianDTO> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // Menambahkan tata letak baris XML jika perlu
    @Override
    public NewCucianAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_cucian_item, parent, false);
        return new NewCucianAdapter.ViewHolder(view);
    }

    // Mengikat data ke TextView dari setiap baris
    @Override
    public void onBindViewHolder(NewCucianAdapter.ViewHolder holder, int position) {
        CucianDTO data = mData.get(position);
        Picasso.get().load(data.image).into(holder.img_cucian);
        holder.tv_jenis_pakaian.setText(data.jenis_pakaian);
        holder.tv_judul_cucian.setText(data.name);
        holder.tv_harga_cucian.setText(data.price);
        holder.tv_harga_cucian.setText("Rp. "+ data.price+"0");
    }

    // Total jumlah baris
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }


    // Menyimpan dan mendaur ulang tampilan saat digulir dari layar
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_jenis_pakaian, tv_judul_cucian, tv_harga_cucian;
        ImageView img_cucian;

        ViewHolder(View itemView) {
            super(itemView);
            tv_jenis_pakaian = itemView.findViewById(R.id.tv_jenis_pakaian);
            tv_judul_cucian = itemView.findViewById(R.id.tv_judul_cucian);
            tv_harga_cucian = itemView.findViewById(R.id.tv_harga_cucian);
            img_cucian = itemView.findViewById(R.id.img_cucian);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // memungkinkan event klik untuk direcord
    public void setClickListener(NewCucianAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // Parent Activity akan mengimplementasikan metode ini untuk merespons event dari klik
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

