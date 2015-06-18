package com.kuailedian.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kuailedian.entity.AddressEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 磊 on 2015/6/12.
 */
public class AddressManagentRepository extends BaseAsyncRepository {

    public AddressManagentRepository()
    {
        super.HostUri = HostsPath.HostUri + "OrderAppInterFace.ashx?method=GetUserAddressList";
    }

    @Override
    public Object getData(String responseObj) {
        List<AddressEntity> addressEntityList = new ArrayList<AddressEntity>();
        try {

            JSONArray objectArray = JSON.parseArray(responseObj);
            for(int i =0 ; i<=objectArray.size()-1 ; i++)
            {
                JSONObject object = objectArray.getJSONObject(i);
                AddressEntity entity = new AddressEntity();
                entity.setId(  object.getString("id"));
                entity.setName( object.getString("name"));
                entity.setAddress( object.getString("address"));
                entity.setMobile( object.getString("tel"));
                boolean isdef = false;
                if(object.getString("flag").equals("1"))
                    isdef = true;
                entity.setIsdefault(isdef);
                addressEntityList.add(entity);
            }
        }
        catch (Exception e) {
        }
        return addressEntityList;
    }


}
