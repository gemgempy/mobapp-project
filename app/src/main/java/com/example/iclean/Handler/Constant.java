package com.example.iclean.Handler;

public class Constant {
    /**
     * CLASS INI DIGUNAKAN UNTUK MENAMPUNG URL URL API SEHINGGA LEBIH GAMPANG MEMANGGILNYA PADA CLASS LAIN
     */
    private static final String ROOT_URL = "http://www.icleanlaundry.my.id/BUAT_UAS/";

    private static final String ROOT_URL2 = "https://www.icleanlaundry.my.id/BUAT_UAS/buat_foto/";


    public static final String URL_REGISTER = ROOT_URL+"Register_Percobaan.php";
    public static final String URL_LOGIN = ROOT_URL+"login.php";
    public static final String URL_UPDATE = ROOT_URL+"UPDATE.php";

    public static final String URL_NEW_CUCIAN = ROOT_URL2+"jsondate.php";
    public static final String URL_POPULAR = ROOT_URL2+"json.php";

    public static final String URL_ADD_CART_USER = ROOT_URL+"cartpercobaan.php";

    public static final String URL_MENAMPILKAN_DATA_CART = ROOT_URL2 + "json_Menampilkan_Cart.php";

    public static final String URL_UPDATE_CART = ROOT_URL + "UPDATE_CART.php"; // ga dipake akrena ya ga jadi digunakan

    public static final String URL_DELETE_CART_FRAGMENT_USER = ROOT_URL + "DELETE_Cart.php";


    public static final String url_web_view ="https://www.myvaporstore.com/";


    // untuk upload pembayaran

    public static final String url_upload_bukti_pembayaran = ROOT_URL + "upload_bukti_pembayaran.php";

    public static final String url_upload_bukti_pembayaran_bukan_gambar = ROOT_URL + "TRANSACTION_ITEM.php";


    // riwayat pesanan

    public static final String URL_HISTORI_CART_PEMBELIANN = ROOT_URL + "history.php";
}