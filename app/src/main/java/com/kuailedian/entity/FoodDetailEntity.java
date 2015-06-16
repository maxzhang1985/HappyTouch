package com.kuailedian.entity;

import java.util.ArrayList;

/**
 * Created by maxzhang on 6/10/2015.
 */
public class FoodDetailEntity {
    private String remark;
    private String deliveryare;
    private ArrayList<String> imglist;

    private ArrayList<SubFoodEntity> subfoodlist;

    public ArrayList<SubFoodEntity> getSubfoodlist() {
        return subfoodlist;
    }

    public void setSubfoodlist(ArrayList<SubFoodEntity> subfoodlist) {
        this.subfoodlist = subfoodlist;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeliveryare() {
        return deliveryare;
    }

    public void setDeliveryare(String deliveryare) {
        this.deliveryare = deliveryare;
    }

    public ArrayList<String> getImglist() {
        return imglist;
    }

    public void setImglist(ArrayList<String> imglist) {
        this.imglist = imglist;
    }



}
