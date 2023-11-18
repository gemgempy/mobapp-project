package com.example.iclean.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.iclean.R;
import com.example.iclean.dto.CucianDTO;

import java.util.ArrayList;

public class CartItemFragment extends Fragment {

    // Melakukan inisialisasi awal
    ImageView imgCucian;
    TextView tvJudulCucian, tvHargaCucian, tvid_id_cart;
    Button btnMin, btnPlus, btnHapus;
    EditText edtQty;
    CucianDTO bookDTO;
    CartFragment cartFragment;

    ArrayList<CucianDTO> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_cart_item, container, false);

        inisialisasi(v);

        return v;
    }

    private void inisialisasi(View v) {
        imgCucian = v.findViewById(R.id.img_cucian);
        tvJudulCucian = v.findViewById(R.id.tv_judul_cucian);
        tvHargaCucian = v.findViewById(R.id.tv_harga_cucian);
        btnMin = v.findViewById(R.id.btn_min);
        btnPlus = v.findViewById(R.id.btn_plus);
        btnHapus = v.findViewById(R.id.btn_hapus);
        edtQty = v.findViewById(R.id.edt_qty);
        tvid_id_cart = v.findViewById(R.id.ID_CUCIAN_CART);
    }

}