package com.kuailedian.happytouch;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

import com.kuailedian.applictionservice.INavigationService;
import com.kuailedian.components.LoginPopupWindow;
import com.kuailedian.domain.Account;
import com.marshalchen.common.uimodule.kenburnsview.KenBurnsView;
import com.marshalchen.common.uimodule.kenburnsview.Transition;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class HomeFragment extends Fragment {


    @InjectView(R.id.kenBurnsImageView)
    KenBurnsView kenBurnsView;

    @InjectView(R.id.kenBurnsImageView1)
    KenBurnsView kenBurnsView1;

    @InjectView(R.id.kenBurnsViewSwitch)
    ViewSwitcher viewSwitcher;

    private Context context;
    private View rootView;
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    private HTApplication getAppliction()
    {
        Activity act = (Activity)context;
        HTApplication app =(HTApplication)act.getApplication();
        return app;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        kenBurnsView.resume();
        kenBurnsView1.resume();

        kenBurnsView1.setTransitionListener(listener);
        kenBurnsView.setTransitionListener(listener);

        context = view.getContext();
        rootView = view;

        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("com.kuailedian.happytouch", Context.MODE_PRIVATE);
        String phone = sp.getString("phone", "none");

        String password = sp.getString("password","none");
        Log.v("login",phone + password);
        if(!phone.equals( "none"))
        {
            Account account = new Account(phone,password);
            getAppliction().SetSystemDomain(Account.class,account);

        }
    }


    KenBurnsView.TransitionListener listener = new KenBurnsView.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {

        }
        @Override
        public void onTransitionEnd(Transition transition) {

            showSwitcher();

        }
    };


    boolean isFirst =true;
    void showSwitcher()
    {
        if(isFirst) {

            viewSwitcher.showNext();
            isFirst =false;
        }
        else
        {

            viewSwitcher.showPrevious();
            isFirst = true;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.inject(this,view);

        return view;
    }


    @OnClick(R.id.reservation)
    public void onGo_Reservation()
    {
        HTApplication app = (HTApplication)getActivity().getApplication();
        Account account = app.GetSystemDomain(Account.class);
        if(account !=null) {
            INavigationService navigation = app.GetSystemDomain(INavigationService.class);
            navigation.Navigate(ReservationFragment.newInstance());
        }
        else
        {
            LoginPopupWindow popup = new LoginPopupWindow(context);
            popup.showAtLocation(rootView , Gravity.CENTER , 0 , 0);
        }
    }


    @OnClick(R.id.shopping)
    public void onGo_Shopping()
    {
        HTApplication app = (HTApplication)getActivity().getApplication();
        Account account = app.GetSystemDomain(Account.class);
        if(account !=null) {
            INavigationService navigation = app.GetSystemDomain(INavigationService.class);
            navigation.Navigate(ProductsFragment.newInstance());
        }
        else
        {
            LoginPopupWindow popup = new LoginPopupWindow(context);
            popup.showAtLocation(rootView , Gravity.CENTER , 0 , 0);
        }
    }


}
