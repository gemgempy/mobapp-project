package com.example.iclean.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class CucianHistoryDTO {

//Untuk menampung data history pemesanan pengguna

    public String id;
    public String cucian_id;
    public String id_user;
    public String id_cart;
    public String name;
    public String image;
    public String qty;
    public String total_price;
    public String Address;
    public String status;

    public CucianHistoryDTO(JSONObject product) {
        try {
            id = product.getString("id");
            cucian_id = product.getString("cucian_id");
            id_user = product.getString("id_user");
            id_cart = product.getString("id_cart");
            name = product.getString("name");
            image = product.getString("image");
            qty = product.getString("qty");
            total_price = product.getString("total_price");
            Address = product.getString("Address");
            status = product.getString("status");
        } catch(JSONException e){

        }
    }

}
