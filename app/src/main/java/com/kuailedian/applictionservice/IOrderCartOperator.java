package com.kuailedian.applictionservice;

import android.view.View;

import com.kuailedian.domain.CartItem;

/**
 * Created by maxzhang on 5/15/2015.
 */
public interface IOrderCartOperator {
    void AddProducts(View itemview, CartItem item);


}
