package com.kuailedian.repository;

import com.kuailedian.entity.ProductEntity;

import java.util.ArrayList;

/**
 * Created by maxzhang on 5/18/2015.
 */
public class ProductsRepository extends  BaseAsyncRepository {

    @Override
    public Object getData(Object responseObj) {

        ArrayList<ProductEntity> productList =new ArrayList<ProductEntity>();
        productList.add(new ProductEntity("","1","p1","100"));
        productList.add(new ProductEntity("","2","p2","100"));
        productList.add(new ProductEntity("","3","p3","100"));
        productList.add(new ProductEntity("","4","p4","100"));
        productList.add(new ProductEntity("","5","p5","100"));
        productList.add(new ProductEntity("","6","p6","100"));
        productList.add(new ProductEntity("","7","p7","100"));
        productList.add(new ProductEntity("","8","p8","100"));
        productList.add(new ProductEntity("","9","p9","100"));
        productList.add(new ProductEntity("","10","p10","100"));



        return productList;

    }
}
