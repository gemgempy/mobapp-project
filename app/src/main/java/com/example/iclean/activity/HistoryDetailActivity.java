package com.example.iclean.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iclean.R;

public class HistoryDetailActivity extends AppCompatActivity {

    // Inisial awal untuk file teks view
    ImageView imgCucian;
    TextView tvJudul_Cucian, tvHarga_Cucian, tvDesc_Cucian, tvMax_Berat, tvMulai_Berlaku, tvKesulitan, tvJenis_Cucian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        // dipanggil
        inisialisasi();

    }

    private void inisialisasi(){
        imgCucian = findViewById(R.id.img_cucian);
        tvJudul_Cucian = findViewById(R.id.tv_judul_cucian);
        tvHarga_Cucian = findViewById(R.id.tv_harga_cucian);
        tvDesc_Cucian = findViewById(R.id.tv_desc_cucian);
        tvMax_Berat = findViewById(R.id.tv_max_berat);
        tvMulai_Berlaku = findViewById(R.id.tv_mulai_berlaku);
        tvKesulitan = findViewById(R.id.tv_kesulitan);
        tvJenis_Cucian = findViewById(R.id.tv_jenis_cucian);
    }

}