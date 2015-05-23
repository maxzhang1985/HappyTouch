package com.kuailedian.entity;

/**
 * Created by maxzhang on 5/18/2015.
 */
public class ProductEntity {

    private String imgurl;
    private String productid;
    private String productname;
    private String productmoney;


    public ProductEntity(){}

    public ProductEntity(String url,String id,String name,String money){

        this.imgurl = url;
        this.productid = id;
        this.productname = name;
        this.productmoney = money;

    }



    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductmoney() {
        return productmoney;
    }

    public void setProductmoney(String productmoney) {
        this.productmoney = productmoney;
    }


    @Override
    public String toString() {
        return this.getProductname();
    }
}
