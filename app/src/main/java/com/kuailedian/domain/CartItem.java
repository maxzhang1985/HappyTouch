package com.kuailedian.domain;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by maxzhang on 6/3/2015.
 */
public class CartItem {

    public CartItem()
    {
        isselected = true;
    }

    private String id;
    private String name;
    private float money;
    private String type;

    public boolean IsSelected() {
        return isselected;
    }

    public void setIsSelected(boolean isselected) {
        this.isselected = isselected;
    }

    private boolean isselected;

    @JSONField(name="amount")
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    private int amount;

    @JSONField(name="id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JSONField(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JSONField(name="price")
    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    @JSONField(name="type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("id:%s,name:%s,money:%f,selected:%b",id,name,money,isselected);
    }
}
