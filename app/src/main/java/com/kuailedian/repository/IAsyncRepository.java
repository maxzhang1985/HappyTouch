package com.kuailedian.repository;

/**
 * Created by maxzhang on 5/18/2015.
 */
public interface IAsyncRepository {

    void Get(PageModel page,AsyncCallBack callback);

}
