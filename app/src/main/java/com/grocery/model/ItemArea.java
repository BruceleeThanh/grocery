package com.grocery.model;

import java.util.ArrayList;

/**
 * Created by ThanhBeo on 16/10/2017.
 */

public class ItemArea {
    private int id;
    private String name;
    private int city_id;
    private String created_at;
    private String updated_at;
    private ArrayList<ItemBuilding2> buildings;

    public ItemArea(int id, String name, int city_id, String created_at, String updated_at, ArrayList<ItemBuilding2> buildings) {
        this.id = id;
        this.name = name;
        this.city_id = city_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.buildings = buildings;
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

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
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

    public ArrayList<ItemBuilding2> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<ItemBuilding2> buildings) {
        this.buildings = buildings;
    }
}
