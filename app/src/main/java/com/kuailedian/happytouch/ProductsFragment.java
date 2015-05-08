package com.kuailedian.happytouch;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    private ArrayList<String> catalogSource = new ArrayList<String>();

    private ArrayList<String> productsSource = new ArrayList<String>();

    private ArrayAdapter<String> catalogAdapter;

    private ArrayAdapter<String> productsAdapter;

    private Handler mHandler;

    public ProductsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProductsFragment newInstance() {
        ProductsFragment fragment = new ProductsFragment();

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
        ArrayList<String> array = new ArrayList<String>();
        array.add("the item 1");
        array.add("the item 2");
        array.add("the item 3");
        array.add("the item 4");
        array.add("the item 5");

        catalogAdapter = new ArrayAdapter<String>(view.getContext(),R.layout.popupwindow_item,R.id.popup_item, array);
        catalogListView.setAdapter(catalogAdapter);

        //productsListView
        productsListView.setPullLoadEnable(true);
        productsListView.setXListViewListener(this);
        productsListView.setAdapter(productsAdapter);

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
