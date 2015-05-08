package com.kuailedian.happytouch;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.kuailedian.components.BottomPopupWindow;

/**
 * Created by maxzhang on 5/8/2015.
 */
public class OrderFragmentBase extends Fragment {

    protected Context context;
    protected View rootView;

    protected void InitView()
    {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.v("mylog","mydemolog1");

        context = view.getContext();
        rootView = view;
        //��ʾ���ﳵ
        ImageButton btnShowOrder  = (ImageButton) view.findViewById(R.id.btn_showolder);
        btnShowOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowOrder();
            }
        });


        //�����ύ
        ImageButton btnOrderSubmit  = (ImageButton) view.findViewById(R.id.btn_ordersubmit);
        btnOrderSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    protected void ShowOrder()
    {
        BottomPopupWindow popup = new BottomPopupWindow(context,null);

        popup.Show(rootView);

    }


}
