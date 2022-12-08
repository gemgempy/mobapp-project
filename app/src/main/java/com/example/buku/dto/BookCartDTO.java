package com.example.buku.dto;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class BookCartDTO implements Serializable {

    public String id;
    public String name;
    public String author;
    public String price;
    public String description;
    public String image;
    public String total_pages;
    public String published_at;
    public String isbn;
    public String rating;
    public String qty;
    public String id_cart;

    public BookCartDTO(JSONObject product) {
        try {
            id = product.getString("id");
            name = product.getString("name");
            author = product.getString("author");
            price = product.getString("price");
            description = product.getString("description");
            image = product.getString("image");
            total_pages = product.getString("total_pages");
            published_at = product.getString("published_at");
            isbn = product.getString("isbn");
            rating = product.getString("rating");
            qty = product.getString("qty");
            id_cart = product.getString("id_cart");
        } catch (JSONException e) {

        }
    }
}