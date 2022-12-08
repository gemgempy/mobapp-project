package com.example.buku.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.buku.R;
import com.example.buku.activity.DetailBookActivity;

public class BookItemFragment extends Fragment {

    CardView cvLayoutBook;
    ImageView imgBook;
    TextView tvAuthor, tvJudulBuku, tvHargaBuku;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_book_item, container, false);

        inisialisasi(v);
        mainButton();

        return v;
    }

    private void inisialisasi(View v){
        cvLayoutBook = v.findViewById(R.id.cv_layout_book);
        imgBook = v.findViewById(R.id.img_buku);
        tvAuthor = v.findViewById(R.id.tv_author);
        tvJudulBuku = v.findViewById(R.id.tv_judul_buku);
        tvHargaBuku = v.findViewById(R.id.tv_harga_buku);
    }


    private void mainButton(){
        cvLayoutBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DetailBookActivity.class);
                startActivity(i);
            }
        });
    }
}