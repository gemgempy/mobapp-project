package com.example.iclean.fragment;


import static com.example.iclean.Handler.Constant.URL_DELETE_CART_FRAGMENT_USER;
import static com.example.iclean.Handler.Constant.URL_MENAMPILKAN_DATA_CART;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.iclean.R;
import com.example.iclean.activity.CheckoutActivity;
import com.example.iclean.activity.HomeActivity;
import com.example.iclean.adapter.ItemCartAdapter;
import com.example.iclean.dto.CucianCartDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartFragment extends Fragment implements ItemCartAdapter.ListItemClickListener{
    // Melakukan inisialisasi awal

    RecyclerView rvcart;
    ArrayList<CucianCartDTO> data; // Memanggil arraylist CucianCartDTO

    ItemCartAdapter adapter;
    Button btnPayment;
    TextView totalHarga;
    TextView TOTAL_barang_CUCIAN;
    Intent i;

    double total_harga;
    int total_barang;
    int ii= 0;

    String hargaTotal;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart, container, false);

        inisialisasi(v, container.getContext());
        loadData();

        return v;
    }

    private void inisialisasi(View v, Context context) { // inisiasi awal

        TOTAL_barang_CUCIAN = v.findViewById(R.id.tv_total_barang);
        totalHarga = v.findViewById(R.id.tv_total_harga);
        btnPayment = v.findViewById(R.id.btn_payment);
        rvcart = v.findViewById(R.id.rv_cart);
        rvcart.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true));

        data = new ArrayList<>();
        adapter = new ItemCartAdapter(context, data, this);

        rvcart.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        final int position = 0;

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    // int a = getArguments().getInt("position");
                    if(ii == 0){
                        Toast.makeText(getActivity(), "You dont have any clothes to be cleaned in your cart !", Toast.LENGTH_SHORT).show();

                    }
                    else{

                        // dia intent ke checkout activity buat ngirim data berrdasarkan posisi

                        i = new Intent(getActivity(), CheckoutActivity.class);

                        i.putExtra("data_123", data.get(position));
                        i.putExtra("loadsPosition", hargaTotal);
                        i.putExtra("loads_summary", TOTAL_barang_CUCIAN.getText().toString());
                        startActivity(i);
                    }

                }catch (Exception e) {
                    Toast.makeText(getActivity(), "You dont have any clothes to be cleaned in your cart !", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }

            }
        });

    }

    private void loadData() {
        // Untuk mengambil user_id dari session
        HomeActivity activity = (HomeActivity) getActivity();
        String id_data_User = activity.getUSERDATA_ID();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_MENAMPILKAN_DATA_CART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            data.clear();
                            //traversing through all the object
                            for (ii = 0; ii < array.length(); ii++) {
                                // Mengambil product dari JSON array
                                JSONObject product = array.getJSONObject(ii);
                                // Menambah product ke productlist
                                data.add(new CucianCartDTO(product));
                            }
                            adapter.notifyDataSetChanged();
                            hitung_total_harga();

                        } catch (JSONException e) { // jika eror maka
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error: ", error.getMessage());

                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                // Mengambil data user_id dari session
                params.put("id_user", id_data_User);


                return params;
            }
        };

        // baru di request dah
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    private void load_Delete(CucianCartDTO item) {

        StringRequest request = new StringRequest(Request.Method.POST, URL_DELETE_CART_FRAGMENT_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //adapter.notifyDataSetChanged();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            int sukses = jsonObject.getInt("success");
                            String pesan = jsonObject.getString("message");

                            if (sukses == 1) { // data berhasil diupdate
                                loadData();
                                Toast.makeText(getActivity(), "Successfully Delete the clothes list from your Cart!", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(getActivity(), "Fail to DELETE data..", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("id_cart", item.id_cart);
                params.put("id_cucian", item.id);

                return params;
            }
        };

        Volley.newRequestQueue(getActivity()).add(request);


    }

    @Override
    public void on_plus_click(CucianCartDTO item) {

        /**
         * function yang digunakan untuk pengubahan apabila jumlah cart di tambah dia akan menambah angkanya
          */

        // membuat memori yg berbeda
        ArrayList <CucianCartDTO> data = (ArrayList<CucianCartDTO>) this.data.clone();

        int index = data.indexOf(item);

        item.qty = Integer.toString(Integer.parseInt(item.qty) + 1);

        data.set(index, item);

        // data memori lama (  lain )
        this.data.clear();

        // data di add yang baru dari yg memori baru

        this.data.addAll(data);

        //notify   di senggol adapternya biar sadar klo ada perubahan
        adapter.notifyDataSetChanged();

        //hitung total
        hitung_total_harga();

    }

    @Override
    public void on_min_click(CucianCartDTO item) {

        /**
         * function yang digunakan untuk pengubahan apabila jumlah cart di tambah dia akan mengurang angkanya
         */


        if (Integer.parseInt(item.qty) == 1 ){ // ini set qty == 1 jdi ga bisa kurang dri ini

            return;
        }

        // membuat memori yg berbeda
        ArrayList <CucianCartDTO> data = (ArrayList<CucianCartDTO>) this.data.clone();

        int index = data.indexOf(item);

        item.qty = Integer.toString(Integer.parseInt(item.qty) - 1);

        data.set(index, item);

        // data memori lama (  lain )
        this.data.clear();

        // data di add yang baru dari yg memori baru
        this.data.addAll(data);

        // di senggol adapternya biar sadar klo ada perubahan
        adapter.notifyDataSetChanged();

        //hitung total
        hitung_total_harga();

    }

    @Override
    public void on_hapus_cart(CucianCartDTO item) {
        /**
         *  tombol listener yang digunakan unutk menghapus data didalam cart user
         */

        load_Delete(item);

    }

    private void hitung_total_harga(){

        /**
         * function yang digunakan untuk perhitungan
         */

        total_harga = 0;
        total_barang = 0;

        for (CucianCartDTO item : data) {

            total_harga += Double.parseDouble(item.qty) * Double.parseDouble(item.price); // perhitungan dri bookcartdto qty * price


            total_barang += Integer.parseInt(item.qty); // total barang didapetin dri bookcartdto.qty
        }

        hargaTotal = Double.toString(total_harga);

        totalHarga.setText("Rp. " + hargaTotal + "00"); // set totak harga menjadi rp. sekian


        TOTAL_barang_CUCIAN.setText(Integer.toString(total_barang)); // ini total harga
    }
}