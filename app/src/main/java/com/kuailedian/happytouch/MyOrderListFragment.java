package com.kuailedian.happytouch;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kuailedian.domain.Account;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by maxzhang on 6/24/2015.
 */
public class MyOrderListFragment extends Fragment {

    private Context context;

    @InjectView(R.id.my_order_list) ListView myOrderListview;

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
    }

    private Account getAccount()
    {
        HTApplication app = (HTApplication)getActivity().getApplication();
        Account account = app.GetSystemDomain(Account.class);
        return account;
    }

}
