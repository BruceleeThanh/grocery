package com.grocery.model;

/**
 * Created by ThanhBeo on 02/10/2017.
 */

public class ItemOrderInfor extends ItemOrders {
    public float building_longitude;
    public float building_latitude;
    public String user_name;
    public String user_photo;
    private float service_fee;

    public float getService_fee() {
        return service_fee;
    }

    public void setService_fee(float service_fee) {
        this.service_fee = service_fee;
    }

    public float getBuilding_longitude() {
        return building_longitude;
    }

    public void setBuilding_longitude(float building_longitude) {
        this.building_longitude = building_longitude;
    }

    public float getBuilding_latitude() {
        return building_latitude;
    }

    public void setBuilding_latitude(float building_latitude) {
        this.building_latitude = building_latitude;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }
}
