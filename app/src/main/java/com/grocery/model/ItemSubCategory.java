package com.grocery.model;

/**
 * Created by ThanhBeo on 16/09/2017.
 */

public class ItemSubCategory {
    private int id;
    private String name;
    private String description;
    private int category_id;
    private String created_at;
    private String updated_at;
    private int is_active;
    private int is_added;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
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

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public int getIs_added() {
        return is_added;
    }

    public void setIs_added(int is_added) {
        this.is_added = is_added;
    }
}
