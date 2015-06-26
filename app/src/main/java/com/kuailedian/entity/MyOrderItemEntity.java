package com.kuailedian.entity;

/**
 * Created by Maxzhang on 2015/6/26.
 */
public class MyOrderItemEntity {

    private String id;
    private String date;
    private String price;
    private String state;
    private String statemessage;

    public String getStatemessage() {
        return statemessage;
    }

    public void setStatemessage(String statemessage) {
        this.statemessage = statemessage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
