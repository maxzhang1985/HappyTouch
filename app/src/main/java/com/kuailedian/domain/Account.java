package com.kuailedian.domain;

import de.greenrobot.event.EventBus;


import java.util.Date;


/**
 * Created by maxzhang on 4/9/2015.
 */
public class Account {

    private String name;
    private int age;
    private String mobilePhone;
    private String nation;
    private Date birthday;
    private String email;
    private String passwrod;

    public String getPasswrod() {
        return passwrod;
    }

    public void setPasswrod(String passwrod) {
        this.passwrod = passwrod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private boolean lockChecked ;

    public Account(String phone)
    {
        this.mobilePhone = phone;
        lockChecked = false;
    }




    public void CheckIn()
    {
        if(!lockChecked)
        {
            //


        }


    }


    public boolean Register()
    {

        return false;
    }


    public boolean ChangePassword(String passWord)
    {

        return true;
    }


}
