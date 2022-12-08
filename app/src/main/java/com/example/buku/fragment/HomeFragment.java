package com.example.buku.fragment;


import static com.example.buku.Handler.Constant.URL_NEW_BOOK;
import static com.example.buku.Handler.Constant.URL_POPULAR;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.buku.Handler.SQLite;
import com.example.buku.Handler.SQLite_Buku;
import com.example.buku.R;
import com.example.buku.activity.DetailBookActivity;
import com.example.buku.activity.SearchActivity;
import com.example.buku.adapter.NewBookAdapter;
import com.example.buku.dto.BookDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    LinearLayout ll_searchView;
    ViewPager2 vpBanner;
    RecyclerView rvNewBook;
    RecyclerView rvPopularBook;

   // private SQLite_Buku dbsqlite;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        inisialisasi(v, container.getContext());
        mainButton();

        loadData_bukuNEW_BOOK(container.getContext());
        LoadData_buku_POPULAR_BOOK(container.getContext());
        return v;
    }

    private void inisialisasi(View v, Context context){
        ll_searchView = v.findViewById(R.id.ll_searchView);
        vpBanner = v.findViewById(R.id.vp_banner);

        rvPopularBook = v.findViewById(R.id.rv_popular_book);
        rvPopularBook.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,true));

        rvNewBook = v.findViewById(R.id.rv_new_book);
        rvNewBook.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,true));

    }

    private void mainButton(){
        ll_searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SearchActivity.class);
                startActivity(i);
            }
        });
    }

    private void loadData_bukuNEW_BOOK(Context context){
        ArrayList<BookDTO> data = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_NEW_BOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);


//                                SQLite_Buku dbEntry = new SQLite_Buku(getActivity());
//                                dbEntry.addbuku(id_buku, deskripsi_buku, total_pages_buku, published_at_buku, isbn_buku, rating_buku);

                                //adding the product to product list
                                data.add(new BookDTO(product));
                            }

//                            // ISI_Datanya();
                            NewBookAdapter adapter = new NewBookAdapter(context, data);
                            adapter.setClickListener(new NewBookAdapter.ItemClickListener(){
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent i = new Intent(getActivity(), DetailBookActivity.class);
                                    i.putExtra("data",data.get(position));
                                    startActivity(i);
                                }
                            });
                            rvNewBook.setAdapter(adapter);
                            adapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error: ", error.getMessage());


                        //Toast.makeText(HomeFragment.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });

        // adding our stringrequest to queue
//        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        Volley.newRequestQueue(getActivity()).add(stringRequest);

    }

    public void LoadData_buku_POPULAR_BOOK(Context context){
        ArrayList<BookDTO> data = new ArrayList<>();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_POPULAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

//                                SQLite_Buku dbEntry = new SQLite_Buku(getActivity());
//                                dbEntry.addbuku(id_buku, deskripsi_buku, total_pages_buku, published_at_buku, isbn_buku, rating_buku);

//                                Intent D = new Intent(getActivity(), DetailBookActivity.class);
//
//                                //i.putExtra("data123", get_dATA_BUKU_des());

//                                D.putExtra("data_DES",deskripsi_buku);
//                                D.putExtra("data_TOTAL",total_pages_buku);
//                                D.putExtra("data_published_at_buku",published_at_buku);
//                                D.putExtra("data_isbn_buku",isbn_buku);
//                                D.putExtra("data_rating_buku",rating_buku);
//                                startActivity(D);

//                               HAHA.add(new BookDTO1(
//                                       product.getString("description"),
//                                       product.getString("total_pages"),
//                                       product.getString("published_at"),
//                                       product.getString("isbn"),
//                                       product.getString("rating")
//                               ));
//
//                                Intent J = new Intent(getActivity(), DetailBookActivity.class);
//
//                                // i.putExtra("data_DES",deskripsi_buku.(position)));
//                                J.putExtra("data1234",HAHA);
//                                startActivity(J);

                                //data.add(new BookDTO("https://th.bing.com/th/id/OIP.ofO5_LBReY91t3lXfoMOJAHaHJ?pid=ImgDet&rs=1", "edwing", "buku memasak", "5000", "0","a", "a", "a", "a", "a", "a"));


                                //adding the product to product list
                                data.add(new BookDTO(product));
                            }

                            // ISI_Datanya();
                            NewBookAdapter adapter = new NewBookAdapter(context, data);
                            adapter.setClickListener(new NewBookAdapter.ItemClickListener(){
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent i = new Intent(getActivity(), DetailBookActivity.class);

                                   // i.putExtra("data_DES",deskripsi_buku.(position)));
                                    i.putExtra("data",data.get(position));
                                    startActivity(i);
                                }
                            });

                            rvPopularBook.setAdapter(adapter);
                            adapter.notifyDataSetChanged();


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
}