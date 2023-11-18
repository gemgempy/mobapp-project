package com.example.iclean.adapter;

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

import com.example.iclean.R;
import com.example.iclean.dto.CucianCartDTO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemCartAdapter extends RecyclerView.Adapter<ItemCartAdapter.ViewHolder> implements Filterable {
        private List<CucianCartDTO> mData;
        private LayoutInflater mInflater;
        private ListItemClickListener mClickListener;

        public interface ListItemClickListener {

            void on_plus_click(CucianCartDTO item);
            void on_min_click(CucianCartDTO item);
            void on_hapus_cart(CucianCartDTO item);

        }


        // data dipassing kepada konstruktor
        public ItemCartAdapter(Context context, List<CucianCartDTO> data, ListItemClickListener mClickListener) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
            this.mClickListener = mClickListener;
        }

        // Menambahkan tata letak baris XML jika perlu
        @Override
        public ItemCartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.layout_cart_item, parent, false);
            return new ItemCartAdapter.ViewHolder(view);
        }

    // Mengikat data ke TextView dari setiap baris
        @Override
        public void onBindViewHolder(ItemCartAdapter.ViewHolder holder, int position) {
            CucianCartDTO data = mData.get(position);

            Picasso.get().load(data.image).into(holder.imgCucian);
            holder.tvJudulCucian.setText(data.name);
            holder.tvHargaCucian.setText(data.price);
            holder.tvHargaCucian.setText("Rp. "+ data.price+"0");
            holder.edtQty.setText(data.qty);
           // holder.TV_ID_CART_USER.setText(data.id_cart);
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

    // Jumlah dari baris
        @Override
        public int getItemCount() {
            return mData.size();
        }

        @Override
        public Filter getFilter() {
            return null;
        }


    // Menyimpan dan mendaur ulang tampilan saat digulir dari layar
        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imgCucian;
            TextView tvJudulCucian, tvHargaCucian, TV_ID_CART_USER;
            Button btnMin, btnPlus, btnHapus;
            EditText edtQty;

            ViewHolder(View itemView) {
                super(itemView);
                imgCucian = itemView.findViewById(R.id.img_cucian);
                tvJudulCucian = itemView.findViewById(R.id.tv_judul_cucian);
                tvHargaCucian = itemView.findViewById(R.id.tv_harga_cucian);
                btnMin = itemView.findViewById(R.id.btn_min);
                btnPlus = itemView.findViewById(R.id.btn_plus);
                btnHapus = itemView.findViewById(R.id.btn_hapus);
                edtQty = itemView.findViewById(R.id.edt_qty);
                TV_ID_CART_USER = itemView.findViewById(R.id.ID_CUCIAN_CART);


            }

        }


    }
