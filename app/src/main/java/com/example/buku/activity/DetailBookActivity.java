package com.example.buku.activity;

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
import com.example.buku.Handler.Constant;
import com.example.buku.Handler.RequestHandler;
import com.example.buku.Handler.SQLite;
import com.example.buku.Handler.SQLite_Buku;
import com.example.buku.R;
import com.example.buku.auth.SignInActivity;
import com.example.buku.auth.SignUpActivity;
import com.example.buku.dto.BookDTO;
import com.example.buku.fragment.CartFragment;
import com.example.buku.fragment.CartItemFragment;
import com.example.buku.fragment.HomeFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.sql.Struct;
import java.util.HashMap;
import java.util.Map;

public class DetailBookActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imgBuku;
    TextView tvAuthor, tvJudulBuku, tvHargaBuku, tvDescBuku, tvJmlHal, tvTglTerbit, tvIsbn, tvPenerbit;
    Button btnAddCart;
    BookDTO bookDTO;
    //private SQLite_Buku dbsqlite;
    HomeFragment homeFragment;

    int qty = 1;

    String id_buku,id_data_User, harga_buku;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);

        bookDTO = (BookDTO) getIntent().getSerializableExtra("data");

        inisialisasi();

        getSupportActionBar().hide();

        btnAddCart.setOnClickListener(this);
//        mainButton();
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

        btnAddCart = findViewById(R.id.btn_add_cart);

        tvAuthor.setText(bookDTO.author);
        tvJudulBuku.setText(bookDTO.name);
        tvHargaBuku.setText(bookDTO.price);
        Picasso.get().load(bookDTO.image).into(imgBuku);

        pengubahan();

        tvDescBuku.setText(bookDTO.description);
        tvJmlHal.setText(bookDTO.total_pages);
        tvTglTerbit.setText(bookDTO.published_at);
        tvIsbn.setText(bookDTO.isbn);
        tvPenerbit.setText(bookDTO.rating);


    }


    public void pengubahan(){

        HomeActivity activity = new HomeActivity();


        // user_id, book_id, qty, price

        // bentar

        id_data_User = activity.getUSERDATA_ID(); // buat id_user

        id_buku = bookDTO.id;  // buat id_buku

        harga_buku = tvHargaBuku.getText().toString().trim();// buat harga_buku
    }


    public void memasukkan_kedalam_cart(){

       // pengubahan();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");

        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Constant.URL_ADD_CART_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);

                    //String created_at = jObj.getString("created_att"); // ambil dari database ( json)

                    int sukses = jObj.getInt("success");
                    String pesan = jObj.getString("message");
                    String id_pembelanjaan = jObj.getString("id_pembelian");


                    if (sukses == -1) {

                        progressDialog.dismiss();
                        Toast.makeText(DetailBookActivity.this, pesan, Toast.LENGTH_SHORT).show();

                        return;

                    }

                    // Error in registration!  // user failed to store
                    // jika gagal didaftarkan
                    if (sukses == -2){

                        progressDialog.dismiss();
                        Toast.makeText(DetailBookActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        return;

                    }

                    // jika ada kesalan dalam PENGISIAN KE DALAM CART
                    if (sukses == -3) {

                        progressDialog.dismiss();
                        Toast.makeText(DetailBookActivity.this, pesan, Toast.LENGTH_SHORT).show();

                        return;
                    }

                    else{
                        progressDialog.dismiss();


                        Toast.makeText(DetailBookActivity.this, "Cool, you have successfully added to your cart ! ", Toast.LENGTH_SHORT).show();



                        // masukin ke sign in
                        Intent i = new Intent(getApplication(), HomeActivity.class);
                        startActivity(i);

                        // menyatakan selesai
                        finish();

                    }

                } catch (Exception ex) {
                    Log.e("Error: ", ex.toString());
                    ex.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(DetailBookActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Log.e("Error: ", error.getMessage());


            }
        }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("user_id", id_data_User);
                params.put("book_id", id_buku);
                params.put("qty", String.valueOf(qty)); // default
                params.put("price", harga_buku);

                return params;

            }
        };

        RequestHandler.getInstance((Context) this).addToRequestQueue(request);

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_add_cart){
            //getSupportFragmentManager().beginTransaction().replace(R.id.container, new CartItemFragment()).commit();

           // pengubahan();
            memasukkan_kedalam_cart();



//            startActivity(new Intent(this,HomeActivity.class));
//            Toast.makeText(DetailBookActivity.this, "Cool, you have successfully added to your cart!", Toast.LENGTH_SHORT).show();
////            fragmentTransaction.addToBackStack(frag_no); //add fragment to stack
////            fragmentTransaction.hide(currentFragment).commit();  // hide current fragment
        }

    }
}