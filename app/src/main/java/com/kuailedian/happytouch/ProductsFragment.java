package com.kuailedian.happytouch;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.kuailedian.adapter.ProductAdapter;
import com.kuailedian.applictionservice.IOrderCartOperator;
import com.kuailedian.components.DetailsPopupWindow;
import com.kuailedian.entity.CatalogEntity;
import com.kuailedian.entity.ProductEntity;
import com.kuailedian.repository.AsyncCallBack;
import com.kuailedian.repository.IAsyncRepository;
import com.kuailedian.repository.ProductsCatalogRepository;
import com.kuailedian.repository.ProductsRepository;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.maxwin.view.XListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends OrderFragmentBase  implements XListView.IXListViewListener , IOrderCartOperator {


    @InjectView(R.id.catalogListView)
    ListView catalogListView;

    @InjectView(R.id.productsListView)
    XListView productsListView;

    IAsyncRepository catalogRepository = new ProductsCatalogRepository();

    IAsyncRepository productRepository = new ProductsRepository();

    ProgressDialog pd =null;

    private ArrayAdapter<CatalogEntity> catalogAdapter;

    private ProductAdapter productsAdapter;

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
        pd = ProgressDialog.show(context, "提示", "加载中，请稍后……");
        //catalogListView

        catalogAdapter = new ArrayAdapter<CatalogEntity>(view.getContext(),R.layout.catalog_item,R.id.catalog_item_name, new ArrayList<CatalogEntity>());
        catalogListView.setAdapter(catalogAdapter);
        catalogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                productsAdapter.clear();
                CatalogEntity catalog = catalogAdapter.getItem(i);

                getProductByCatelogid(catalog.categoryid);

            }
        });
        //productsListView
        productsListView.setPullLoadEnable(true);

        productsAdapter = new ProductAdapter(context,this,null);

        productsListView.setXListViewListener(this);
        productsListView.setAdapter(productsAdapter);
        ViewStub mViewStub = (ViewStub)view.findViewById(R.id.productempty);
        productsListView.setEmptyView(mViewStub);


        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ProductEntity entity = productsAdapter.getItem(position-1);
                if(!entity.getProductid().equals("")) {
                    DetailsPopupWindow window = new DetailsPopupWindow(context, "product", entity.getProductid());
                    window.showAtLocation(rootView, Gravity.CENTER, 0, 0);
                }
            }
        });


        catalogRepository.Get(new RequestParams(),new AsyncCallBack() {
            @Override
            public void onDataReceive(Object data, Object statusCode) {
                pd.dismiss();
                if(data!=null) {

                    if (!data.equals(null) && !((ArrayList<CatalogEntity>) data).isEmpty()) {
                        catalogAdapter.addAll((ArrayList<CatalogEntity>) data);
                        catalogAdapter.notifyDataSetChanged();
                        productsAdapter.clear();
                        CatalogEntity catalog = catalogAdapter.getItem(0);
                        getProductByCatelogid(catalog.categoryid);

                    }
                }
                else {
                    Toast.makeText(context, "网格错误！", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private void getProductByCatelogid(String categoryid)
    {
        RequestParams params = new RequestParams();
        params.add("categoryid", categoryid);

        Log.v("print get product",categoryid);
        pd = ProgressDialog.show(context, "提示", "加载中，请稍后……");
        productRepository.Get(params, new AsyncCallBack(){
            @Override
            public void onDataReceive(Object data, Object statusCode) {
                pd.dismiss();
                if(data == null) {
                    Toast.makeText(context,"获取数据失败！",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                productsAdapter.addAll((ArrayList<ProductEntity>)data);
                productsAdapter.notifyDataSetChanged();

            }
        });

    }



    private void onLoad() {
        productsListView.stopRefresh();
        productsListView.stopLoadMore();

    }

    @Override
    public void onRefresh() {

        productsListView.stopRefresh();
    }

    @Override
    public void onLoadMore() {

        productsListView.stopLoadMore();
    }


}
