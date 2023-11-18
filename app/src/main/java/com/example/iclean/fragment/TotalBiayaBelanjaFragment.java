package com.example.iclean.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.iclean.R;
import com.example.iclean.activity.CheckoutActivity;

public class TotalBiayaBelanjaFragment extends Fragment {

    TextView totalHarga;
    Button btnPayment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_total_biaya_belanja, container, false);

        inisialisasi(v);
        btnPayment();

        return v;
    }

    private void inisialisasi(View v){
        totalHarga = v.findViewById(R.id.tv_total_harga);
        btnPayment = v.findViewById(R.id.btn_payment);
    }

    private void btnPayment(){
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CheckoutActivity.class);
                startActivity(i);
            }
        });
    }

}