package com.grocery.model;

import android.content.Intent;

import java.io.Serializable;

/**
 * Created by ThanhBeo on 11/10/2017.
 */

public class InforOrdersModel extends Intent implements Serializable {
    private int new_order;
    private int preparing_order;
    private int schedule_order;
    private int bulk_order;
    private int despatched_order;
    private int delivered_order;
    private int today_order;
    private int cancellled_order;
    private int number_notification;
    private int all_user;
    private int live_user;
    private String money_today;
    private String money_week;
    private String money_month;
    private String money_year;

    public int getCancellled_order() {
        return cancellled_order;
    }

    public void setCancellled_order(int cancellled_order) {
        this.cancellled_order = cancellled_order;
    }

    public int getNew_order() {
        return new_order;
    }

    public void setNew_order(int new_order) {
        this.new_order = new_order;
    }

    public int getPreparing_order() {
        return preparing_order;
    }

    public void setPreparing_order(int preparing_order) {
        this.preparing_order = preparing_order;
    }

    public int getSchedule_order() {
        return schedule_order;
    }

    public void setSchedule_order(int schedule_order) {
        this.schedule_order = schedule_order;
    }

    public int getBulk_order() {
        return bulk_order;
    }

    public void setBulk_order(int bulk_order) {
        this.bulk_order = bulk_order;
    }

    public int getDespatched_order() {
        return despatched_order;
    }

    public void setDespatched_order(int despatched_order) {
        this.despatched_order = despatched_order;
    }

    public int getDelivered_order() {
        return delivered_order;
    }

    public void setDelivered_order(int delivered_order) {
        this.delivered_order = delivered_order;
    }

    public int getToday_order() {
        return today_order;
    }

    public void setToday_order(int today_order) {
        this.today_order = today_order;
    }

    public int getNumber_notification() {
        return number_notification;
    }

    public void setNumber_notification(int number_notification) {
        this.number_notification = number_notification;
    }

    public int getAll_user() {
        return all_user;
    }

    public void setAll_user(int all_user) {
        this.all_user = all_user;
    }

    public int getLive_user() {
        return live_user;
    }

    public void setLive_user(int live_user) {
        this.live_user = live_user;
    }

    public String getMoney_today() {
        return money_today;
    }

    public void setMoney_today(String money_today) {
        this.money_today = money_today;
    }

    public String getMoney_week() {
        return money_week;
    }

    public void setMoney_week(String money_week) {
        this.money_week = money_week;
    }

    public String getMoney_month() {
        return money_month;
    }

    public void setMoney_month(String money_month) {
        this.money_month = money_month;
    }

    public String getMoney_year() {
        return money_year;
    }

    public void setMoney_year(String money_year) {
        this.money_year = money_year;
    }
}
