package com.example.buku.activity;

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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.buku.Handler.Constant;
import com.example.buku.Handler.RequestHandler;
import com.example.buku.Handler.SQLite;
import com.example.buku.R;
import com.example.buku.adapter.Session;
import com.example.buku.auth.SignInActivity;
import com.example.buku.fragment.ProfileFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ChangeProfileActivity extends AppCompatActivity {

    EditText edtName, edtEmail, edtPhone, edtAddress;
    Button btnSave;
    ImageView imgBack;
    String pesan;
    TextView TULISAN_EROR;
    private SQLite dbsqlite;


    // pembatasan buat nama
    final Pattern NAMA_PATTERN =
            Pattern.compile("^" +
                    ".{5,}"               //at least 5 characters

            );

    // pembatasan buat no telepon
    final Pattern telepon_PATTERN =
            Pattern.compile("^" +
                    ".{9,}"               //at least 9 characters

            );



    String name, email, noHP_USER, hape, id_data_User;
    String namaa, emaill, id_sql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);


        inisialisasi();

        // ambil data dari sql lite untuk isi nama biar bisa nyocokin update dibawah
        dbsqlite = new SQLite(getApplicationContext());
        HashMap<String, String> user = dbsqlite.getUserDetails();

        // dan yang diambil adalah data nama dan email dari SQLite
        name = user.get("nama");
        email = user.get("email");
        id_sql = user.get("id");


        // ambil dari home activity buat ambil data bawaan berupa nomer hp yang berada pada session!
        HomeActivity activity = new HomeActivity();

        noHP_USER = activity.getUSERDATA_NOHAPE();
        id_data_User = activity.getUSERDATA_ID();


        // masukkin kedalam textnya biar ga koosong!
        edtName.setText(name);
        edtEmail.setText(email);
        edtPhone.setText(noHP_USER);

        // dia buat tombol kembali
        mainButton();
        getSupportActionBar().hide();


    }
    private void inisialisasi() {

        //inisialisasi();// init awal semua data yang dibutuhin
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPhone = findViewById(R.id.edt_phone);
        edtAddress = findViewById(R.id.edt_address);

        TULISAN_EROR = findViewById(R.id.tv_errortext_UPDATE);
        btnSave = findViewById(R.id.btn_save);
        imgBack = findViewById(R.id.img_back);


    }

    private boolean pengubahan(){

        //get text ye bos

        namaa = edtName.getText().toString().trim();
        emaill = edtEmail.getText().toString().trim();
        hape = edtPhone.getText().toString().trim();

        return false;
    }

    public void SAVE_BTN(View view){

        //PENGECEKAN ketika nama kosong DLL dia keluar notif !

        pengubahan();

        if (namaa.length()<1) {

            TULISAN_EROR.setText("Name field is required!");
            edtName.requestFocus();

            return;

        }
        // KETIKA USERNAME LEBIH KECIL DARI 5 MAKA MASUK KE CLASS DIBAWAH YANG MENGURUSNYA
        if (! (NAMA_PATTERN).matcher(namaa).matches()){

            TULISAN_EROR.setText("A Minimum of 5 letters is required for the FullName !");
            edtName.requestFocus();

            return;
        }

        // JIKA EMAIL KOSONG MAKA DIA TURUN MENEGAMBIL CLASS YANG MENGURUSNYA DIBAWAH
        if (emaill.isEmpty()) {
            if (emaill.length()<5) {
                TULISAN_EROR.setText("Email field is required ! ");
                edtEmail.requestFocus();

            }

            return;

        }

        // JIKA EMAIL TIDAK SESUAI DENGAN KETENTUAN EMAIL SECARA GLOBAL SEMISAL
        // @BLABLA.COM DIA AKAN MENGELUARKAN NOTIF
        if (!Patterns.EMAIL_ADDRESS.matcher(emaill).matches()){

            TULISAN_EROR.setText("The email address you entered is not valid ! ");
            edtEmail.requestFocus();
            return;

        }
        // jika NO  TELP ANKGANYA CUMAN 1 DIA MENAMPILKAN PESAN!
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

        // JIKA NO TELPON TIDAK SESUAI DENGAN KETENTUAN YAITU NGACO NOMERNYA ( MINIMAL 8 DIGIT )
        // MAKA DIA EROR (MENAMPILKAN PESAN)
        if (! (telepon_PATTERN).matcher(hape).matches()) {
            TULISAN_EROR.setText("The phone number you entered is not valid!");
            edtPhone.requestFocus();
            return;
        }

        else{

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

//                            String namauser = jsonObject.getString("namee"); // ambil dari database ( json)
//                            String emailuser = jsonObject.getString("email");
//                            String nohpuser = jsonObject.getString("nohp");
//                            String id_user = jsonObject.getString("id_user123"); // ambil dari database ( json)

                                int sukses = jsonObject.getInt("success");
                                pesan = jsonObject.getString("message");

                                if (sukses == -1){ // email udah ada yang pakek jadi ga bisa update datanya

                                    progressDialog.dismiss();

                                    TULISAN_EROR.setText(pesan);

                                    edtEmail.requestFocus();

                                    return;

                                }

                                if (sukses == 0){ // data gagal diupdate karena jaringan ataupun karena masalah lainnya

                                    progressDialog.dismiss();
                                    TULISAN_EROR.setText(pesan);

                                    edtEmail.requestFocus();
                                    edtName.requestFocus();

                                    edtPhone.requestFocus();

                                    return;

                                }

                                if (sukses == -2 ){ // datanya kosong atau gagal untuk di update karena kosong
                                    progressDialog.dismiss();
                                    TULISAN_EROR.setText(pesan);

                                    edtEmail.requestFocus();
                                    edtName.requestFocus();

                                    edtPhone.requestFocus();
                                }

                                if (sukses == 1) { // data berhasil diupdate

                                    // inside on response method we are
                                    // setting our edit text to empty.
                                    edtName.setText("");
                                    edtEmail.setText("");
                                    edtPhone.setText("");


                                    // on below line we are displaying a toast message as data updated.
                                    Toast.makeText(ChangeProfileActivity.this, "Data Updated..", Toast.LENGTH_SHORT).show();


                                   // dbsqlite.deleteUsers();
                                    dbsqlite.updateUser(id_sql, namaa, emaill);

                                    // masukin session ye ges !
                                    Session.getInstance(getApplicationContext())
                                            .userUpdated(
                                                    hape

                                            );

                                    //membuka activity baru buat refresh
                                    Intent i = new Intent(getApplication(), HomeActivity.class);
                                    startActivity(i);


                                    // menyatakan selesai

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
                public void onErrorResponse(VolleyError error) {
                    // displaying toast message on response failure.
                    Toast.makeText(ChangeProfileActivity.this, "Fail to update data..", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();


                }
            }) {
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

            RequestHandler.getInstance(this).addToRequestQueue(request);


        }

    }

    private void mainButton(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), HomeActivity.class);
                startActivity(i);
            }
        });
    }



}