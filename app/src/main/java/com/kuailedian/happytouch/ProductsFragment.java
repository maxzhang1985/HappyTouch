package com.kuailedian.happytouch;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kuailedian.entity.CatalogEntity;
import com.kuailedian.repository.AsyncCallBack;
import com.kuailedian.repository.IAsyncRepository;
import com.kuailedian.repository.ProductsCatalogRepository;
import com.kuailedian.repository.ProductsRepository;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.maxwin.view.XListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends OrderFragmentBase  implements XListView.IXListViewListener {



    @InjectView(R.id.catalogListView)
    ListView catalogListView;

    @InjectView(R.id.productsListView)
    XListView productsListView;

    IAsyncRepository catalogRepository = new ProductsCatalogRepository();

    IAsyncRepository productRepository = new ProductsRepository();



    private ArrayAdapter<CatalogEntity> catalogAdapter;

    private ArrayAdapter<String> productsAdapter;

    private Handler mHandler;

    public ProductsFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static ProductsFragment newInstance() {

        ProductsFragment   fragment = new ProductsFragment();

        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_products, container, false);

        ButterKnife.inject(this, view);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //catalogListView

        catalogAdapter = new ArrayAdapter<CatalogEntity>(view.getContext(),R.layout.catalog_item,R.id.catalog_item_name, new ArrayList<CatalogEntity>());
        catalogListView.setAdapter(catalogAdapter);
        catalogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        //productsListView
        productsListView.setPullLoadEnable(true);
        productsListView.setXListViewListener(this);
        productsListView.setAdapter(productsAdapter);


        catalogRepository.Get(null,new AsyncCallBack() {
            @Override
            public void onDataReceive(Object data, Object statusCode) {
                Log.v("productdata", data.toString());
                ProductsFragment.this.catalogAdapter.addAll((ArrayList<CatalogEntity>) data);
                ProductsFragment.this.catalogAdapter.notifyDataSetChanged();

            }
        });






    }


    private void onLoad() {
        productsListView.stopRefresh();
        productsListView.stopLoadMore();

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
