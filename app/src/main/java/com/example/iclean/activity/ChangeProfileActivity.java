package com.example.iclean.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.iclean.Handler.SQLite;
import com.example.iclean.R;
import com.example.iclean.adapter.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ChangeProfileActivity extends AppCompatActivity {

    // Untuk edit text

    EditText edtName, edtEmail, edtPhone, edtAddress;
    Button btnSave;
    ImageView imgBack;
    String pesan;
    TextView TULISAN_EROR;

    // panggil sqlite
    private SQLite dbsqlite;


    // batas jumlah karater untuk nama
    final Pattern NAMA_PATTERN =
            Pattern.compile("^" +
                    ".{5,}"               //paling sedikit 5 karakter

            );

    // batas jumlah karakter untuk nama
    final Pattern telepon_PATTERN =
            Pattern.compile("^" +
                    ".{9,}"               //paling sedikit 9 karakter

            );

    String name, email, noHP_USER, hape, id_data_User;
    String namaa, emaill, id_sql;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);



        inisialisasi();

        // mengambil data dari sqlite untuk mengisi nama
        dbsqlite = new SQLite(getApplicationContext());
        HashMap<String, String> user = dbsqlite.getUserDetails();

        // mengambil data nama dan email dari SQLite
        name = user.get("nama");
        email = user.get("email");
        id_sql = user.get("id");


        // mengambil data untuk session
        HomeActivity activity = new HomeActivity();

        noHP_USER = activity.getUSERDATA_NOHAPE();
        id_data_User = activity.getUSERDATA_ID();


        // memasukan data agar tidak kosong
        edtName.setText(name);
        edtEmail.setText(email);
        edtPhone.setText(noHP_USER);

        //tombol kembali
        mainButton();
        getSupportActionBar().hide();


    }
    private void inisialisasi() {

        //inisialisasi
        edtName = findViewById(R.id.edt_addresss);
        edtEmail = findViewById(R.id.edt_email);
        edtPhone = findViewById(R.id.edt_phone);

        TULISAN_EROR = findViewById(R.id.tv_errortext_UPDATE);
        btnSave = findViewById(R.id.btn_save);
        imgBack = findViewById(R.id.img_back);

    }

    private boolean pengubahan(){



        namaa = edtName.getText().toString().trim();
        emaill = edtEmail.getText().toString().trim();
        hape = edtPhone.getText().toString().trim();

        return false;
    }

    public void SAVE_BTN(View view){

        //check ketika field kosong maka ada toast

        pengubahan();

        if (namaa.length()<1) {

            TULISAN_EROR.setText("Name field is required!");
            edtName.requestFocus();

            return;

        }
        // toast untuk masukin 5 karakter
        if (! (NAMA_PATTERN).matcher(namaa).matches()){

            TULISAN_EROR.setText("A Minimum of 5 letters is required for the FullName !");
            edtName.requestFocus();

            return;
        }

        // toast untuk field email
        if (emaill.isEmpty()) {
            if (emaill.length()<5) {
                TULISAN_EROR.setText("Email field is required ! ");
                edtEmail.requestFocus();

            }

            return;

        }

        // toast jika email invalid
        if (!Patterns.EMAIL_ADDRESS.matcher(emaill).matches()){

            TULISAN_EROR.setText("The email address you entered is not valid ! ");
            edtEmail.requestFocus();
            return;

        }
        // toast no hp
        if (hape.length()<1) {

            TULISAN_EROR.setText("Phone number field is required!");

            edtPhone.requestFocus();
            return;
        }

        if (hape.length()>12){
            TULISAN_EROR.setText("Your Phone number is too long!");

            edtPhone.requestFocus();
            return;
        }

        // toast jika no hp ga valid
        if (! (telepon_PATTERN).matcher(hape).matches()) {
            TULISAN_EROR.setText("The phone number you entered is not valid!");
            edtPhone.requestFocus();
            return;
        }

        else{

            // progress dialog

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Updating.....");
            progressDialog.show();
            pengubahan();


            StringRequest request = new StringRequest(Request.Method.POST, Constant.URL_UPDATE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //adapter.notifyDataSetChanged();
                            try {
                                JSONObject jsonObject = new JSONObject(response);


                                // get json object untuk mendapatkan nilai dari api
                                int sukses = jsonObject.getInt("success");
                                pesan = jsonObject.getString("message");

                                if (sukses == -1){ // email yang sudah dipakai tidak bisa dipakai

                                    progressDialog.dismiss();

                                    TULISAN_EROR.setText(pesan);

                                    edtEmail.requestFocus();

                                    return;

                                }

                                if (sukses == 0){ // tulisan error

                                    progressDialog.dismiss();
                                    TULISAN_EROR.setText(pesan);

                                    edtEmail.requestFocus();
                                    edtName.requestFocus();

                                    edtPhone.requestFocus();

                                    return;

                                }

                                if (sukses == -2 ){ // gagal update karena data kosong
                                    progressDialog.dismiss();
                                    TULISAN_EROR.setText(pesan);

                                    edtEmail.requestFocus();
                                    edtName.requestFocus();

                                    edtPhone.requestFocus();
                                }

                                if (sukses == 1) { // data berhasil diupdate

                                    edtName.setText("");
                                    edtEmail.setText("");
                                    edtPhone.setText("");



                                    Toast.makeText(ChangeProfileActivity.this, "Data Updated..", Toast.LENGTH_SHORT).show();



                                    dbsqlite.updateUser(id_sql, namaa, emaill);

                                    // untuk session

                                    Session.getInstance(getApplicationContext())
                                            .userUpdated(
                                                    hape

                                            );

                                    //membuka activity baru
                                    Intent i = new Intent(getApplication(), HomeActivity.class);
                                    startActivity(i);


                                    finish();
                                    progressDialog.dismiss();

                                }
                            }
                            catch (JSONException jsonObject) {
                                jsonObject.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) { // toast ketika data gagal diupdate
                    Toast.makeText(ChangeProfileActivity.this, "Fail to update data..", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }) {

                //Method buat masukin data inputan ke api, dengan mencocokkan deklarasi method post di api php sehingga dapat terhubung ke database
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();

                    params.put("id", id_data_User);
                    params.put("nama", namaa);
                    params.put("email", emaill);
                    params.put("nohp", hape);

                    return params;
                }
            };
            //jalan dari sini
            RequestHandler.getInstance(this).addToRequestQueue(request);
        }
    }
    private void mainButton(){

        // tombol back
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), HomeActivity.class);
                startActivity(i);
            }
        });
    }
}