package com.example.buku.activity;

import static com.example.buku.Handler.Constant.URL_NEW_BOOK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.buku.R;
import com.example.buku.adapter.NewBookAdapter;
import com.example.buku.adapter.SearchListAdapter;
import com.example.buku.dto.BookDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchView searchView;
    List<BookDTO> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


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

    private void filterList(String searchQuery) {
        ArrayList<BookDTO> filteredData = new ArrayList<>();

        for (BookDTO item : data) {
            if (item.name.toLowerCase().contains(searchQuery.toLowerCase())){
                filteredData.add(item);
            }
        }

        ArrayList<String> newData = new ArrayList<>();
        for (BookDTO item : filteredData) {
            newData.add(item.name);
        }

        SearchListAdapter adapter = new SearchListAdapter(this, newData);
        adapter.setClickListener(new SearchListAdapter.ItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(view.getContext(), DetailBookActivity.class);
                i.putExtra("data",filteredData.get(position));
                startActivity(i);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    void loadData() {
        // data to populate the RecyclerView with
        // data to populate the RecyclerView with
        // to do: ambil data dari API
        data = new ArrayList<BookDTO>();
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

                                //adding the product to product list
                                data.add(new BookDTO(product));
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
        for (BookDTO item : data) {
                newData.add(item.name);
            }
        SearchListAdapter adapter = new SearchListAdapter(this, newData);
        adapter.setClickListener(new SearchListAdapter.ItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(view.getContext(), DetailBookActivity.class);
                i.putExtra("data",data.get(position));
                startActivity(i);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}