package com.grocery.model;

import java.io.Serializable;

/**
 * Created by ThanhBeo on 29/09/2017.
 */

public class ItemOrders implements Serializable{
    private int id;
    private int user_id;
    private int grocery_id;
    private int order_type;
    private int min_pick_up;
    private float total_money;
    private String note;
    private int status;
    private int is_delete;
    private int delivery_address_id;
    private float tip_money;
    private int is_redeem;
    private float bring_change_money;
    private float reward_money;
    private float cash_back_money;
    private String schedule_date;
    private String schedule_time;
    private int schedule_frequency;
    private int schedule_payment;
    private String schedule_comment;
    private String created_at;
    private String updated_at;
    private String delivery_time;
    private int delivery_boy_id;
    private int reject_type;
    private String reject_comment;
    private String flat_no;
    private String building_name;
    private String customer_name;
    private String deliver_before;
    private int remaining_time;
    private String delivery_boy_name;
    private int is_confirm;
    private int payment_type;

    public ItemOrders() {
    }

    public ItemOrders(int id, int order_type) {
        this.id = id;
        this.order_type = order_type;
    }

    public int getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(int payment_type) {
        this.payment_type = payment_type;
    }

    public void setTotal_money(float total_money) {
        this.total_money = total_money;
    }

    public int getIs_confirm() {
        return is_confirm;
    }

    public void setIs_confirm(int is_confirm) {
        this.is_confirm = is_confirm;
    }

    public String getDelivery_boy_name() {
        return delivery_boy_name;
    }

    public void setDelivery_boy_name(String delivery_boy_name) {
        this.delivery_boy_name = delivery_boy_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getGrocery_id() {
        return grocery_id;
    }

    public void setGrocery_id(int grocery_id) {
        this.grocery_id = grocery_id;
    }

    public int getOrder_type() {
        return order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }

    public int getMin_pick_up() {
        return min_pick_up;
    }

    public void setMin_pick_up(int min_pick_up) {
        this.min_pick_up = min_pick_up;
    }

    public Float getTotal_money() {
        return total_money;
    }

    public void setTotal_money(Float total_money) {
        this.total_money = total_money;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public int getDelivery_address_id() {
        return delivery_address_id;
    }

    public void setDelivery_address_id(int delivery_address_id) {
        this.delivery_address_id = delivery_address_id;
    }

    public float getTip_money() {
        return tip_money;
    }

    public void setTip_money(float tip_money) {
        this.tip_money = tip_money;
    }

    public int getIs_redeem() {
        return is_redeem;
    }

    public void setIs_redeem(int is_redeem) {
        this.is_redeem = is_redeem;
    }

    public float getBring_change_money() {
        return bring_change_money;
    }

    public void setBring_change_money(float bring_change_money) {
        this.bring_change_money = bring_change_money;
    }

    public float getReward_money() {
        return reward_money;
    }

    public void setReward_money(float reward_money) {
        this.reward_money = reward_money;
    }

    public float getCash_back_money() {
        return cash_back_money;
    }

    public void setCash_back_money(float cash_back_money) {
        this.cash_back_money = cash_back_money;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(String schedule_date) {
        this.schedule_date = schedule_date;
    }

    public String getSchedule_time() {
        return schedule_time;
    }

    public void setSchedule_time(String schedule_time) {
        this.schedule_time = schedule_time;
    }

    public int getSchedule_frequency() {
        return schedule_frequency;
    }

    public void setSchedule_frequency(int schedule_frequency) {
        this.schedule_frequency = schedule_frequency;
    }

    public int getSchedule_payment() {
        return schedule_payment;
    }

    public void setSchedule_payment(int schedule_payment) {
        this.schedule_payment = schedule_payment;
    }

    public String getSchedule_comment() {
        return schedule_comment;
    }

    public void setSchedule_comment(String schedule_comment) {
        this.schedule_comment = schedule_comment;
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

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public int getDelivery_boy_id() {
        return delivery_boy_id;
    }

    public void setDelivery_boy_id(int delivery_boy_id) {
        this.delivery_boy_id = delivery_boy_id;
    }

    public int getReject_type() {
        return reject_type;
    }

    public void setReject_type(int reject_type) {
        this.reject_type = reject_type;
    }

    public String getReject_comment() {
        return reject_comment;
    }

    public void setReject_comment(String reject_comment) {
        this.reject_comment = reject_comment;
    }

    public String getFlat_no() {
        return flat_no;
    }

    public void setFlat_no(String flat_no) {
        this.flat_no = flat_no;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getDeliver_before() {
        return deliver_before;
    }

    public void setDeliver_before(String deliver_before) {
        this.deliver_before = deliver_before;
    }

    public int getRemaining_time() {
        return remaining_time;
    }

    public void setRemaining_time(int remaining_time) {
        this.remaining_time = remaining_time;
    }
}
