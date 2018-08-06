package com.grocery.model;

import java.util.ArrayList;

/**
 * Created by ThanhBeo on 04/10/2017.
 */

public class ItemAlternavite {
    private int id;
    private int grocery_id;
    private int product_id;
    private int unit_id;
    private int instock;
    private String created_at;
    private String updated_at;
    private int min_stock;
    private float price;
    private int unit_id_min;
    private int is_active;
    private int instock_status;
    private String product_name;
    private String photo;
    private float discount_price;
    private int avaiable_instock;
    private ArrayList<UnitModel> unit;
    private int type;

    /*-------------*/
    private boolean ischoose = false;
    private int quantity = 0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean ischoose() {
        return ischoose;
    }

    public void setIschoose(boolean ischoose) {
        this.ischoose = ischoose;
    }

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

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }

    public int getInstock() {
        return instock;
    }

    public void setInstock(int instock) {
        this.instock = instock;
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

    public int getMin_stock() {
        return min_stock;
    }

    public void setMin_stock(int min_stock) {
        this.min_stock = min_stock;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getUnit_id_min() {
        return unit_id_min;
    }

    public void setUnit_id_min(int unit_id_min) {
        this.unit_id_min = unit_id_min;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public int getInstock_status() {
        return instock_status;
    }

    public void setInstock_status(int instock_status) {
        this.instock_status = instock_status;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public float getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(float discount_price) {
        this.discount_price = discount_price;
    }

    public int getAvaiable_instock() {
        return avaiable_instock;
    }

    public void setAvaiable_instock(int avaiable_instock) {
        this.avaiable_instock = avaiable_instock;
    }

    public ArrayList<UnitModel> getUnit() {
        return unit;
    }

    public void setUnit(ArrayList<UnitModel> unit) {
        this.unit = unit;
    }
}
