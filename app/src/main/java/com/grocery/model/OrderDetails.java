package com.grocery.model;

import java.util.ArrayList;

/**
 * Created by ThanhBeo on 02/10/2017.
 */

public class OrderDetails {
    private ItemOrderInfor order_info;
    private ArrayList<ItemDeliveryBoy> delivery_boys;

    private ArrayList<ItemProductsOrders> products;

    public ArrayList<ItemProductsOrders> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ItemProductsOrders> products) {
        this.products = products;
    }

    public ItemOrderInfor getOrder_info() {
        return order_info;
    }



    public void setOrder_info(ItemOrderInfor order_info) {
        this.order_info = order_info;
    }

    public ArrayList<ItemDeliveryBoy> getDelivery_boys() {
        return delivery_boys;
    }

    public void setDelivery_boys(ArrayList<ItemDeliveryBoy> delivery_boys) {
        this.delivery_boys = delivery_boys;
    }
}
