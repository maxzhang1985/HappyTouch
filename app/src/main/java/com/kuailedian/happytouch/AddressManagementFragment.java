package com.kuailedian.happytouch;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.common.ui.FloatActionButton;

import butterknife.ButterKnife;


public class AddressManagementFragment extends Fragment {
    private Context context;
    static FloatActionButton fabButton;
    // TODO: Rename and change types and number of parameters
    public static AddressManagementFragment newInstance() {
        AddressManagementFragment fragment = new AddressManagementFragment();
        return fragment;
    }

    public AddressManagementFragment() {
        // Required empty public constructor
    }

    private HTApplication getAppliction()
    {
        Activity act = (Activity)context;
        HTApplication app =(HTApplication)act.getApplication();
        return app;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address_management, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        context = view.getContext();

        if(fabButton == null) {
            fabButton = new FloatActionButton.Builder((Activity) context)
                    .withDrawable(getResources().getDrawable(R.mipmap.fab1))
                    .withButtonColor(Color.TRANSPARENT)
                    .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                    .withMargins(0, 0, 16, 16)
                    .create();
        }

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        fabButton.showFloatingActionButton();


    }


    @Override
    public void onDestroy() {
        fabButton.hideFloatingActionButton();
        super.onDestroy();

    }
}
