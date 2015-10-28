package com.kuailedian.repository;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kuailedian.entity.FoodDetailEntity;
import com.kuailedian.entity.SubFoodEntity;

import java.util.ArrayList;

/**
 * Created by maxzhang on 6/15/2015.
 */
public class ReservationDetailRepository extends BaseAsyncRepository {

     public ReservationDetailRepository()
     {
         super.HostUri = HostsPath.HostUri + "OrderAppInterFace.ashx?method=GetFoodDetails";
     }


    @Override
    public Object getData(String responseObj) {

        JSONObject object = JSON.parseObject(responseObj);
        Log.v("detail",responseObj);
        FoodDetailEntity detailEntity = new FoodDetailEntity();

        detailEntity.setRemark(object.getString("remark"));
        detailEntity.setDeliveryare(object.getString("deliveryarea"));

        ArrayList<String> imglist = new ArrayList<>();
        JSONArray array = object.getJSONArray("imglist");
        for(int i =0;i<=array.size()-1;i++) {
            imglist.add(array.getString(i));
        }

        detailEntity.setImglist(imglist);

        JSONArray subfoodArray =  object.getJSONArray("subfoodlist");

        ArrayList<SubFoodEntity> subFoodEntities = new ArrayList<SubFoodEntity>();
        if(subfoodArray!=null)
        {
            for(int i =0;i<=subfoodArray.size()-1;i++) {
               JSONObject subfoodJson =  subfoodArray.getJSONObject(i);
                SubFoodEntity subfood = new SubFoodEntity();
                subfood.setName(subfoodJson.getString("productname"));

                JSONArray subfoodImgArray = subfoodJson.getJSONArray("img");
                try {
                    subfood.setImage(subfoodImgArray.getString(0));
                }catch (Exception ex){}
                subFoodEntities.add(subfood);

            }
            detailEntity.setSubfoodlist(subFoodEntities);
        }


        return detailEntity;
    }

}
