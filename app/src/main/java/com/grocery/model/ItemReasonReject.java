package com.grocery.model;

/**
 * Created by ThanhBeo on 03/10/2017.
 */

public class ItemReasonReject {
    private String reason;
    private boolean isChoose = false;
    private int id;

    public ItemReasonReject(String reason, int id) {
        this.reason = reason;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
