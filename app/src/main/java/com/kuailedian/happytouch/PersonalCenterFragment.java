package com.kuailedian.happytouch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kuailedian.applictionservice.INavigationService;
import com.kuailedian.domain.Account;
import com.kuailedian.entity.AddressEntity;
import com.kuailedian.reveallayout.RevealLayout;

import butterknife.ButterKnife;

/**
 * Created by 磊 on 2015/5/24.
 */
public class PersonalCenterFragment extends Fragment{

    private RevealLayout mRevealLayout;
    private Context context;

    public PersonalCenterFragment() {
        // Required empty public constructor
    }

    private HTApplication getAppliction()
    {
        Activity act = (Activity)context;
        HTApplication app =(HTApplication)act.getApplication();
        return app;
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

        context = rootView.getContext();

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

        HTApplication app = (HTApplication)getActivity().getApplication();
        Account account = app.GetSystemDomain(Account.class);
        if(account!= null) {
            TextView personName = (TextView) view.findViewById(R.id.tv_person_name);
            personName.setText(account.getMobilePhone());
        }
        LinearLayout l1 = (LinearLayout)view.findViewById(R.id.person_center_myaddress);
        LinearLayout l2 = (LinearLayout)view.findViewById(R.id.person_center_myorder);

        LinearLayout l4 = (LinearLayout)view.findViewById(R.id.person_center_aboat);

        l1.setOnClickListener(onClick);
        l2.setOnClickListener(onClick);

        l4.setOnClickListener(onClick);


    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            INavigationService navigation = getAppliction().GetSystemDomain(INavigationService.class);

            switch (v.getId())
            {
                case R.id.person_center_myorder:
                    navigation.Push(MyOrderListFragment.newInstance());
                    break;
                case R.id.person_center_myaddress:
                    Intent intent = new Intent(context,AddressManagementActivity.class);
                    intent.putExtra("callback",false);
                    startActivity(intent);
                    break;

                case R.id.person_center_aboat:
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        AddressEntity entity = (AddressEntity)data.getSerializableExtra("address");
        Log.v("address",entity.toString());

    }


}
