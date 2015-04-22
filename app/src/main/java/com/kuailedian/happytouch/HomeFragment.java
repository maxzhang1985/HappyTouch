package com.kuailedian.happytouch;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;
import com.marshalchen.common.uimodule.kenburnsview.KenBurnsView;
import com.marshalchen.common.uimodule.kenburnsview.Transition;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class HomeFragment extends Fragment {


    @InjectView(R.id.kenBurnsImageView)
    KenBurnsView kenBurnsView;

    @InjectView(R.id.kenBurnsImageView1)
    KenBurnsView kenBurnsView1;

    @InjectView(R.id.kenBurnsViewSwitch)
    ViewSwitcher viewSwitcher;

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




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        kenBurnsView.resume();
        kenBurnsView1.resume();

        kenBurnsView1.setTransitionListener(listener);
        kenBurnsView.setTransitionListener(listener);
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




}