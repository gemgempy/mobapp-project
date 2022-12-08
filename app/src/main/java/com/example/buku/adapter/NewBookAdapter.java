package com.example.buku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.buku.R;
import com.example.buku.dto.BookDTO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewBookAdapter extends RecyclerView.Adapter<NewBookAdapter.ViewHolder> implements Filterable {

    private List<BookDTO> mData;
    private LayoutInflater mInflater;
    private NewBookAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public NewBookAdapter(Context context, List<BookDTO> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public NewBookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_book_item, parent, false);
        return new NewBookAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(NewBookAdapter.ViewHolder holder, int position) {
        BookDTO data = mData.get(position);
        Picasso.get().load(data.image).into(holder.img_buku);
        holder.tv_author.setText(data.author);
        holder.tv_judul_buku.setText(data.name);
        holder.tv_harga_buku.setText(data.price);
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
        TextView tv_author, tv_judul_buku, tv_harga_buku;
        ImageView img_buku;

        ViewHolder(View itemView) {
            super(itemView);
            tv_author = itemView.findViewById(R.id.tv_author);
            tv_judul_buku = itemView.findViewById(R.id.tv_judul_buku);
            tv_harga_buku = itemView.findViewById(R.id.tv_harga_buku);
            img_buku = itemView.findViewById(R.id.img_buku);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    public void setClickListener(NewBookAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

