package com.kuailedian.repository;

import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync;

import org.apache.http.Header;

/**
 * Created by maxzhang on 5/18/2015.
 */
public abstract class BaseAsyncRepository implements  IAsyncRepository {

    protected String HostUri = null;

    @Override
    public  void Get(RequestParams params, final AsyncCallBack callback) {

        if(HostUri != null) {


            Log.v("print uri", HostUri + params.toString());
            HttpUtilsAsync.setTimeout(8000);

            HttpUtilsAsync.get(HostUri, params, new TextHttpResponseHandler("GB2312") {

                @Override
                public void onSuccess(int i, Header[] headers, String responseString) {
                    Log.v("getdatalogonSuccess", responseString);
                    // JSONArray object = JSON.parseArray(responseString);
                    //Day array = object.getJSONObject(0).getJSONArray("days").getObject(0, Day.class);
                    //Log.v("getdatalog", array.getDate());
                    callback.onDataReceive(getData(responseString), null);
                }

                @Override
                public void onFailure(int i, Header[] headers, String responseString, Throwable throwable) {
                    Log.v("getdatalog", "failure");
                    callback.onDataReceive(null, null);
                }



            });
        }
        else
        {
            callback.onDataReceive(getData(null), null);
        }


    }



    public Object getData(String responseObj)
    {
        return null;
    }





}
