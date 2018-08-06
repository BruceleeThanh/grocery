package com.grocery.model;

/**
 * Created by ThanhBeo on 05/07/2017.
 */

public class ItemUsers {
    private int ava;
    private String name;
    private int flat;
    private String buildingName;

    public int getAva() {
        return ava;
    }

    public String getName() {
        return name;
    }

    public int getFlat() {
        return flat;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public ItemUsers(int ava, String name, int flat, String buildingName) {

        this.ava = ava;
        this.name = name;
        this.flat = flat;
        this.buildingName = buildingName;
    }
}
