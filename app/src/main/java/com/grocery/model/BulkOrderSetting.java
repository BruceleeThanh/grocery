package com.grocery.model;

import com.google.gson.annotations.SerializedName;

public class BulkOrderSetting {

    private int id;

    @SerializedName("grocery_id")
    private int groceryId;

    @SerializedName("setting_type")
    private int settingType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroceryId() {
        return groceryId;
    }

    public void setGroceryId(int groceryId) {
        this.groceryId = groceryId;
    }

    public int getSettingType() {
        return settingType;
    }

    public void setSettingType(int settingType) {
        this.settingType = settingType;
    }
}
