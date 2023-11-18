package com.example.iclean.activity;

import static com.example.iclean.Handler.Constant.URL_NEW_CUCIAN;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.iclean.R;
import com.example.iclean.adapter.SearchListAdapter;
import com.example.iclean.dto.CucianDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    // DEKLARASI AWAL VARIABLE
    RecyclerView recyclerView;
    SearchView searchView;
    List<CucianDTO> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // DEKLARASI VARIABLE

        recyclerView = findViewById(R.id.search_book);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadData();
        getSupportActionBar().hide();

        // set up the SearchView
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void filterList(String searchQuery) { // ambil data dari arraylist bookdto class
        ArrayList<CucianDTO> filteredData = new ArrayList<>();

        for (CucianDTO item : data) {
            if (item.name.toLowerCase().contains(searchQuery.toLowerCase())){
                filteredData.add(item);
            }
        }

        ArrayList<String> newData = new ArrayList<>();
        for (CucianDTO item : filteredData) {
            newData.add(item.name);
        }

        SearchListAdapter adapter = new SearchListAdapter(this, newData);
        adapter.setClickListener(new SearchListAdapter.ItemClickListener(){
            @Override
            public void onItemClick(View view, int position) { // ketika di tekan itu searchnya maka dia akan intent data sesuai posisi

                Intent i = new Intent(view.getContext(), DetailCucianActivity.class);
                i.putExtra("data",filteredData.get(position));
                startActivity(i);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    void loadData() {
        data = new ArrayList<CucianDTO>();
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

                                //adding the product to product list
                                data.add(new CucianDTO(product));
                            }

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

        Volley.newRequestQueue(this).add(stringRequest);
        ArrayList<String> newData = new ArrayList<>();
        for (CucianDTO item : data) {
                newData.add(item.name);
            }

        SearchListAdapter adapter = new SearchListAdapter(this, newData);
        adapter.setClickListener(new SearchListAdapter.ItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(view.getContext(), DetailCucianActivity.class);
                i.putExtra("data",data.get(position));
                startActivity(i);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}