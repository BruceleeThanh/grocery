package com.grocery.model;

/**
 * Created by ThanhBeo on 15/10/2017.
 */

public class DeliveryTime {
    private int id;
    private int grocery_id;
    private int delivery_timing;
    private int despatching_timing;
    private String sunday;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String created_at;
    private String updated_at;

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

    public int getDelivery_timing() {
        return delivery_timing;
    }

    public void setDelivery_timing(int delivery_timing) {
        this.delivery_timing = delivery_timing;
    }

    public int getDespatching_timing() {
        return despatching_timing;
    }

    public void setDespatching_timing(int despatching_timing) {
        this.despatching_timing = despatching_timing;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
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
}
