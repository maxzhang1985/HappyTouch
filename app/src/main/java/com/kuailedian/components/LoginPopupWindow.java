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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by maxzhang on 5/28/2015.
 */
public class LoginPopupWindow extends PopupWindow {

    private Context owner;
    private View _view;

    @InjectView(R.id.edit_login_username)
    MyEditText edit_username;
    @InjectView(R.id.edit_login_password)
    MyEditText edit_password;



    @InjectView(R.id.btn_login)
    Button btnLogin;


    public LoginPopupWindow(final Context context)
    {
        super(context);
        this.owner = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.user_login, null);


        this.setContentView(view);




        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT );
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimBottom);

        this.setBackgroundDrawable(new ColorDrawable(
                android.graphics.Color.TRANSPARENT));

//        TextView go_reg =  (TextView)view.findViewById(R.id.goto_register);
//
//        go_reg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });



        ButterKnife.inject(owner.getApplicationContext(), view);

        Button btnShowPassword = (Button)view.findViewById(R.id.btn_showpassword);
        btnShowPassword.setOnTouchListener(new View.OnTouchListener() {
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
        });
    }


    private HTApplication getAppliction()
    {
        Activity act = (Activity)owner;
        HTApplication app =(HTApplication)act.getApplication();
        return app;
    }


    @OnClick(R.id.goto_register)
    public void Goto_registerView()
    {
        LoginPopupWindow.this.dismiss();
        INavigationService service =  this.getAppliction().GetSystemDomain(INavigationService.class);
        service.Navigate(UserRegisterFragment.newInstance());
    }


    @OnClick(R.id.btn_login)
    public void Login()
    {
        String hosturi = "http://60.2.176.70:21121/test/jiekou/OrderAppInterFace.ashx?method=Login";

        RequestParams params = new RequestParams();


        params.add("phone",edit_username.getText().toString() );
        params.add("password",edit_password.getText().toString());

        HttpUtilsAsync.get(hosturi, params, new TextHttpResponseHandler("UTF-8") {

            @Override
            public void onSuccess(int i, Header[] headers, String responseString) {
                Log.v("getdatalog", responseString);
                JSONObject stateObject = JSON.parseObject(responseString);

                String code = stateObject.getString("statecode");
                if(code == "0000")
                {
                    Account account = new Account(edit_username.getText().toString());
                    getAppliction().SetSystemDomain(Account.class,account);
                    SharedPreferences sp = owner.getSharedPreferences("SP", Context.MODE_PRIVATE);
                    sp.edit().putString("phone",account.getMobilePhone());
                    sp.edit().putString("password",account.getPasswrod());
                    sp.edit().commit();


                    LoginPopupWindow.this.dismiss();
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


}
