package com.kuailedian.repository;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kuailedian.entity.ProductEntity;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

/**
 * Created by maxzhang on 5/18/2015.
 */
public class ProductsRepository extends  BaseAsyncRepository {

    public ProductsRepository()
    {
        super.HostUri = HostsPath.HostUri + "OrderAppInterFace.ashx?method=GetProductsList";
    }



    @Override
    public Object getData(String responseObj) {

        JSONArray objectArray = JSON.parseArray(responseObj);
        ArrayList<ProductEntity> productList =new ArrayList<ProductEntity>();

        for(int i=0 ; i<=objectArray.size()-1;i++)
        {
            JSONObject item = objectArray.getJSONObject(i);
            productList.add(new ProductEntity( item.getString("img"), item.getString("productid") , item.getString("productname"),item.getString("unitprice") ));
        }

        return productList;

    }
}
