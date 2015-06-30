package com.kuailedian.happytouch;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kuailedian.adapter.MyOrderAdapter;
import com.kuailedian.domain.Account;
import com.kuailedian.entity.MyOrderItemEntity;
import com.kuailedian.repository.AsyncCallBack;
import com.kuailedian.repository.MyOrderRepository;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by maxzhang on 6/24/2015.
 */
public class MyOrderListFragment extends Fragment {

    private Context context;
    private MyOrderRepository orderRepository;
    private MyOrderAdapter adapter;
    ProgressDialog pd;

    @InjectView(R.id.my_order_list) ListView myOrderListview;


    // TODO: Rename and change types and number of parameters
    public static MyOrderListFragment newInstance() {

        MyOrderListFragment   fragment = new MyOrderListFragment();

        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_myorders, container, false);

        ButterKnife.inject(this, rootView);

        context = rootView.getContext();

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new MyOrderAdapter(context,new ArrayList<MyOrderItemEntity>());

        myOrderListview.setAdapter(adapter);

        orderRepository = new MyOrderRepository();
        RequestParams params = new RequestParams();
        Account account = getAccount();
        if(account!=null) {
            params.add("userid", getAccount().getMobilePhone());
            pd = ProgressDialog.show(context, "提示", "加载中，请稍后……");
            orderRepository.Get(params, new AsyncCallBack() {
                @Override
                public void onDataReceive(Object data, Object statusCode) {
                    if (data != null) {
                        adapter.clear();
                        adapter.addAll((ArrayList<MyOrderItemEntity>) data);
                        adapter.notifyDataSetChanged();
                    }
                    pd.dismiss();
                }
            });

        }
    }

    private Account getAccount()
    {
        HTApplication app = (HTApplication)getActivity().getApplication();
        Account account = app.GetSystemDomain(Account.class);
        return account;
    }

}
