package com.example.buku.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.buku.Handler.RequestHandler;


/**

 * Menyimpan sesi login agar kapanpun aplikasi dibuka tetap login sebagai user masing-masing
 */
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

        SharedPreferences sharedPreferences =ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_ID, id);
        editor.putString(KEY_NAME, namee);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USER_NOHP, nohp);

        editor.apply();

        return true;

    }

    public boolean userUpdated (String nohp ){

        SharedPreferences sharedPreferences =ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

//        editor.putString(KEY_NAME, namee);
//        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USER_NOHP, nohp);

        editor.apply();

        return true;

    }




    public boolean isLoggedin (){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_NAME, null) != null){
            return true;
        }
        return false;
    }

    public boolean logout (){

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        return true;
    }


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
