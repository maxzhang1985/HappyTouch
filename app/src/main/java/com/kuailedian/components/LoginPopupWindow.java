package com.kuailedian.components;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kuailedian.applictionservice.INavigationService;
import com.kuailedian.happytouch.HTApplication;
import com.kuailedian.happytouch.R;
import com.kuailedian.happytouch.UserRegisterFragment;

/**
 * Created by maxzhang on 5/28/2015.
 */
public class LoginPopupWindow extends PopupWindow {

    private Context owner;
    private View _view;
    public LoginPopupWindow(final Context context)
    {
        super(context);
        this.owner = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.user_login, null);


        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT );
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);

        this.setBackgroundDrawable(new ColorDrawable(
                android.graphics.Color.TRANSPARENT));

        TextView go_reg =  (TextView)view.findViewById(R.id.goto_register);

        go_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginPopupWindow.this.dismiss();
                Activity act = (Activity)context;
                HTApplication app =(HTApplication)act.getApplication();
                INavigationService service =  app.GetSystemDomain(INavigationService.class);
                service.Navigate(UserRegisterFragment.newInstance());
            }
        });


    }


}
