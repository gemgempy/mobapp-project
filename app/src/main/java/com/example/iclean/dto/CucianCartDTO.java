package com.example.iclean.dto;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CucianCartDTO implements Serializable {

    //menampung data keranjang

    public String id;
    public String name;
    public String jenis_pakaian;
    public String price;
    public String description;
    public String image;
    public String max_berat;
    public String mulai_berlaku;
    public String kesulitan;
    public String rating;
    public String qty;
    public String id_cart;

    public CucianCartDTO(JSONObject product) {
        try {
            id = product.getString("id");
            name = product.getString("name");
            jenis_pakaian = product.getString("jenis_pakaian");
            price = product.getString("price");
            description = product.getString("description");
            image = product.getString("image");
            max_berat = product.getString("max_berat");
            mulai_berlaku = product.getString("mulai_berlaku");
            kesulitan = product.getString("kesulitan");
            rating = product.getString("rating");
            qty = product.getString("qty");
            id_cart = product.getString("id_cart");
        } catch (JSONException e) {

        }
    }
}