package com.kuailedian.domain;

import de.greenrobot.event.EventBus;

/**
 * Created by maxzhang on 4/9/2015.
 */
public class Account {

    private String name;
    private int age;
    private String mobilePhone;
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





}
