package com.example.buku.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buku.R;
import com.example.buku.fragment.HistoryFragment;

public class CheckoutActivity extends AppCompatActivity {

    TextView tvAlamatRumah;
    EditText edtNamaKartu, edtCardNumber, edtCVV, edtExpDate;
    Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        inisialisasi();
        mainButton();
        getSupportActionBar().hide();
    }

    private void inisialisasi(){
        tvAlamatRumah = findViewById(R.id.edt_alamat_rumah);
        edtNamaKartu = findViewById(R.id.edt_nama_kartu);
        edtCardNumber = findViewById(R.id.edt_card_number);
        edtCVV = findViewById(R.id.edt_cvv);
        edtExpDate = findViewById(R.id.edt_exp_date);
        btnCheckout = findViewById(R.id.btn_checkout);
    }

    private void mainButton(){
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), HistoryFragment.class);
                startActivity(i);
            }
        });
    }

}