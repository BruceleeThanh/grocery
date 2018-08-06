package com.grocery.model;

/**
 * Created by ThanhBeo on 18/07/2017.
 */

public class ItemProduct {
    private String product_name;
    private String brand_name;
    private String description;

    public ItemProduct(String product_name, String brand_name, String description) {
        this.product_name = product_name;
        this.brand_name = brand_name;
        this.description = description;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
