package com.example.iclean.adapter;

import android.content.Context;
import android.content.SharedPreferences;


// Menyimpan sesi login agar aplikasi tetap login sebagai pengguna yang sesuai setiap kali dibuka

public class Session {


    private static Session instance;

    private static final String SHARED_PREF_NAME = "mysharedpref123";

    public static final String KEY_NAME = "USER_NAME";
    public static final String KEY_EMAIL = "USER_EMAIL";
    public static final String KEY_USER_ID = "USER_ID";
    public static final String KEY_USER_NOHP = "USER_NOHP";
    private static Context ctx;



    private Session(Context context) {
        ctx = context;


    }

    public static synchronized Session getInstance(Context context) {
        if (instance == null) {
            instance = new Session(context);
        }
        return instance;
    }


    public boolean userlogin (String  id, String namee, String email, String nohp ){

        // Jika login, data yang masuk tetap berada pada session

        SharedPreferences sharedPreferences =ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_ID, id);
        editor.putString(KEY_NAME, namee);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USER_NOHP, nohp);

        editor.apply();

        return true;

    }

    public boolean userUpdated (String nohp ){ // Mengupdate nomor HP user

        SharedPreferences sharedPreferences =ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

//        editor.putString(KEY_NAME, namee);
//        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USER_NOHP, nohp);

        editor.apply();

        return true;

    }


    // Untuk memeriksa apakah user telah login

    public boolean isLoggedin (){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_NAME, null) != null){
            return true;
        }
        return false;
    }

    public boolean logout (){ // Hal ini digunakan untuk melakukan log out pada session

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        return true;
    }

    // di bawah ini digunakan untuk mendapatkan data untuk mengembalikan apa yang diminta oleh session yang ditambahkan sebelumnya

    public String getuserName() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, null);

    }

    public String getusernomer_Hape() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_NOHP, null);

    }

    public String getEMAIL_USER() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null);

    }

    public String getKeyUserId(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ID, null);


    }


}
