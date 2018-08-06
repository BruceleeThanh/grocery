package com.grocery.model;

/**
 * Created by ThanhBeo on 29/06/2017.
 */

public class ItemMenu {
    private int number;
    private String name;
    private int note;
    private int src;
    public boolean checkBG;

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public int getNote() {
        return note;
    }

    public int getSrc() {
        return src;
    }


    public ItemMenu(int number, String name, int note, int src) {
        this.number = number;
        this.name = name;
        this.note = note;
        this.src = src;
        this.checkBG = true;
    }
}
