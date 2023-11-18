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
import com.example.iclean.adapter.User;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class SignUpActivity extends AppCompatActivity {
    // Melakukan inisialisasi awal
    Button btnSignUp;
    TextView tvBtnSignIn;
    TextView tvErrorText;
    EditText edtName, edtEmail, edtPass, edtPhone;
    String nama, email, passwordw, nohp;

    // Membuat ketentuan pada nama
    final Pattern NAMA_PATTERN =
            Pattern.compile("^" +
                    ".{5,}"               //at least 5 characters

    );

    // Membuat ketentuan pada no telepon
    final Pattern telepon_PATTERN =
            Pattern.compile("^" +
                    ".{9,}"               //at least 9 characters

            );

    // Membuat ketentuan pada password
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
        setContentView(R.layout.activity_sign_up);

        // SqLite database handler
        dbsqlite = new SQLite(getApplicationContext());

        inisialisasi();
        getSupportActionBar().hide();
        Sign_in_Button();

        User user;
    }

    private void inisialisasi() {

        edtName = findViewById(R.id.edt_addresss);
        edtEmail = findViewById(R.id.edt_email);
        edtPass = findViewById(R.id.edt_password);
        edtPhone = findViewById(R.id.edt_phone);

        btnSignUp = findViewById(R.id.btn_sign_up);
        tvBtnSignIn = findViewById(R.id.tv_btn_signin);
        tvErrorText = findViewById(R.id.tv_errortext);

    }

    public void Sign_Up(View view) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");

        // Menampung variabel yang telah diubah
        pengubahan();

        // Memeriksa nama jika tidak terdapat nama, maka akan keluar notifikasi
        if (nama.length()<1) {

            tvErrorText.setText("Name field is required!");
            edtName.requestFocus();

            return;

        }
        // Jika username kurang dari 5 karakter, maka akan masuk pada class dibawah
        if (! (NAMA_PATTERN).matcher(nama).matches()){

           registernama(edtName);

           return;
        }

        // Jika tidak terdapat email, maka akan turun mengambil class dibawah
        if (email.isEmpty()) {
           registerEmail(edtEmail); // ambil kebawah
           return;

        }

        // Jika email tidak sesuai  ketentuan
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

           tvErrorText.setText("The email address you entered is not valid ! ");
           edtEmail.requestFocus();
            return;

        }
        // Jika nomor telepon hanya menampilkan 1 angka
        if (nohp.length()<1) {
            
            tvErrorText.setText("Phone number field is required!");
            
            edtPhone.requestFocus();
            return;
        }

        if (nohp.length()>12){
            tvErrorText.setText("Your Phone number is too long!");

            edtPhone.requestFocus();
            return;
        }

        // Jika nomor telepon tidak sesuai ketentuan maka akan menampilkan pesan
        if (! (telepon_PATTERN).matcher(nohp).matches()) {
            tvErrorText.setText("The phone number you entered is not valid!");
            edtPhone.requestFocus();
            return;
        }


        // Jika password tidak cocok dengan ketentuan maka akan menampilkan pesan
        if (edtPass.length()<1) {

            tvErrorText.setText("Password field is required!");

            edtPass.requestFocus();
            return;

        }


        // Jika password tidak cocok dengan ketentuan maka akan menampilkan pesan
        if (! (PASSWORD_PATTERN).matcher(passwordw).matches()){
            tvErrorText.setText("At least with 8 Characters," +
                    " 1 Lower case and 1 Upper case and have 1 Special character in your Password !");
            edtPass.requestFocus();
            //return;
        }
        else {

            progressDialog.show();
            pengubahan();

            StringRequest request = new StringRequest(Request.Method.POST, Constant.URL_REGISTER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jObj = new JSONObject(response);

                        // ambil dari database ( json)
                        int sukses = jObj.getInt("success");
                        String pesan = jObj.getString("message");

                        //This email " . $email . "is already registered ! , Try another one" ;
                        if (sukses == -1) {

                            progressDialog.dismiss();
                            tvErrorText.setText(pesan);
                            edtEmail.requestFocus();

                            return;

                        }

                       // jika akun gagal didaftarkan
                        if (sukses == -2){

                            progressDialog.dismiss();
                            tvErrorText.setText(pesan);
                            edtEmail.requestFocus();
                            return;

                        }
                        // Jika terdapat kesalahan dalam pendaftaran
                        if (sukses == -3) {

                            progressDialog.dismiss();
                            tvErrorText.setText(pesan);
                            edtEmail.requestFocus();
                            return;
                        }


                        else{
                            progressDialog.dismiss();

                            edtName.setText("");
                            edtEmail.setText("");
                            edtPass.setText("");
                            edtPhone.setText("");
                            Toast.makeText(SignUpActivity.this, pesan, Toast.LENGTH_SHORT).show();

                            dbsqlite.addUser(nama, email, passwordw);

                            // Melakukan sign in
                            Intent i = new Intent(getApplication(), SignInActivity.class);
                            startActivity(i);

                            finish();

                        }

                    } catch (Exception ex) {
                        Log.e("Error: ", ex.toString());
                        ex.printStackTrace();

                    }


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) { // jika eror
                    Log.e("Error: ", error.getMessage());

                    Toast.makeText(SignUpActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            ) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("name", nama);
                    params.put("email", email);
                    params.put("password", passwordw);
                    params.put("nohp", nohp);

                    return params;

                }
            };

            // baru di request
            RequestHandler.getInstance(this).addToRequestQueue(request);

       }
    }

    private boolean pengubahan(){

        nama = edtName.getText().toString().trim();
        email = edtEmail.getText().toString().trim();
        passwordw = edtPass.getText().toString().trim();
        nohp = edtPhone.getText().toString().trim();
        return false;
    }

    private void registernama(EditText edtName) {

        pengubahan();

        tvErrorText.setText("A Minimum of 5 letters is required for the FullName !");
        edtName.requestFocus();
        return;

    }

    private boolean registerEmail(EditText edtEmail) {

        pengubahan();

        if (email.length()<5) {
            tvErrorText.setText("Email field is required ! ");
            edtEmail.requestFocus();
            return false;
        }

        else {
            tvErrorText.setText("");
            edtEmail.requestFocus();
            return true;
        }
    }

    private void Sign_in_Button() {

        // Untuk berpindah ke sign in activity
        tvBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), SignInActivity.class);
                startActivity(i);
            }
        });
    }

}


