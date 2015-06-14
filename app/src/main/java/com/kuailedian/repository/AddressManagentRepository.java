package com.kuailedian.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kuailedian.entity.AddressEntity;
import com.kuailedian.entity.CatalogEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 磊 on 2015/6/12.
 */
public class AddressManagentRepository extends BaseAsyncRepository {

    public AddressManagentRepository()
    {
        super.HostUri = null;
    }

    @Override
    public Object getData(String responseObj) {

        List<AddressEntity> addressEntityList = new ArrayList<AddressEntity>();

        for(int i=0;i<=3;i++)
        {
            AddressEntity entity = new AddressEntity();
            entity.setId(String.valueOf(i));
            entity.setName("某人" + String.valueOf(i));
            entity.setAddress("河北省唐山市路北区高新技术开发区创业公司B座" + String.valueOf(i) + "层" );
            entity.setMobile("1300000000" + String.valueOf(i));

            addressEntityList.add(entity);
        }
        addressEntityList.get(0).setIsdefault(true);

        return addressEntityList;
    }


}
