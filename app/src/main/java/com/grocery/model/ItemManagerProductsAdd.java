package com.grocery.model;

/**
 * Created by ThanhBeo on 19/09/2017.
 */

public class ItemManagerProductsAdd {
    private int id;
    private String name;
    private String product_code;
    private int currency_id;
    private String photo;
    private String description;
    private float price;
    private int sub_category_id;
    private int brand_id;
    private int type_product;
    private String list_unit;
    private String created_at;
    private String updated_at;
    private int is_active;
    private int unit_id;
    private int number_unit;
    private int is_added;
    private String unit_name;
    private String category_name;

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public int getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(int currency_id) {
        this.currency_id = currency_id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(int sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public int getType_product() {
        return type_product;
    }

    public void setType_product(int type_product) {
        this.type_product = type_product;
    }

    public String getList_unit() {
        return list_unit;
    }

    public void setList_unit(String list_unit) {
        this.list_unit = list_unit;
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

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }

    public int getNumber_unit() {
        return number_unit;
    }

    public void setNumber_unit(int number_unit) {
        this.number_unit = number_unit;
    }

    public int getIs_added() {
        return is_added;
    }

    public void setIs_added(int is_added) {
        this.is_added = is_added;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }
}
