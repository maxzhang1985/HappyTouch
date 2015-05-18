package com.kuailedian.repository;

import com.kuailedian.entity.CatalogEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxzhang on 5/18/2015.
 */
public class ProductsCatalogRepository extends  BaseAsyncRepository{

    @Override
    public Object getData(Object responseObj) {

        List<CatalogEntity> catalogList = new ArrayList<CatalogEntity>();

        catalogList.add(new CatalogEntity("001","the item 1"));
        catalogList.add(new CatalogEntity("002","the item 2"));
        catalogList.add(new CatalogEntity("003","the item 3"));
        catalogList.add(new CatalogEntity("004","the item 4"));
        catalogList.add(new CatalogEntity("005","the item 5"));

        return catalogList;
    }
}
