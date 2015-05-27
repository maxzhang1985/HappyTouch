package com.kuailedian.repository;

import android.util.Log;
import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.kuailedian.entity.Generate.Reservation.Day;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync;
import org.apache.http.Header;

/**
 * Created by maxzhang on 5/18/2015.
 */
public abstract class BaseAsyncRepository implements  IAsyncRepository {

    protected String HostUri;



    @Override
    public   void Get(PageModel page, final AsyncCallBack callback) {

        RequestParams params = new RequestParams();

        HttpUtilsAsync.get(HostUri, params, new TextHttpResponseHandler("UTF-8") {

            @Override
            public void onSuccess(int i, Header[] headers, String responseString) {
                Log.v("getdatalog",responseString);
                JSONArray object = JSON.parseArray(responseString);

                Day array = object.getJSONObject(0).getJSONArray("days").getObject(0,Day.class);




                Log.v("getdatalog", array.getDate());
                callback.onDataReceive(object, null);
            }

            @Override
            public void onFailure(int i, Header[] headers, String responseString, Throwable throwable) {
                Log.v("getdatalog","failure");
                callback.onDataReceive(null, null);
            }
        });

        callback.onDataReceive( getData(null),null);


    }



    public Object getData(Object responseObj)
    {
        return null;
    }





}
