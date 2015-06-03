package com.kuailedian.domain;

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

    public boolean getIsSelected() {
        return isselected;
    }

    public void setIsSelected(boolean isselected) {
        this.isselected = isselected;
    }

    private boolean isselected;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    private int amount;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
