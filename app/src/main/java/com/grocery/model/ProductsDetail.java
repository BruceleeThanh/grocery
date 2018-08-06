package com.grocery.model;

import java.util.ArrayList;

/**
 * Created by ThanhBeo on 20/09/2017.
 */

public class ProductsDetail {
    private ItemManagerProductsUpdate product;
    private ArrayList<UnitModel> unit;

    public ItemManagerProductsUpdate getProduct() {
        return product;
    }

    public void setProduct(ItemManagerProductsUpdate product) {
        this.product = product;
    }

    public ArrayList<UnitModel> getUnit() {
        return unit;
    }

    public void setUnit(ArrayList<UnitModel> unit) {
        this.unit = unit;
    }
}
