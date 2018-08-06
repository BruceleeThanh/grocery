package com.grocery.model;

/**
 * Created by ThanhBeo on 25/09/2017.
 */

public class UnitModel {
    private int id;
    private int manage_product_id;
    private int unit_product_id;
    private String created_at;
    private String updated_at;
    private int main_unit;
    private int is_choose;
    private String unit_name;
    private int number_unit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManage_product_id() {
        return manage_product_id;
    }

    public void setManage_product_id(int manage_product_id) {
        this.manage_product_id = manage_product_id;
    }

    public int getUnit_product_id() {
        return unit_product_id;
    }

    public void setUnit_product_id(int unit_product_id) {
        this.unit_product_id = unit_product_id;
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

    public int getMain_unit() {
        return main_unit;
    }

    public void setMain_unit(int main_unit) {
        this.main_unit = main_unit;
    }

    public int getIs_choose() {
        return is_choose;
    }

    public void setIs_choose(int is_choose) {
        this.is_choose = is_choose;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public int getNumber_unit() {
        return number_unit;
    }

    public void setNumber_unit(int number_unit) {
        this.number_unit = number_unit;
    }
}
