package com.grocery.model;

/**
 * Created by ThanhBeo on 22/06/2017.
 */

public class ItemStockNotifi {
    private int aed;
    private int qte;
    private int weight;
    private String note;

    public int getAed() {
        return aed;
    }

    public void setAed(int aed) {
        this.aed = aed;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ItemStockNotifi() {
    }

    public ItemStockNotifi(int aed, int qte, int weight, String note) {
        this.aed = aed;
        this.qte = qte;
        this.weight = weight;
        this.note = note;
    }
}
