package com.kuailedian.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kuailedian.entity.CatalogEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxzhang on 5/18/2015.
 */
public class ProductsCatalogRepository extends  BaseAsyncRepository{

    public ProductsCatalogRepository()
    {
        super.HostUri = HostsPath.HostUri + "OrderAppInterFace.ashx?method=GetProductsCategory";
    }





    @Override
    public Object getData(String responseObj) {

        JSONArray objectArray = JSON.parseArray(responseObj);

        List<CatalogEntity> catalogList = new ArrayList<CatalogEntity>();

        for(int i=0 ; i<=objectArray.size()-1;i++)
        {
            JSONObject item = objectArray.getJSONObject(i);
            catalogList.add(new CatalogEntity( item.getString("categoryid") , item.getString("categoryname") ));
        }




        return catalogList;
    }
}
