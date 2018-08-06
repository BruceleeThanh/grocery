package com.grocery.model;

/**
 * Created by ThanhBeo on 25/09/2017.
 */

public class ItemManagerProductsUpdate extends ItemManagerProductsAdd {
    private int instock;
    private int unit_id_min;
    private int min_stock;
    private int instock_status;
    private int status;
    private String from_date;
    private String end_date;
    private int limit_number;
    private float offer_price;

    public int getInstock() {
        return instock;
    }

    public void setInstock(int instock) {
        this.instock = instock;
    }

    public int getUnit_id_min() {
        return unit_id_min;
    }

    public void setUnit_id_min(int unit_id_min) {
        this.unit_id_min = unit_id_min;
    }

    public int getMin_stock() {
        return min_stock;
    }

    public void setMin_stock(int min_stock) {
        this.min_stock = min_stock;
    }

    public int getInstock_status() {
        return instock_status;
    }

    public void setInstock_status(int instock_status) {
        this.instock_status = instock_status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getLimit_number() {
        return limit_number;
    }

    public void setLimit_number(int limit_number) {
        this.limit_number = limit_number;
    }

    public float getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(float offer_price) {
        this.offer_price = offer_price;
    }
}
