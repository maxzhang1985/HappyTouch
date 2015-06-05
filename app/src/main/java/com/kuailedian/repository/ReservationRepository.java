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
        //Day array = object.getJSONObject(0).getJSONArray("days").getObject(0, Day.class);
        //Log.v("getdatalog", array.getDate());


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


    private List<GroupStatusEntity> getListData() {
        List<GroupStatusEntity> groupList;
        String[] strArray = new String[] { "2015年5月4日", "2015年5月5日", "2015年5月6日", "2015年5月7日", "2015年5月8日", "2015年5月9日", "2015年5月10日" };
        String[][] childTimeArray = new String[][] {
                { "俯卧撑十次", "仰卧起坐二十次", "大喊我爱紫豪二十次", "每日赞紫豪一次" },
                { "亲，快快滴点赞哦~" }, { "没有赞的，赶紧去赞哦~" },   { "俯卧撑十次", "仰卧起坐二十次", "大喊我爱紫豪二十次", "每日赞紫豪一次" },
                { "亲，快快滴点赞哦~" }, { "没有赞的，赶紧去赞哦~" },   { "俯卧撑十次", "仰卧起坐二十次", "大喊我爱紫豪二十次", "每日赞紫豪一次" },
        };

        groupList = new ArrayList<GroupStatusEntity>();
        for (int i = 0; i < strArray.length; i++) {
            GroupStatusEntity groupStatusEntity = new GroupStatusEntity();
            groupStatusEntity.setGroupName(strArray[i]);

            List<ChildStatusEntity> childList = new ArrayList<ChildStatusEntity>();

            for (int j = 0; j < childTimeArray[i].length; j++) {
                ChildStatusEntity childStatusEntity = new ChildStatusEntity();
                childStatusEntity.setProductName(childTimeArray[i][j]);
                childList.add(childStatusEntity);
            }

            groupStatusEntity.setChildList(childList);
            groupList.add(groupStatusEntity);
        }
        return groupList;
    }


}
