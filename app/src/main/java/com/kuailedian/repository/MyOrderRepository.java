package com.kuailedian.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kuailedian.entity.MyOrderItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxzhang on 2015/6/26.
 */
public class MyOrderRepository extends  BaseAsyncRepository {

    public MyOrderRepository()
    {
        super.HostUri = HostsPath.HostUri + "OrderAppInterFace.ashx?method=GetOrdersList";
    }

    @Override
    public Object getData(String responseObj) {

        JSONArray objectArray = JSON.parseArray(responseObj);
        List<MyOrderItemEntity> list = new ArrayList<MyOrderItemEntity>();
        for(int i=0 ; i<=objectArray.size()-1;i++)
        {
            JSONObject object = objectArray.getJSONObject(i);

            MyOrderItemEntity detailEntity = new MyOrderItemEntity();

            detailEntity.setId(object.getString("orderid"));
            detailEntity.setDate(object.getString("date"));
            detailEntity.setPrice(object.getString("price"));
            detailEntity.setState(object.getString("state"));

            list.add(detailEntity);
        }

        return list;
    }



}
