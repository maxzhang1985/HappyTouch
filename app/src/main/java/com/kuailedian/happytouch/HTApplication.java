package com.kuailedian.happytouch;

import android.app.Application;

import java.util.HashMap;

/**
 * Created by 磊 on 2015/4/13.
 */
public class HTApplication extends Application {


    HashMap<String,Object> services = new HashMap<String, Object>();

    public <T> T GetSystemDomain(Class<T> type)
    {
       return (T)services.get(type.getSimpleName());
    }


    public void SetSystemDomain(Class<?> type,Object val)
    {
        services.put(type.getSimpleName(),val);
    }



    @Override
    public void onCreate() {
        super.onCreate();
    }
}
