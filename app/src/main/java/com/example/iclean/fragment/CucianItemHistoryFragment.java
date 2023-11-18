package com.example.iclean.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.iclean.R;

public class CucianItemHistoryFragment extends Fragment {

    // Melakukan inisialisasi awal
    LinearLayout llProduct;
    ImageView imgCucian;
    TextView tvJenis_Pakaian, tvJudulCucian, tvHargaCucian, tvQty;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_cucian_item, container, false);
        inisialisasi(v);
        return v;
    }

    private void inisialisasi(View v){
        llProduct = v.findViewById(R.id.ll_product);
        imgCucian = v.findViewById(R.id.img_cucian);
        tvJenis_Pakaian = v.findViewById(R.id.tv_jenis_pakaian);
        tvJudulCucian = v.findViewById(R.id.tv_judul_cucian);
        tvQty = v.findViewById(R.id.tv_qty);
        tvHargaCucian = v.findViewById(R.id.tv_harga_cucian);
    }

}