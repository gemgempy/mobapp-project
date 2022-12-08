package com.example.buku.fragment;


import static com.example.buku.Handler.Constant.URL_ADD_CART_USER;
import static com.example.buku.Handler.Constant.URL_DELETE_CART_FRAGMENT_USER;
import static com.example.buku.Handler.Constant.URL_MENAMPILKAN_DATA_CART;
import static com.example.buku.Handler.Constant.URL_UPDATE_CART;

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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.buku.Handler.Constant;
import com.example.buku.Handler.RequestHandler;
import com.example.buku.R;
import com.example.buku.activity.ChangeProfileActivity;
import com.example.buku.activity.CheckoutActivity;
import com.example.buku.activity.HomeActivity;
import com.example.buku.adapter.ItemCartAdapter;
import com.example.buku.adapter.Session;
import com.example.buku.dto.BookCartDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartFragment extends Fragment implements ItemCartAdapter.ListItemClickListener{
    RecyclerView rvcart;
    ArrayList<BookCartDTO> data;

    ItemCartAdapter adapter;
    Button btnPayment;
    TextView totalHarga;


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

    private void inisialisasi(View v, Context context) {

        totalHarga = v.findViewById(R.id.tv_total_harga);
        btnPayment = v.findViewById(R.id.btn_payment);
        rvcart = v.findViewById(R.id.rv_cart);
        rvcart.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true));

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CheckoutActivity.class);
                startActivity(i);
            }
        });


        data = new ArrayList<>();
        adapter = new ItemCartAdapter(context, data, this);

        rvcart.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void loadData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_MENAMPILKAN_DATA_CART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            data.clear();
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {


                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                data.add(new BookCartDTO(product));

                            }

                            adapter.notifyDataSetChanged();
                            hitung_total_harga();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error: ", error.getMessage());

                    }
                });
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    private void load_Delete(BookCartDTO item) {

        StringRequest request = new StringRequest(Request.Method.POST, URL_DELETE_CART_FRAGMENT_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //adapter.notifyDataSetChanged();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            int sukses = jsonObject.getInt("success");
                            String pesan = jsonObject.getString("message");

                            Log.v("eror",Integer.toString(sukses));


                            if (sukses == 1) { // data berhasil diupdate

                                // inside on response method we are
                                // setting our edit text to empty.

                                loadData();
                                // on below line we are displaying a toast message as data updated.
                                Toast.makeText(getActivity(), "Data DELETE", Toast.LENGTH_SHORT).show();


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
                params.put("id_book", item.id);



                return params;
            }
        };

        Volley.newRequestQueue(getActivity()).add(request);


    }


    @Override
    public void on_plus_click(BookCartDTO item) {

        // membuat memori yg berbeda
        ArrayList <BookCartDTO> data = (ArrayList<BookCartDTO>) this.data.clone();

        int index = data.indexOf(item);

        item.qty = Integer.toString(Integer.parseInt(item.qty) + 1);

        data.set(index, item);

        // data memori lama (  lain )
        this.data.clear();

        // data di add yang baru dari yg memori baru

        this.data.addAll(data);

        //notify
        adapter.notifyDataSetChanged();

        //hitung
        hitung_total_harga();

    }

    @Override
    public void on_min_click(BookCartDTO item) {

        if (Integer.parseInt(item.qty) == 1 ){

            return;
        }
        ArrayList <BookCartDTO> data = (ArrayList<BookCartDTO>) this.data.clone();

        int index = data.indexOf(item);

        item.qty = Integer.toString(Integer.parseInt(item.qty) - 1);

        data.set(index, item);

        this.data.clear();

        this.data.addAll(data);

        adapter.notifyDataSetChanged();
        hitung_total_harga();
    }

    @Override
    public void on_hapus_cart(BookCartDTO item) {
        load_Delete(item);

    }

    private void hitung_total_harga(){

        double total_harga = 0;

        for (BookCartDTO item : data) {

            total_harga += Double.parseDouble(item.qty) * Double.parseDouble(item.price);

        }

        totalHarga.setText(Double.toString(total_harga));

    }





}