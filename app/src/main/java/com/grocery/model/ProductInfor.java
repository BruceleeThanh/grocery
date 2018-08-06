package com.grocery.model;

/**
 * Created by ducth on 10/23/2017.
 */

public class ProductInfor {
    private int product_id;
    private int quantity;
    private String product_name;
    private String photo;
    private float price;
    private boolean isCheckbox;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isCheckbox() {
        return isCheckbox;
    }

    public void setCheckbox(boolean checkbox) {
        isCheckbox = checkbox;
    }
}
