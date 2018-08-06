package com.grocery.model;

/**
 * Created by ThanhBeo on 17/07/2017.
 */

public class ItemManageOffers {
    private String iD;
    private String category;
    private String name;

    public ItemManageOffers(String iD, String category, String name) {
        this.iD = iD;
        this.category = category;
        this.name = name;
    }

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
