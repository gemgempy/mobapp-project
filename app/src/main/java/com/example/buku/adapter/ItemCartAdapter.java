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
import com.example.buku.dto.BookCartDTO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemCartAdapter extends RecyclerView.Adapter<ItemCartAdapter.ViewHolder> implements Filterable {
        private List<BookCartDTO> mData;
        private LayoutInflater mInflater;
        private ListItemClickListener mClickListener;

        public interface ListItemClickListener {

            void on_plus_click(BookCartDTO item);
            void on_min_click(BookCartDTO item);
            void on_hapus_cart(BookCartDTO item);

        }


        // data is passed into the constructor
        public ItemCartAdapter(Context context, List<BookCartDTO> data, ListItemClickListener mClickListener) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
            this.mClickListener = mClickListener;
        }

        // inflates the row layout from xml when needed
        @Override
        public ItemCartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.layout_cart_item, parent, false);
            return new ItemCartAdapter.ViewHolder(view);
        }

        // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(ItemCartAdapter.ViewHolder holder, int position) {
            BookCartDTO data = mData.get(position);

            Picasso.get().load(data.image).into(holder.imgBuku);
            holder.tvJudulBuku.setText(data.name);
            holder.tvHargaBuku.setText(data.price);
            holder.edtQty.setText(data.qty);
            holder.TV_ID_CART_USER.setText(data.id_cart);
            holder.btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.on_hapus_cart(data);
                }
            });

            holder.btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.on_plus_click(data);
                }
            });

            holder.btnMin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.on_min_click(data);
                }
            });

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
        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imgBuku;
            TextView tvJudulBuku, tvHargaBuku, TV_ID_CART_USER;
            Button btnMin, btnPlus, btnHapus;
            EditText edtQty;

            ViewHolder(View itemView) {
                super(itemView);
                imgBuku = itemView.findViewById(R.id.img_buku);
                tvJudulBuku = itemView.findViewById(R.id.tv_judul_buku);
                tvHargaBuku = itemView.findViewById(R.id.tv_harga_buku);
                btnMin = itemView.findViewById(R.id.btn_min);
                btnPlus = itemView.findViewById(R.id.btn_plus);
                btnHapus = itemView.findViewById(R.id.btn_hapus);
                edtQty = itemView.findViewById(R.id.edt_qty);
                TV_ID_CART_USER = itemView.findViewById(R.id.ID_BUKU_CART);


            }

        }


    }
