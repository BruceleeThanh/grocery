package com.grocery.model;

import java.io.Serializable;

/**
 * Created by ThanhBeo on 03/10/2017.
 */

public class ItemProductsOrders implements Serializable {
    private int id;
    private int order_id;
    private int product_id;
    private int quantity;
    private int unit_product_id;
    private int is_delete;
    private float price_origin;
    private float price_current;
    private int type;
    private String created_at;
    private String updated_at;
    private String product_name;
    private String product_photo;
    private int brand_id;
    private int number_unit;
    private String unit_name;
    private float price;
    private float price_discount;
    private int number_alternative;
    private int avaiable_quantity;
    private int number_of_units;
    /**/
    private int replace_product_id;
    private int imclose = 0;

    public int getImclose() {
        return imclose;
    }

    public void setImclose(int imclose) {
        this.imclose = imclose;
    }

    public int getReplace_product_id() {
        return replace_product_id;
    }

    public void setReplace_product_id(int replace_product_id) {
        this.replace_product_id = replace_product_id;
    }

    public ItemProductsOrders(int product_id, int quantity, int unit_product_id, int is_delete, float price_origin, float price_current, int type, int replace_product_id) {
        this.product_id = product_id;
        this.quantity = quantity;
        this.unit_product_id = unit_product_id;
        this.is_delete = is_delete;
        this.price_origin = price_origin;
        this.price_current = price_current;
        this.type = type;
        this.replace_product_id = replace_product_id;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public float getPrice_discount() {
        return price_discount;
    }

    public void setPrice_discount(float price_discount) {
        this.price_discount = price_discount;
    }

    public int getNumber_of_units() {
        return number_of_units;
    }

    public void setNumber_of_units(int number_of_units) {
        this.number_of_units = number_of_units;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

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

    public int getUnit_product_id() {
        return unit_product_id;
    }

    public void setUnit_product_id(int unit_product_id) {
        this.unit_product_id = unit_product_id;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public float getPrice_origin() {
        return price_origin;
    }

    public void setPrice_origin(float price_origin) {
        this.price_origin = price_origin;
    }

    public float getPrice_current() {
        return price_current;
    }

    public void setPrice_current(float price_current) {
        this.price_current = price_current;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_photo() {
        return product_photo;
    }

    public void setProduct_photo(String product_photo) {
        this.product_photo = product_photo;
    }

    public int getNumber_unit() {
        return number_unit;
    }

    public void setNumber_unit(int number_unit) {
        this.number_unit = number_unit;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNumber_alternative() {
        return number_alternative;
    }

    public void setNumber_alternative(int number_alternative) {
        this.number_alternative = number_alternative;
    }

    public int getAvaiable_quantity() {
        return avaiable_quantity;
    }

    public void setAvaiable_quantity(int avaiable_quantity) {
        this.avaiable_quantity = avaiable_quantity;
    }
}
