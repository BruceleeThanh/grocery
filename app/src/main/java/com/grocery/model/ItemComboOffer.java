package com.grocery.model;

import java.util.ArrayList;

/**
 * Created by ThanhBeo on 26/09/2017.
 */

public class ItemComboOffer {
    private int id;
    private int grocery_id;
    private String start_date;
    private String end_date;
    private float combo_price;
    private String description;
    private String created_at;
    private String updated_at;
    private int is_active;
    private ArrayList<ItemManagerProductsUpdate> listProducts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGrocery_id() {
        return grocery_id;
    }

    public void setGrocery_id(int grocery_id) {
        this.grocery_id = grocery_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public float getCombo_price() {
        return combo_price;
    }

    public void setCombo_price(float combo_price) {
        this.combo_price = combo_price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public ArrayList<ItemManagerProductsUpdate> getListProducts() {
        return listProducts;
    }

    public void setListProducts(ArrayList<ItemManagerProductsUpdate> listProducts) {
        this.listProducts = listProducts;
    }
}
