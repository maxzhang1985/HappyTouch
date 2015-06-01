package com.kuailedian.repository;

import com.loopj.android.http.RequestParams;

/**
 * Created by maxzhang on 5/18/2015.
 */
public interface IAsyncRepository {

    void Get(RequestParams params,AsyncCallBack callback);

}
