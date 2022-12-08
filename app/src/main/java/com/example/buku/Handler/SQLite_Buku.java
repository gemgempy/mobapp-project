package com.example.buku.Handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**

 * Menyimpan data di handphone masing-masing berbasis SQLite Database
 */

public class SQLite_Buku extends SQLiteOpenHelper{

    private static final String TAG = SQLite.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 10;

    // Database Name
    private static final String DATABASE_NAME = "buku8812gn0021";

    // Login table name
    private static final String TABLE_Buku = "b5uku38y42h";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_AUTHOR = "author";

    private static final String KEY_PRICE = "price";
    private static final String KEY_DESCRIPTION ="description";

    private static final String KEY_IMAGE ="image"; // gaperlu
    private static final String KEY_total_pages ="total_pages";

    private static final String KEY_published_at = "published_at";
    private static final String KEY_isbn = "isbn";
    private static final String KEY_rating = "rating";


    public SQLite_Buku(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    // Membuat tabel SQLite
    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_Buku + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_AUTHOR + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_total_pages + " TEXT,"
                + KEY_published_at + " TEXT,"
                + KEY_isbn + " TEXT,"
                + KEY_rating + " TEXT,"
                + KEY_PRICE + " TEXT" + ")";

        db.execSQL(CREATE_LOGIN_TABLE);


        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Buku);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * Menyimpan data user kedalam database SQLite
     * */
    public void addbuku(String idd,String Description, String total_pages, String published_at, String isbn, String rating) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put (KEY_ID, idd);
        values.put(KEY_DESCRIPTION, Description); // DESKRIPSI


        values.put(KEY_total_pages, total_pages); // TOTAL PAGES

        values.put(KEY_published_at, published_at); // publised AT

        values.put(KEY_isbn, isbn); // isbn

        values.put(KEY_rating, rating);// rating


        // Inserting Row
        long id = db.insert(TABLE_Buku, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    // below is the method for updating our courses
    public void updateUser(String id, String nama, String email) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(KEY_ID, id); // Name
        values.put(KEY_NAME, nama); // Name
       // values.put(KEY_EMAIL, email); // Email

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.update(TABLE_Buku, values, "id=?", new String[]{id});
        db.close();

//        db.insert(TABLE_USER, null, values);
//        db.close(); // Closing database connection

    }

    /**
     * Getting user data from database
     * Mengambil data user dari Database SQLite
     * */
    public HashMap<String, String> getbook_Details() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_Buku + " ORDER BY " + KEY_rating + " DESC LIMIT 10";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > -1) {

            user.put("id", cursor.getString(0));
            user.put("nama", cursor.getString(1));
            user.put("author", cursor.getString(2));
            user.put("description", cursor.getString(3));
            user.put("total_pages", cursor.getString(4));
            user.put("published_at", cursor.getString(5));
            user.put("isbn", cursor.getString(6));
            user.put("rating", cursor.getString(7));
            user.put("price", cursor.getString(8));


        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }


    /**
     * Getting user data from database
     * Mengambil data user dari Database SQLite
     * */
    public HashMap<String, String> getbook_Details_popular() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_Buku + " ORDER BY " + KEY_published_at + " DESC LIMIT 10";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > -1) {

            user.put("id", cursor.getString(0));
            user.put("nama", cursor.getString(1));
            user.put("author", cursor.getString(2));
            user.put("description", cursor.getString(3));
            user.put("total_pages", cursor.getString(4));
            user.put("published_at", cursor.getString(5));
            user.put("isbn", cursor.getString(6));
            user.put("rating", cursor.getString(7));
            user.put("price", cursor.getString(8));


        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }



    /**
     * Recreate database Delete all tables and create them again
     * Menghapus isi database SQLite untuk diperbarui.
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_Buku, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }
}
