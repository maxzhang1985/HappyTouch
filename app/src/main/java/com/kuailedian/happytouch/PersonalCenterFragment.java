package com.kuailedian.happytouch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.kuailedian.reveallayout.RevealLayout;

import butterknife.ButterKnife;

/**
 * Created by 磊 on 2015/5/24.
 */
public class PersonalCenterFragment extends Fragment{

    private RevealLayout mRevealLayout;

    public PersonalCenterFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static PersonalCenterFragment newInstance() {

        PersonalCenterFragment   fragment = new PersonalCenterFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_personalcenter, container, false);

        ButterKnife.inject(this, rootView);

        mRevealLayout = (RevealLayout) rootView.findViewById(R.id.reveal_layout);




        mRevealLayout.setContentShown(false);
        mRevealLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRevealLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mRevealLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRevealLayout.show();
                    }
                }, 50);
            }
        });


        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }
}
