package com.kuailedian.components;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kuailedian.applictionservice.INavigationService;
import com.kuailedian.domain.Account;
import com.kuailedian.happytouch.HTApplication;
import com.kuailedian.happytouch.R;
import com.kuailedian.happytouch.UserRegisterFragment;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync;

import org.apache.http.Header;

/**
 * Created by maxzhang on 5/28/2015.
 */
public class LoginPopupWindow extends PopupWindow {

    private Context owner;
    private View _view;


    MyEditText edit_username;

    MyEditText edit_password;

    Button btnLogin;


    public LoginPopupWindow(final Context context)
    {
        super(context);
        this.owner = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.user_login, null);


        this.setContentView(view);




        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimBottom);

        this.setBackgroundDrawable(new ColorDrawable(
                android.graphics.Color.TRANSPARENT));


        edit_username =  (MyEditText)view.findViewById(R.id.edit_login_username);

        edit_password =  (MyEditText)view.findViewById(R.id.edit_login_password);
        
        //go to register button
        TextView go_reg =  (TextView)view.findViewById(R.id.goto_register);
        go_reg.setOnClickListener(Goto_registerView);

        //login button
        Button btnLogin =(Button)view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(Goto_Login);

        //show password button
        Button btnShowPassword = (Button)view.findViewById(R.id.btn_showpassword);
        btnShowPassword.setOnTouchListener(On_showPassword);
    }


    private HTApplication getAppliction()
    {
        Activity act = (Activity)owner;
        HTApplication app =(HTApplication)act.getApplication();
        return app;
    }

    View.OnClickListener Goto_registerView =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginPopupWindow.this.dismiss();
            INavigationService service =  getAppliction().GetSystemDomain(INavigationService.class);
            service.Navigate(UserRegisterFragment.newInstance());
        }
    };

    View.OnTouchListener On_showPassword =  new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                edit_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            return false;
        }
    };



    View.OnClickListener Goto_Login =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String hosturi = "http://60.2.176.70:21121/test/jiekou/OrderAppInterFace.ashx?method=Login";
            RequestParams params = new RequestParams();
            params.add("phone",edit_username.getText().toString() );
            params.add("password",edit_password.getText().toString());
            HttpUtilsAsync.get(hosturi, params, new TextHttpResponseHandler("GB2312") {
                @Override
                public void onSuccess(int i, Header[] headers, String responseString) {
                    Log.v("getdatalog", responseString);
                    JSONObject stateObject = JSON.parseObject(responseString);
                    String code = stateObject.getString("statecode");
                    Log.v("show state code",code);
                    if(code.equals("0000"))
                    {
                        Account account = new Account(edit_username.getText().toString(),edit_password.getText().toString());
                        getAppliction().SetSystemDomain(Account.class, account);
                        SharedPreferences sp = owner.getSharedPreferences("com.kuailedian.happytouch", Context.MODE_PRIVATE);
                        sp.edit().putString("phone",account.getMobilePhone());
                        sp.edit().putString("password",account.getPasswrod());
                        sp.edit().commit();
                        Log.v("login success",sp.getString("phone","") + account.getPasswrod());
                        LoginPopupWindow.this.dismiss();

                        Toast.makeText(owner, "登录成功",  Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(owner, stateObject.getString("msg"),
                                Toast.LENGTH_LONG).show();
                    }



                    //Toast

                }

                @Override
                public void onFailure(int i, Header[] headers, String responseString, Throwable throwable) {
                    Log.v("getdatalog", "failure");
                    Toast.makeText(owner, "网络错误！",
                            Toast.LENGTH_LONG).show();
                }
            });


        }
    };






}
