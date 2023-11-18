package com.example.iclean.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.iclean.Handler.Constant;
import com.example.iclean.Handler.RequestHandler;
import com.example.iclean.R;
import com.example.iclean.dto.CucianDTO;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailCucianActivity extends AppCompatActivity implements View.OnClickListener{

    // Deklarasi Variable
    ImageView imgCucian;
    TextView tvJenis_Pakaian, tvJudul_Cucian, tvHarga_Cucian, tvDesc_Cucian, tvMax_Berat, tvMulai_Berlaku, tvKesulitan, tvJenis_Cucian, tvMax_Jumlah;
    Button btnAddCart;
    CucianDTO cucianDTO;

    int qty = 1;

    String id_Cucian,id_data_User, harga_cucian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cucian);

        // ambil intent data dari cuciandto
        cucianDTO = (CucianDTO) getIntent().getSerializableExtra("data");

        inisialisasi();

        getSupportActionBar().hide();

        btnAddCart.setOnClickListener(this);

    }

    private void inisialisasi(){ // inilisilisasi awal

        imgCucian = findViewById(R.id.img_cucian);
        tvJenis_Pakaian = findViewById(R.id.tv_jenis_pakaian);
        tvJudul_Cucian = findViewById(R.id.tv_judul_cucian);
        tvHarga_Cucian = findViewById(R.id.tv_harga_cucian);
        tvDesc_Cucian = findViewById(R.id.tv_desc_cucian);
        tvMax_Berat = findViewById(R.id.tv_max_berat);
        tvMulai_Berlaku = findViewById(R.id.tv_mulai_berlaku);
        tvKesulitan = findViewById(R.id.tv_kesulitan);
        tvJenis_Cucian = findViewById(R.id.tv_jenis_cucian);
        tvMax_Jumlah = findViewById(R.id.tv_max_jumlah);

        btnAddCart = findViewById(R.id.btn_add_cart);

        tvJenis_Pakaian.setText(cucianDTO.jenis_pakaian);
        tvJudul_Cucian.setText(cucianDTO.name);
        tvHarga_Cucian.setText(cucianDTO.price);
        tvMax_Jumlah.setText(CucianDTO.max_jumlah);

        //picasso untuk load image
        Picasso.get().load(cucianDTO.image).into(imgCucian);

        pengubahan();

        // set textnya ya bro dari cucian dto. apa apa , yang mau diambil datanya berdasarkan tempatnya
        tvDesc_Cucian.setText(cucianDTO.description);
        tvMax_Berat.setText(cucianDTO.max_berat);
        tvMulai_Berlaku.setText(cucianDTO.mulai_berlaku);
        tvKesulitan.setText(cucianDTO.kesulitan);
        tvJenis_Cucian.setText(cucianDTO.rating);

    }

    public void pengubahan(){
        HomeActivity activity = new HomeActivity();

        // user_id, cucian_id, qty, price

        id_data_User = activity.getUSERDATA_ID(); // buat id_user

        id_Cucian = cucianDTO.id;  // buat id_cucian

        harga_cucian = tvHarga_Cucian.getText().toString().trim();// buat harga_cucian
    }


    public void memasukkan_kedalam_cart(){



        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();

        //library volley dibawah ini untuk mengubungkan ke api lalu ke database,


        StringRequest request = new StringRequest(Request.Method.POST, Constant.URL_ADD_CART_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);

                    // ambil dari database ( json)
                    int sukses = jObj.getInt("success");
                    String pesan = jObj.getString("message");

                    if (sukses == -1) {

                        progressDialog.dismiss();
                        Toast.makeText(DetailCucianActivity.this, pesan, Toast.LENGTH_SHORT).show();

                        return;

                    }

                    // Error dalam registrasi
                    // jika gagal didaftarkan
                    if (sukses == -2){

                        progressDialog.dismiss();
                        Toast.makeText(DetailCucianActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        return;

                    }

                    // kalau ada kesalahan pengisian cart
                    if (sukses == -3) {

                        progressDialog.dismiss();
                        Toast.makeText(DetailCucianActivity.this, pesan, Toast.LENGTH_SHORT).show();

                        return;
                    }

                    else{
                        progressDialog.dismiss();

                        Toast.makeText(DetailCucianActivity.this, "Cool, you have successfully added to your cart ! ", Toast.LENGTH_SHORT).show();

                        // buat masuk ke sign in
                        Intent i = new Intent(getApplication(), HomeActivity.class);
                        startActivity(i);


                        finish();
                    }
                } catch (Exception ex) { // jika eror
                    Log.e("Error: ", ex.toString());
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) { // toast jika eror

                Toast.makeText(DetailCucianActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Log.e("Error: ", error.getMessage());

            }
        }

        ) {

            //digunakan untuk memasukkan data imputan dari aplikasi kedalam api, dengan mencocokkan pada apa yang telah dideklarasikan pada methode post di api (php - nya), sehingga bisa terhubung

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("user_id", id_data_User);
                params.put("cucian_id", id_Cucian);
                params.put("qty", String.valueOf(qty)); // default
                params.put("price", harga_cucian);

                return params;

            }
        };
// baru bisa dijalankan
        RequestHandler.getInstance((Context) this).addToRequestQueue(request);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_add_cart){

            try {

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
}