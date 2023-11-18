package com.example.iclean.Handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**

 * Menyimpan data di SQLite Database
 */

public class SQLite extends SQLiteOpenHelper{

    private static final String TAG = SQLite.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 9;

    // Database Name
    private static final String DATABASE_NAME = "lofRYYfffDyyc0Raldb77";

    // Login table name
    private static final String TABLE_USER = "user3ATDewGORYvvf57553";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    //private static final String KEY_NoHP = "nohp";
    private static final String KEY_PASSWORD ="passwordw";
    //private static final String KEY_CREATED_AT = "created_at";

    public SQLite(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    // Membuat tabel SQLite
    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT," + KEY_PASSWORD + " TEXT" + ")";

        db.execSQL(CREATE_LOGIN_TABLE);


        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Menyimpan data ke SQLite
     * */
    public void addUser(String nama, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, nama); // Nama
        values.put(KEY_EMAIL, email); // Email

        values.put(KEY_PASSWORD, password);// password


        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }


    public void updateUser(String id, String nama, String email) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(KEY_ID, id); // Name
        values.put(KEY_NAME, nama); // Name
        values.put(KEY_EMAIL, email); // Email


        db.update(TABLE_USER, values, "id=?", new String[]{id});
        db.close();

//        db.insert(TABLE_USER, null, values);
//        db.close(); // Closing database connection

    }

    /**
     * Ambil data user dari Database SQLite
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > -1) {

            user.put("nama", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("password", cursor.getString(3));
            user.put("id", cursor.getString(0));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Menghapus isi database SQLite untuk diperbarui.
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }


    public boolean checkUser(String email, String password) {
        // array of columns to fetch
        String[] columns = {
                KEY_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = KEY_EMAIL + " = ?" + " AND " +  KEY_PASSWORD+ " = ?";
        // selection arguments
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USER, //Tabel untuk query
                columns,                    //mengembalikan kolom
                selection,                  //Where clause
                selectionArgs,              //Value untuk where clause
                null,                       //buat gruping baris
                null,                       //filter baris
                null);                      //buat sort
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

}
