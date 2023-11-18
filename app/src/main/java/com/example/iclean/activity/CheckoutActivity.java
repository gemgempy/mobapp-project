package com.example.iclean.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.iclean.Handler.Constant;
import com.example.iclean.Handler.RequestHandler;
import com.example.iclean.R;
import com.example.iclean.dto.CucianCartDTO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {

    // Inisial untuk edit
    TextView Total_payment_id,tv_summary_item;
    Button btnCheckout;
    EditText edt_address;

    String HARGA_KESELURUHAN;
    String TOTAL_CUCIAN_YANG_DI_BELI;

    ImageView img;
    String address;
    Bitmap bitmap;

    String id_data_User;

    CucianCartDTO cucianCartDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // intent untuk buat data
        cucianCartDTO = (CucianCartDTO) getIntent().getSerializableExtra("data_123");

        setContentView(R.layout.activity_checkout);
        inisialisasi();

        mainButton();
        getSupportActionBar().hide();

        img = findViewById(R.id.image_piew);


        // buat mengambil gambar untuk proses upload di checkout


        ActivityResultLauncher<Intent> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if(result.getResultCode() == Activity.RESULT_OK) {

                            Intent data = result.getData();

                            Uri uri = data.getData();

                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                img.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                activityResultLauncher.launch(intent);
            }
        });



    }

    private boolean pengubahan(){

        address = edt_address.getText().toString().trim();

        return false;
    }


    private void inisialisasi(){ // deklarasi variable

        btnCheckout = findViewById(R.id.btn_checkout);


        Total_payment_id = findViewById(R.id.Total_payment_id);
        tv_summary_item = findViewById(R.id.Summary_item_id);

        Intent intent = getIntent();
        HARGA_KESELURUHAN = intent.getStringExtra("loadsPosition");
        TOTAL_CUCIAN_YANG_DI_BELI = intent.getStringExtra("loads_summary");

        Total_payment_id.setText("Rp." +HARGA_KESELURUHAN + "00");

        tv_summary_item.setText(TOTAL_CUCIAN_YANG_DI_BELI);

        edt_address = findViewById(R.id.edt_addresss);


        HomeActivity activity = new HomeActivity();

        id_data_User = activity.getUSERDATA_ID(); // buat id_user



    }

    private void mainButton(){

        // listener button checkout

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pengubahan();

                if (edt_address.length()<1) { // toast untuk input data yg benar


                    edt_address.requestFocus();

                    Toast.makeText(CheckoutActivity.this,"Please input your address correctly",Toast.LENGTH_SHORT).show();

                }

                else{

                    // menjalankan proses upload gambar
                    kedalam_transaction_item();
                    upload_gambar();

                }

            }
        });

    }



    public void kedalam_transaction_item(){



        //library volley buat hubungkan api ke database
        StringRequest request = new StringRequest(Request.Method.POST, Constant.url_upload_bukti_pembayaran_bukan_gambar, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);

                    // passing value json object untuk mendapatkan nilai dari api yang telah dibuat
                    int sukses = jObj.getInt("success");
                    String pesan = jObj.getString("message");


                    if (sukses == -1) {


                        Toast.makeText(CheckoutActivity.this, pesan, Toast.LENGTH_SHORT).show();

                        return;

                    }

                    // toast gagal registrasi
                    if (sukses == -2){


                        Toast.makeText(CheckoutActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        return;

                    }

                    // toast gagal pengisian cart
                    if (sukses == -3) {


                        Toast.makeText(CheckoutActivity.this, pesan, Toast.LENGTH_SHORT).show();

                        return;
                    }

                } catch (Exception ex) {
                    Toast.makeText(CheckoutActivity.this, "Something Wrong with your connection", Toast.LENGTH_SHORT).show();

                    Log.e("Error: ", ex.toString());
                    ex.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CheckoutActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                Log.e("Error: ", error.getMessage());


            }
        }

        ) {
            //method untuk masukin data inputan aplikasi ke api, caranya dengan mencocokkan dengan apa yg ytelah dideklarasikan senhingga terhubung ke database
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();



                params.put("cucian_id", cucianCartDTO.id);

                params.put("user_id", id_data_User);

                params.put("cart_id", cucianCartDTO.id_cart);

                params.put("qty", TOTAL_CUCIAN_YANG_DI_BELI);

                params.put("total_price", HARGA_KESELURUHAN);
                params.put("address", address);

                return params;

            }
        };

        RequestHandler.getInstance((Context) this).addToRequestQueue(request);

    }

    // upload gambar payment
    public void upload_gambar(){


        //deklarasi variable
        ByteArrayOutputStream byteArrayOutputStream;
        byteArrayOutputStream = new ByteArrayOutputStream();


        if(bitmap != null) {
            //bitmap yang diambil dari storage
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte [] bytes = byteArrayOutputStream.toByteArray();
            final  String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait..");

            // progress upload tidak bisa dicancel
            progressDialog.setCancelable(false);
            progressDialog.show();



            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.url_upload_bukti_pembayaran,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {


                                JSONObject jObj = new JSONObject(response);
                                int sukses = jObj.getInt("success");

                                if (sukses == 1) {

                                    Toast.makeText(getApplicationContext(), "Upload Payment Success ^.^", Toast.LENGTH_SHORT).show();

                                    progressDialog.dismiss();

                                    Intent i = new Intent(getApplication(), HomeActivity.class);
                                    startActivity(i);

                                } else

                                    Toast.makeText(CheckoutActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                protected Map<String, String> getParams(){
                    Map<String, String> paramV = new HashMap<>();
                    paramV.put("img", base64Image);
                    paramV.put("user_id",id_data_User);
                    paramV.put("total_harga",HARGA_KESELURUHAN);

                    return paramV;
                }
            };


            //digunakan untuk handle internet slow, jadi dia timeout di  x 0
            //agar tidak terjadi bug
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES -1,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ));


            Volley.newRequestQueue(this).add(stringRequest);
        }
        else {
            Toast.makeText(getApplicationContext(), "Select Image First! ", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}