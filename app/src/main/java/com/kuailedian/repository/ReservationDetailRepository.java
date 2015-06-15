package com.kuailedian.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kuailedian.entity.ProductDetailEntity;

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

        ProductDetailEntity detailEntity = new ProductDetailEntity();

        detailEntity.setRemark(object.getString("remark"));
        detailEntity.setDeliveryare(object.getString("deliveryarea"));

        ArrayList<String> imglist = new ArrayList<>();
        JSONArray array = object.getJSONArray("imglist");
        for(int i =0;i<=array.size()-1;i++) {
            imglist.add(array.getString(i));
        }

        detailEntity.setImglist(imglist);


        return detailEntity;
    }

}
