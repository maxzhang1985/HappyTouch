package com.kuailedian.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.kuailedian.domain.CartItem;

import java.util.List;

/**
 * Created by maxzhang on 6/24/2015.
 */
public class MyOrderEntity {

    private String orderid;
    private String userid;
    private String state;
    private String addresscode;
    private String paycode;

    private List<CartItem> cart;


    public MyOrderEntity()
    {
        orderid = "";
        paycode = "001";
        state ="0";
    }

    public MyOrderEntity(String uid, String aid)
    {
        orderid = "";

        paycode = "001";
        state ="0";
        this.userid = uid;
        this.addresscode = aid;
    }


    @JSONField(name="orderid")
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @JSONField(name="userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @JSONField(name="state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @JSONField(name="addresscode")
    public String getAddresscode() {
        return addresscode;
    }

    public void setAddresscode(String addresscode) {
        this.addresscode = addresscode;
    }

    @JSONField(name="paycode")
    public String getPaycode() {
        return paycode;
    }

    public void setPaycode(String paycode) {
        this.paycode = paycode;
    }



    @JSONField(name="cart")
    public List<CartItem> getCart() {
        return cart;
    }

    public void setCart(List<CartItem> cart) {
        this.cart = cart;
    }
}
