package com.example.iclean.dto;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CucianDTO implements Serializable {

    // Untuk menampung data detail pakaian

    public String image;
    public String jenis_pakaian;
    public String name;
    public String price;
    public String description;
    public String max_berat;
    public String mulai_berlaku;
    public String kesulitan;
    public String rating;
    public static String max_jumlah;
    public String id;

    public CucianDTO(JSONObject product) {
        try {
            image = product.getString("image");
            jenis_pakaian = product.getString("jenis_pakaian");
            name = product.getString("name");
            price = product.getString("price");
            id = product.getString("id");
            description = product.getString("description");
            max_berat = product.getString("max_berat");
            mulai_berlaku = product.getString("mulai_berlaku");
            kesulitan = product.getString("kesulitan");
            rating = product.getString("rating");
            max_jumlah = product.getString("max_jumlah");
        } catch(JSONException e){

        }
    }
}