package com.example.buku.auth;

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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.buku.Handler.Constant;
import com.example.buku.Handler.RequestHandler;
import com.example.buku.Handler.SQLite;
import com.example.buku.R;
import com.example.buku.activity.HomeActivity;
import com.example.buku.adapter.Session;
import com.example.buku.adapter.User;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class SignUpActivity extends AppCompatActivity {


    Button btnSignUp;
    TextView tvBtnSignIn;
    TextView tvErrorText;

    EditText edtName, edtEmail, edtPass, edtPhone;

    String url_tambah_anggota = "http://172.31.75.173/MOBILE%20APPLICATION/BUAT_UAS/Register_Percobaan.php";
    String nama, email, passwordw, nohp;

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
        setContentView(R.layout.activity_sign_up);

        // SqLite database handler
        dbsqlite = new SQLite(getApplicationContext());

        inisialisasi();
        getSupportActionBar().hide();
        Sign_in_Button();

        User user;
    }
    // perapihan
    private void inisialisasi() {

        edtName = findViewById(R.id.edt_name);

        edtEmail = findViewById(R.id.edt_email);

        edtPass = findViewById(R.id.edt_password);

        edtPhone = findViewById(R.id.edt_phone);

        btnSignUp = findViewById(R.id.btn_sign_up); // gw taroh di SignUp(LAYOUT)
        tvBtnSignIn = findViewById(R.id.tv_btn_signin);
        tvErrorText = findViewById(R.id.tv_errortext);

        //Calendar calendar = Calendar.getInstance();
        // String CurrentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

    }

    public void Sign_Up(View view) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");

        // PENAMPUNGAN VARIABLE YANG TELAH DIRUBAH DIBAWAH
        pengubahan();

        //PENGECEKAN ketika nama kosong dia keluar notif !

        if (nama.length()<1) {

            tvErrorText.setText("Name field is required!");
            edtName.requestFocus();

            return;

        }
        // KETIKA USERNAME LEBIH KECIL DARI 5 MAKA MASUK KE CLASS DIBAWAH YANG MENGURUSNYA
        if (! (NAMA_PATTERN).matcher(nama).matches()){

           registernama(edtName);

           return;
        }

        // JIKA EMAIL KOSONG MAKA DIA TURUN MENEGAMBIL CLASS YANG MENGURUSNYA DIBAWAH
        if (email.isEmpty()) {
           registerEmail(edtEmail); // ambil kebawah
           return;

        }

        // JIKA EMAIL TIDAK SESUAI DENGAN KETENTUAN EMAIL SECARA GLOBAL SEMISAL
        // @BLABLA.COM DIA AKAN MENGELUARKAN NOTIF
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

           tvErrorText.setText("The email address you entered is not valid ! ");
           edtEmail.requestFocus();
            return;

        }
        // jika NO  TELP ANKGANYA CUMAN 1 DIA MENAMPILKAN PESAN!
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

        // JIKA NO TELPON TIDAK SESUAI DENGAN KETENTUAN YAITU NGACO NOMERNYA ( MINIMAL 8 DIGIT )
        // MAKA DIA EROR (MENAMPILKAN PESAN)
        if (! (telepon_PATTERN).matcher(nohp).matches()) {
            tvErrorText.setText("The phone number you entered is not valid!");
            edtPhone.requestFocus();
            return;
        }

        // JIKA PASSWORD TIDAK SESUAI DENGAN KETENTUAN YAITU DIKIT ISINYA (MINIMAL 8 KARAKTER)
        // MAKA DIA EROR (MENAMPILKAN PESAN)
        if (edtPass.length()<1) {

            tvErrorText.setText("Password field is required!");

            edtPass.requestFocus();
            return;

        }

        // JIKA PASSWROD TIDAK SESUAI DENGAN KETENTUAN DIA NGACO ISINYA
        //MAKA PESAN NOTIF AKAN KELUAR
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

                        String created_at = jObj.getString("created_att"); // ambil dari database ( json)

                        int sukses = jObj.getInt("success");
                        String pesan = jObj.getString("message");

                        //This email " . $email . "is already registered ! , Try another one" ;
                        if (sukses == -1) {

                            progressDialog.dismiss();
                            tvErrorText.setText(pesan);
                            edtEmail.requestFocus();

                            return;

                        }

                        // Error in registration!  // user failed to store
                        // jika gagal didaftarkan
                        if (sukses == -2){

                            progressDialog.dismiss();
                            tvErrorText.setText(pesan);
                            edtEmail.requestFocus();
                            return;

                        }
                        // jika ada kesalan dalam pendaftaran
                        if (sukses == -3) {

                            progressDialog.dismiss();
                            tvErrorText.setText(pesan);
                            edtEmail.requestFocus();
                            return;
                        }

                        //if  (sukses == 1) {

                        else{
                            progressDialog.dismiss();

                            edtName.setText("");
                            edtEmail.setText("");
                            edtPass.setText("");
                            edtPhone.setText("");
                            Toast.makeText(SignUpActivity.this, pesan, Toast.LENGTH_SHORT).show();

                        //    String name, String email, String nohp, String password ,String created_at (buat masukin sqlite)
                            dbsqlite.addUser(nama, email, passwordw);

                            // masukin ke sign in
                            Intent i = new Intent(getApplication(), SignInActivity.class);
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

        tvBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), SignInActivity.class);
                startActivity(i);
            }
        });
    }

}


