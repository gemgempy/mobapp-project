package com.example.buku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.buku.R;
import com.example.buku.dto.BookDTO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.ViewHolder> implements Filterable {

    private List<BookDTO> mData;
    private LayoutInflater mInflater;
    private HistoryItemAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public HistoryItemAdapter(Context context, List<BookDTO> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public HistoryItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_book_item_history, parent, false);
        return new HistoryItemAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(HistoryItemAdapter.ViewHolder holder, int position) {
        BookDTO data = mData.get(position);
        Picasso.get().load(data.image).into(holder.imgBuku);
        holder.tvJudulBuku.setText(data.name);
        holder.tvHargaBuku.setText(data.price);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgBuku;
        TextView tvAuthor,tvJudulBuku, tvHargaBuku;

        ViewHolder(View itemView) {
            super(itemView);
            imgBuku = itemView.findViewById(R.id.img_buku);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvJudulBuku = itemView.findViewById(R.id.tv_judul_buku);
            tvHargaBuku = itemView.findViewById(R.id.tv_harga_buku);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    public void setClickListener(HistoryItemAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
