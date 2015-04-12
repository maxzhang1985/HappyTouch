package com.kuailedian.domain;

import de.greenrobot.event.EventBus;
import com.loopj.android.*;
import com.loopj.android.http.AsyncHttpClient;

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
