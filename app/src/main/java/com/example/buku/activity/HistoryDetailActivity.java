package com.example.buku.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buku.R;

public class HistoryDetailActivity extends AppCompatActivity {

    ImageView imgBuku;
    TextView tvAuthor, tvJudulBuku, tvHargaBuku, tvDescBuku, tvJmlHal, tvTglTerbit, tvIsbn, tvPenerbit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        inisialisasi();

    }

    private void inisialisasi(){
        imgBuku = findViewById(R.id.img_buku);
        tvAuthor = findViewById(R.id.tv_author);
        tvJudulBuku = findViewById(R.id.tv_judul_buku);
        tvHargaBuku = findViewById(R.id.tv_harga_buku);
        tvDescBuku = findViewById(R.id.tv_desc_buku);
        tvJmlHal = findViewById(R.id.tv_jml_hal);
        tvTglTerbit = findViewById(R.id.tv_tgl_terbit);
        tvIsbn = findViewById(R.id.tv_isbn);
        tvPenerbit = findViewById(R.id.tv_penerbit);
    }

}