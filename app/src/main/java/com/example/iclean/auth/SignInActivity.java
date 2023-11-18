package com.example.iclean.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.iclean.activity.HomeActivity;
import com.example.iclean.adapter.Session;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {

    // Untuk melakukan inisialisasi awal pada text view
    Button btnSignIn;
    TextInputEditText edtEmail, edtPassword;
    TextView tvBtnSignup;
    TextView tvErrorTextLogin;

    String email, passwordw;
    String pesan;


    // Untuk melakukan pembatasan pada nama
    final Pattern NAMA_PATTERN =
            Pattern.compile("^" +
                    ".{5,}"               //at least 5 characters

            );

    // pembatasan buat password
    final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$");

    private SQLite dbsqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Database handler pada SQLite
        dbsqlite = new SQLite(getApplicationContext());

        // Setelah memeriksa pengguna ini masuk atau tidak, saat masuk akan ke activity home
        // Jika tidak, maka pengguna akan tetap berada pada halaman log in
        if(Session.getInstance(this).isLoggedin()){
            finish();
            startActivity(new Intent(this, HomeActivity.class));
           // return;
        }

        inisialisasi();
        // Menghilangkan support action pada bar
        getSupportActionBar().hide();
        Sign_up_Button();

    }

    private void inisialisasi() {
        btnSignIn = findViewById(R.id.btn_sign_in);//ga gw pake karena gw lanngsung pake di xml ( Sign_in)
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);

        tvBtnSignup = findViewById(R.id.tv_btn_signup);

        tvErrorTextLogin = findViewById(R.id.tv_errortext_login);

    }

    public void Sign_In (View view) {

        // Untuk menampung variabel yang telah diubah
        pengubahan();

        // Jika email/username tidak sesuai dengan ketentuan yang berlaku, maka akan menampilkan pesan
        if (email.isEmpty()) {
            LoginEmail(edtEmail); // ambil kebawah
            return;

        }

        // Jika password tidak sesuai dengan ketentuan yang berlaku, maka akan menampilkan pesan
        if (! NAMA_PATTERN.matcher(email).matches()) {

            tvErrorTextLogin.setText("The email address or full name you entered is invalid !");
            edtEmail.requestFocus();

            return;
        }


        // Jika password tidak sesuai dengan ketentuan yang berlaku, maka akan menampilkan pesan notif
        if (edtPassword.length()<1) {

            tvErrorTextLogin.setText("Password field is required!");

            edtPassword.requestFocus();
            return;

        }

        // Jika password tidak sesuai dengan ketentuan yang berlaku, maka akan menampilkan pesan notif
        if (! (PASSWORD_PATTERN).matcher(passwordw).matches()){
            tvErrorTextLogin.setText("At least with 8 Characters," +
                    " 1 Lower case and 1 Upper case and have 1 Special character in your Password !");
            edtPassword.requestFocus();
        }


        // Jika SQlite belum memiliki data yaitu email dan password, maka akan menjalankan ke public void dengan menjalankan aksi 1
        if(!dbsqlite.checkUser(email, passwordw)){

            menjalankan_aksi1();

        }

       else {
           // jika  memiliki data berupa email dan password ini, dia akan  menjalankan ke public void menjalankan aksi 2


            menjalankan_aksi2();
       }

    }


    public void menjalankan_aksi1(){ // Jika sqlite belum menyimpan data email dn password
        // dialog progres, menampilkan progres dari setiap tugas yang sedang berjalan atau operasi. Dialog Peringatan
        // adalah jenis pesan peringatan yang ditampilkan di layar yang memungkinkan pengguna
        // memilih di antara opsi untuk merespons pesan peringatan.

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        pengubahan();

        // Mulai gunakan library volley di bawah ini untuk terhubung ke api dan kemudian ke database,
        // di bawah ini gunakan metode posting berarti metode Permintaan HTTP digunakan untuk
        // menghasilkan data baru dengan memasukkan data ke dalam tubuh saat permintaan dibuat

        StringRequest request = new StringRequest(Request.Method.POST, Constant.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);


                    // Mengambil JSON OBject untuk mendapatkan data dari API

                    String namauser = jObj.getString("namee"); // ambil dari database ( json)
                    String emailuser = jObj.getString("email");
                    String nohpuser = jObj.getString("nohp");
                    String id_user = jObj.getString("id_user123"); // ambil dari database ( json)

                    int sukses = jObj.getInt("success");
                    pesan = jObj.getString("message");

                    if (sukses == -1){ // jika respon - 1 maka akan mengeluarkan pesan

                        progressDialog.dismiss();
                        tvErrorTextLogin.setText(pesan);
                        edtEmail.requestFocus();
                        edtPassword.requestFocus();

                        return;

                    }

                    if (sukses == -2){

                        progressDialog.dismiss();
                        tvErrorTextLogin.setText(pesan);
                        edtEmail.requestFocus();
                        edtPassword.requestFocus();

                        return;

                    }


                    if (sukses == 1) {

                        progressDialog.dismiss();

                        edtEmail.setText("");
                        edtPassword.setText("");
                        tvErrorTextLogin.setText("");


                        Toast.makeText(SignInActivity.this, pesan, Toast.LENGTH_SHORT).show();

                        // Memasukkan data ke SQLite
                      dbsqlite.addUser(namauser, emailuser, passwordw);

                        // Untuk berpindah ke halaman home page
                        Intent i = new Intent(getApplication(), HomeActivity.class);
                        startActivity(i);

                        // Memasukkan session
                        Session.getInstance(getApplicationContext())
                                .userlogin(
                                        id_user, namauser, emailuser, nohpuser
                                );

                        finish();

                    }


                } catch (Exception ex) { // jika eror maka mengeluarkan pesan
                    progressDialog.dismiss();

                    tvErrorTextLogin.setText("Login not matched. Please try again!");
                    Log.e("Error: ", ex.toString());
                    ex.printStackTrace();
                    Log.e("anyText",response);

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {// jika eror
                Log.e("Error: ", error.getMessage());

                Toast.makeText(SignInActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        ) {

            // Metode di bawah ini digunakan untuk memasukkan input aplikasi ke dalam api,
            // mencocokkan data yang dideklarasikan dalam metode post di api (php),
            // sehingga aplikasi dapat terhubung ke api dan dapat memberi tahu database bahwa ada insert baru .
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                // Jika login menggunakan email
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    params.put("email",email);
                    params.put("password", passwordw);

                }

                else {

                    // Jika login menggunakan nama lengkap atau username
                    params.put("name", email);
                    params.put("password", passwordw);

                }
                return params;

            }

        };


        RequestHandler.getInstance(this).addToRequestQueue(request);

    }


    public void menjalankan_aksi2(){// kalo sqlite blom ada data email dn password

        // Mulai gunakan library volley di bawah ini untuk terhubung ke api dan kemudian ke database,
        // di bawah ini gunakan metode posting berarti metode Permintaan HTTP digunakan untuk
        // menghasilkan data baru dengan memasukkan data ke dalam tubuh saat permintaan dibuat
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        pengubahan();

        StringRequest request = new StringRequest(Request.Method.POST, Constant.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response); // ambil respon dari json

                    String namauser = jObj.getString("namee"); // ambil dari database ( json)
                    String emailuser = jObj.getString("email");
                    String nohpuser = jObj.getString("nohp");
                    String id_user = jObj.getString("id_user123"); // ambil dari database ( json)

                    int sukses = jObj.getInt("success");
                    pesan = jObj.getString("message");

                    if (sukses == -1){
                        progressDialog.dismiss();
                        tvErrorTextLogin.setText(pesan);
                        edtEmail.requestFocus();
                        edtPassword.requestFocus();

                        return;

                    }

                    if (sukses == -2){

                        progressDialog.dismiss();
                        tvErrorTextLogin.setText(pesan);
                        edtEmail.requestFocus();
                        edtPassword.requestFocus();

                        return;
                    }

                    if (sukses == 1) {

                        progressDialog.dismiss();

                        edtEmail.setText("");
                        edtPassword.setText("");
                        tvErrorTextLogin.setText("");

                        Toast.makeText(SignInActivity.this, pesan, Toast.LENGTH_SHORT).show();


                        Intent i = new Intent(getApplication(), HomeActivity.class);
                        startActivity(i);

                        Session.getInstance(getApplicationContext())
                                .userlogin(
                                        id_user, namauser, emailuser, nohpuser

                                );

                        finish();

                    }


                } catch (Exception ex) {
                    progressDialog.dismiss();

                    tvErrorTextLogin.setText("Login not matched. Please try again!");
                    Log.e("Error: ", ex.toString());
                    ex.printStackTrace();
                    Log.e("anyText",response);

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());


                Toast.makeText(SignInActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        ) {
            // Metode ini digunakan untuk memasukkan data input aplikasi ke api, cocok dengan apa yang
            // dideklarasikan dalam metode posting di api (php-nya), sehingga dapat terhubung di api
            // dan dapat meneruskannya ke database dengan insert baru

           @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                // kondisi dibawah ini digunakan apabila login dengan menggunakan email
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    params.put("email",email);
                    params.put("password", passwordw);

                }

                else {

                    // Jika ingin login menggunakan nama panjang atau username
                    params.put("name", email);
                    params.put("password", passwordw);

                }
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(request);
    }

    private boolean pengubahan(){

        email = edtEmail.getText().toString().trim();
        passwordw = edtPassword.getText().toString().trim();

        return false;
    }
    private boolean LoginEmail(EditText edtEmail) {

        pengubahan();

        if (email.length()<5) {
            tvErrorTextLogin.setText("Email field is required ! ");
            edtEmail.requestFocus();
            return false;
        }

        else {
            tvErrorTextLogin.setText("");
            edtEmail.requestFocus();
            return true;
        }

    }

    private void Sign_up_Button() {

        tvBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), SignUpActivity.class);
                startActivity(i);
            }
        });
    }
}