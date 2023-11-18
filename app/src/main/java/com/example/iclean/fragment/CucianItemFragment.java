package com.example.iclean.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.iclean.R;
import com.example.iclean.activity.DetailCucianActivity;

public class CucianItemFragment extends Fragment {

    // Melakukan inisialisasi awal
    CardView cvLayoutCucian;
    ImageView imgCucian;
    TextView tvJenis, tvJudulCucian, tvHargaCucian;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_cucian_item, container, false);
        inisialisasi(v);
        mainButton();

        return v;
    }
    private void inisialisasi(View v){

        cvLayoutCucian = v.findViewById(R.id.cv_layout_cucian);
        imgCucian = v.findViewById(R.id.img_cucian);
        tvJenis = v.findViewById(R.id.tv_jenis_pakaian);
        tvJudulCucian = v.findViewById(R.id.tv_judul_cucian);
        tvHargaCucian = v.findViewById(R.id.tv_harga_cucian);
    }
    private void mainButton(){
        cvLayoutCucian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DetailCucianActivity.class);
                startActivity(i);
            }
        });
    }
}