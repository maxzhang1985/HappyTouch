package com.kuailedian.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by maxzhang on 6/11/2015.
 */
public class AddressEntity implements Serializable {

    @JSONField(name="userCode")
    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    private String usercode;


    @JSONField(name="id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String name;
    private String mobile;
    private String Address;


    public boolean getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(boolean isdefault) {
        this.isdefault = isdefault;
    }

    private boolean isdefault;

    @JSONField(name="flag")
    public String getFlag()
    {
        String flag = "0";
        if(isdefault)
            flag = "1";
        return flag;
    }

    @JSONField(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @JSONField(name="tel")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    @JSONField(name="address")
    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public String toString() {
        return this.getAddress();
    }
}
