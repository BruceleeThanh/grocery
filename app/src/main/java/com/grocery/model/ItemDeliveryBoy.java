package com.grocery.model;

/**
 * Created by ThanhBeo on 19/06/2017.
 */

public class ItemDeliveryBoy {
    private int id;
    private int grocery_id;
    private String delivery_boy_name;
    private String delivery_boy_email;
    private String created_at;
    private String updated_at;
    private int is_active;
    /*-----------------*/
    private boolean isChoose = false;

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
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

    public String getDelivery_boy_name() {
        return delivery_boy_name;
    }

    public void setDelivery_boy_name(String delivery_boy_name) {
        this.delivery_boy_name = delivery_boy_name;
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

    public String getDelivery_boy_email() {
        return delivery_boy_email;
    }

    public void setDelivery_boy_email(String delivery_boy_email) {
        this.delivery_boy_email = delivery_boy_email;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }
}
