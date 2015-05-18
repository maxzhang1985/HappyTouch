package com.kuailedian.entity;

/**
 * Created by maxzhang on 5/18/2015.
 */
public class CatalogEntity {

    public CatalogEntity(String id, String name)
    {
        categoryid = id;
        categoryname = name;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String categoryid;
    public String categoryname;


}
