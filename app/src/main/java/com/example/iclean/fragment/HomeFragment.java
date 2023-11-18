package com.example.iclean.fragment;


import static com.example.iclean.Handler.Constant.URL_NEW_CUCIAN;
import static com.example.iclean.Handler.Constant.URL_POPULAR;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.iclean.R;
import com.example.iclean.activity.DetailCucianActivity;
import com.example.iclean.activity.SearchActivity;
import com.example.iclean.adapter.NewCucianAdapter;
import com.example.iclean.dto.CucianDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    // Inisialisasi awal
    LinearLayout ll_searchView;
    ViewFlipper v_flipper;
    RecyclerView rvNewCucian;
    RecyclerView rvPopularCucian;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        inisialisasi(v, container.getContext());
        mainButton();

        // Untuk melakukan load, berdasarkan cucian baru pada recyleview tampilan awal
        loadData_cucianNEW_CUCIAN(container.getContext());
        LoadData_cucian_POPULAR_CUCIAN(container.getContext());
        return v;
    }

    private void inisialisasi(View v, Context context){

        ll_searchView = v.findViewById(R.id.ll_searchView);

        // Deklarasi carousel bergerak pada home
        int[] images = new int[] {R.drawable.carousel1, R.drawable.carousel2, R.drawable.carousel3, R.drawable.carousel4};
        v_flipper = v.findViewById(R.id.v_flipper);
        for (int image:images){
            flipperImages(image);
        }

        rvPopularCucian = v.findViewById(R.id.rv_popular_cucian);
        rvPopularCucian.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,true));

        rvNewCucian = v.findViewById(R.id.rv_new_cucian);
        rvNewCucian.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,true));
    }

    private void mainButton(){ // Berpindah ke search activity
        ll_searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SearchActivity.class);
                startActivity(i);
            }
        });
    }

    public void flipperImages(int image){ // Function pada carousel
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(4000);
        v_flipper.setAutoStart(true);

        v_flipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
    }

    private void loadData_cucianNEW_CUCIAN(Context context){ // load data cucian baru dri api yg diambil dri database

        ArrayList<CucianDTO> data = new ArrayList<>(); // deklarasi arraylist CucianDdto


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_NEW_CUCIAN,
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
                                // Memasukkan ke arraylist berdasarkan object API product
                                data.add(new CucianDTO(product));
                            }

                            NewCucianAdapter adapter = new NewCucianAdapter(context, data);
                            adapter.setClickListener(new NewCucianAdapter.ItemClickListener(){

                                // Ketika menekan cuciannya maka recyle viewnya akan intent data berdasarkan cuciannya
                                // Sehingga pada detail activity dapat membaca data tentang cucians

                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent i = new Intent(getActivity(), DetailCucianActivity.class);
                                    i.putExtra("data",data.get(position));
                                    startActivity(i);
                                }
                            });

                            rvNewCucian.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) { // jika eror
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() { // jika eror
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error: ", error.getMessage());

                    }

                });
        // adding our stringrequest to queue

        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    public void LoadData_cucian_POPULAR_CUCIAN(Context context){  // LOAD API DRI database laundry popular cucian
        ArrayList<CucianDTO> data = new ArrayList<>(); // deklarasi awal arraylist cuciandto
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_POPULAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject product = array.getJSONObject(i);
                                data.add(new CucianDTO(product));
                            }
                            NewCucianAdapter adapter = new NewCucianAdapter(context, data);
                            adapter.setClickListener(new NewCucianAdapter.ItemClickListener(){
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent i = new Intent(getActivity(), DetailCucianActivity.class);

                                    i.putExtra("data",data.get(position));
                                    startActivity(i);
                                }
                            });
                            rvPopularCucian.setAdapter(adapter);
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
        // direquest dah
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
}