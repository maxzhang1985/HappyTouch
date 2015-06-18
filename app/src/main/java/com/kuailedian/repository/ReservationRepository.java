package com.kuailedian.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kuailedian.entity.ChildStatusEntity;
import com.kuailedian.entity.GroupStatusEntity;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxzhang on 5/18/2015.
 */
public class ReservationRepository extends BaseAsyncRepository {

    public ReservationRepository()
    {
        super.HostUri = HostsPath.HostUri + "OrderAppInterFace.ashx?method=GetReservationList";
    }


    @Override
    public Object getData(String responseObj) {

        JSONArray objectArray = JSON.parseArray(responseObj);

        List<GroupStatusEntity> groupList = new ArrayList<>();
        for(int i=0;i<=objectArray.size() - 1 ; i++)
        {

            GroupStatusEntity groupItem = new GroupStatusEntity();
            JSONObject jsonObject = objectArray.getJSONObject(i);

            groupItem.setGroupName(jsonObject.getString("date"));
            groupItem.setIstoday(jsonObject.getBoolean("istoday"));
            JSONArray foodList =  jsonObject.getJSONArray("foods");

            List<ChildStatusEntity> childList = new ArrayList<ChildStatusEntity>();
            for(int j=0;j<=foodList.size()-1;j++)
            {
                JSONObject food = foodList.getJSONObject(j);

                ChildStatusEntity child = new ChildStatusEntity();
                child.setProductsid(food.getString("productid"));
                child.setProductName(food.getString("productname"));
                child.setImg(food.getString("img"));
                child.setUnitprice(food.getString("unitprice"));


                childList.add(child);
            }

            groupItem.setChildList(childList);
            groupList.add(groupItem);


        }


         return groupList;

    }





}
