package com.kuailedian.repository;

import java.util.HashMap;

/**
 * Created by maxzhang on 5/18/2015.
 */
public class PageModel {

    public String Keyword;
    public int Index;
    public int Count;

    public PageModel()
    {
       params = new HashMap<String,String>();
    }

    private HashMap<String,String> params;


    public HashMap<String,String> getParams()
    {
        return params;
    }

}
