package com.kuailedian.entity.Generate.Reservation;

import java.util.List;


public class Food{

    private static final String FIELD_PRODUCTTYPE = "producttype";
    private static final String FIELD_UNITPRICE = "unitprice";
    private static final String FIELD_PRODUCTNAME = "productname";
    private static final String FIELD_PRODUCTID = "productid";
    private static final String FIELD_IMG = "img";


    private String mProducttype;
    private int mUnitprice;
    private String mProductname;
    private int mProductid;
    private List<Img> mImgs;


    public Food(){

    }

    public void setProducttype(String producttype) {
        mProducttype = producttype;
    }

    public String getProducttype() {
        return mProducttype;
    }

    public void setUnitprice(int unitprice) {
        mUnitprice = unitprice;
    }

    public int getUnitprice() {
        return mUnitprice;
    }

    public void setProductname(String productname) {
        mProductname = productname;
    }

    public String getProductname() {
        return mProductname;
    }

    public void setProductid(int productid) {
        mProductid = productid;
    }

    public int getProductid() {
        return mProductid;
    }

    public void setImgs(List<Img> imgs) {
        mImgs = imgs;
    }

    public List<Img> getImgs() {
        return mImgs;
    }


}