package com.kuailedian.repository;

/**
 * Created by maxzhang on 5/18/2015.
 */
public abstract class BaseAsyncRepository implements  IAsyncRepository {

    protected String HostUri;



    @Override
    public   void Get(PageModel page, final AsyncCallBack callback) {

//        RequestParams params = new RequestParams();
//
//        HttpUtilsAsync.get(HostUri, params, new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                  Object object =  JSON.parse(responseString);
//                  callback.onDataReceive(object,null);
//            }
//        });

        callback.onDataReceive( getData(null),null);


    }



    public Object getData(Object responseObj)
    {
        return null;
    }





}
