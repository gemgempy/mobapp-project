package com.example.buku.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.buku.R;

public class BookItemHistoryFragment extends Fragment {

    LinearLayout llProduct;
    ImageView imgBuku;
    TextView tvAuthor,tvJudulBuku, tvHargaBuku;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_item_history, container, false);

        inisialisasi(v);

        return v;
    }

    private void inisialisasi(View v){

        llProduct = v.findViewById(R.id.ll_product);
        imgBuku = v.findViewById(R.id.img_buku);
        tvAuthor = v.findViewById(R.id.tv_author);
        tvJudulBuku = v.findViewById(R.id.tv_judul_buku);
        tvHargaBuku = v.findViewById(R.id.tv_harga_buku);

    }

}