package com.example.iclean.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.iclean.R;
import com.example.iclean.dto.CucianHistoryDTO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.ViewHolder> implements Filterable {

    private List<CucianHistoryDTO> mData;
    private LayoutInflater mInflater;
    private HistoryItemAdapter.ItemClickListener mClickListener;

    // Konstruktor menerima data.
    public HistoryItemAdapter(Context context, ArrayList<CucianHistoryDTO> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // Menambahkan tata letak baris XML jika perlu
    @Override
    public HistoryItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_cucian_item_history, parent, false);
        return new HistoryItemAdapter.ViewHolder(view);
    }

    // Mengikat data ke TextView dari setiap baris
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CucianHistoryDTO data = mData.get(position);
        Picasso.get().load(data.image).into(holder.imgCucian);
        holder.tvHarga_Cucian.setText("Rp. " +data.total_price+ ".000");
        holder.tvJudul_Cucian.setText(data.name);
        holder.tvquantity.setText(data.qty);
        holder.tvstatus.setText(data.status);
        int conditionColor;
        if(data.status.equals("1")){
            holder.tvstatus.setText("Pending");
            conditionColor = Color.parseColor("#FB7181");
        } else{
            holder.tvstatus.setText("Payment Successful");
            conditionColor = Color.parseColor("#4BB543");
        }
        holder.tvstatus.setTextColor(conditionColor);
    }

    // Total jumlah dari baris
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
        ImageView imgCucian;
        TextView tvJenis_Pakaian, tvHarga_Cucian, tvstatus, tvquantity, tvJudul_Cucian;

        ViewHolder(View itemView) {
            super(itemView);
            imgCucian = itemView.findViewById(R.id.img_cucian);
            tvJudul_Cucian = itemView.findViewById(R.id.tv_judul_cucian);
            tvHarga_Cucian = itemView.findViewById(R.id.tv_harga_cucian);
            tvstatus = itemView.findViewById(R.id.tv_status);
            tvquantity = itemView.findViewById(R.id.tv_qty);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // memungkinkan event klik untuk direcord
    public void setClickListener(HistoryItemAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // Parent Activity akan mengimplementasikan metode ini untuk merespons event dari klik
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}