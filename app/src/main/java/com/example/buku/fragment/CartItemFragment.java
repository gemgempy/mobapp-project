package com.example.buku.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.buku.R;
import com.example.buku.dto.BookDTO;

import java.util.ArrayList;

public class CartItemFragment extends Fragment {

    ImageView imgBuku;
    TextView tvJudulBuku, tvHargaBuku, tvid_id_cart;
    Button btnMin, btnPlus, btnHapus;
    EditText edtQty;
    BookDTO bookDTO;
    CartFragment cartFragment;

    ArrayList<BookDTO> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.layout_cart_item, container, false);

        //bookDTO = (BookDTO) getIntent().getSerializableExtra("data");
        inisialisasi(v);

        return v;
    }

    private void inisialisasi(View v) {
        imgBuku = v.findViewById(R.id.img_buku);
        tvJudulBuku = v.findViewById(R.id.tv_judul_buku);
        tvHargaBuku = v.findViewById(R.id.tv_harga_buku);
        btnMin = v.findViewById(R.id.btn_min);
        btnPlus = v.findViewById(R.id.btn_plus);
        btnHapus = v.findViewById(R.id.btn_hapus);
        edtQty = v.findViewById(R.id.edt_qty);
        tvid_id_cart = v.findViewById(R.id.ID_BUKU_CART);
    }

}