package com.kuailedian.happytouch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kuailedian.components.MyEditText;
import com.kuailedian.domain.Account;
import com.kuailedian.repository.HostsPath;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync;

import org.apache.http.Header;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 磊 on 2015/5/25.
 */
public class UserRegisterFragment extends Fragment {

    // TODO: Rename and change types and number of parameters
    public static UserRegisterFragment newInstance() {
        UserRegisterFragment fragment = new UserRegisterFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    public UserRegisterFragment() {
        // Required empty public constructor
    }

    @InjectView(R.id.btn_showpassword)
    Button btnShowPassword;

    @InjectView(R.id.edit_reg_password)
    MyEditText editPasswrod;

    @InjectView(R.id.edit_reg_mobilephone)
    MyEditText editPhone;

    @InjectView(R.id.edit_reg_msgcode)
    MyEditText editMsgCode;

    @InjectView(R.id.btn_sendcodesms)
    Button btnSendSms;


    @InjectView(R.id.btn_displaysmstiem)
    Button sendSmsCountTimer;

    @InjectView(R.id.btn_register_user)
    Button btnRegisterUser;

    CountDownTimer timer;
    Context context;

    ProgressDialog pd;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnShowPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                   editPasswrod.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    editPasswrod.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    editPasswrod.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                return false;
            }
        });


        timer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long min = (millisUntilFinished / 1000);
                sendSmsCountTimer.setText(String.valueOf(min)+ "秒后获取");
            }

            @Override
            public void onFinish() {
                btnSendSms.setEnabled(true);
                btnSendSms.setText("获取");
                sendSmsCountTimer.setText("60秒后获取");
            }
        };



        btnSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = editPhone.getText().toString();
                if(!phone.equals("")) {
                    sendMessage(phone);
                    btnSendSms.setEnabled(false);
                    btnSendSms.setText("已发送");
                    timer.start();
                }
                else
                {
                    Toast.makeText(context, "手机号不能为空！",  Toast.LENGTH_LONG).show();
                }
            }
        });

        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = editPhone.getText().toString();
                String passwrod = editPasswrod.getText().toString();
                String messagecode = editMsgCode.getText().toString();

                if(!phone.equals("") && !passwrod.equals("") && !messagecode.equals(""))
                {
                    registerUser(phone,passwrod,messagecode);
                }
                else
                {
                    Toast.makeText(context, "必填字段不能为空！",  Toast.LENGTH_LONG).show();
                }


            }
        });




    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.user_register, container, false);
        context = view.getContext();
        ButterKnife.inject(this, view);
        return view;
    }






    private void sendMessage(String mobilephone)
    {
        //PostMessageCode
        String hosturi = HostsPath.HostUri + "OrderAppInterFace.ashx?method=PostMessageCode";
        RequestParams params = new RequestParams();
        params.add("phone", mobilephone );
        pd = ProgressDialog.show(context, "提示", "正在注册，请稍后……");
        HttpUtilsAsync.get(hosturi, params, new TextHttpResponseHandler("GB2312") {
            @Override
            public void onSuccess(int i, Header[] headers, String responseString) {
                Log.v("getdatalog", responseString);
                JSONObject stateObject = JSON.parseObject(responseString);
                //String code = stateObject.getString("statecode");
                Toast.makeText(context, stateObject.getString("msg"),
                        Toast.LENGTH_LONG).show();
                pd.dismiss();
            }

            @Override
            public void onFailure(int i, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(context, "网络错误！",
                        Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        });


    }

    private void registerUser(final String phone, final String password,String messagecode)
    {

        String hosturi = HostsPath.HostUri + "OrderAppInterFace.ashx?method=PostMessageCode";
        RequestParams params = new RequestParams();
        params.add("phone", phone );
        params.add("password", password );
        params.add("messagecode", messagecode );
        pd = ProgressDialog.show(context, "提示", "正在注册，请稍后……");
        HttpUtilsAsync.get(hosturi, params, new TextHttpResponseHandler("GB2312") {
            @Override
            public void onSuccess(int i, Header[] headers, String responseString) {
                Log.v("getdatalog", responseString);
                JSONObject stateObject = JSON.parseObject(responseString);
                String code = stateObject.getString("statecode");
                Log.v("show state code", code);
                //register suress
                if (code.equals("0000")) {

                    Account account = new Account(phone,password);
                    getAppliction().SetSystemDomain(Account.class, account);
                    SharedPreferences sp = context.getApplicationContext().getSharedPreferences("com.kuailedian.happytouch", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("phone",account.getMobilePhone());
                    editor.putString("password",account.getPasswrod());
                    editor.commit();
                }

                Toast.makeText(context, stateObject.getString("msg"),
                        Toast.LENGTH_LONG).show();

                pd.dismiss();
            }

            @Override
            public void onFailure(int i, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(context, "网络错误！",
                        Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        });


    }

    private HTApplication getAppliction()
    {
        Activity act = (Activity)context;
        HTApplication app =(HTApplication)act.getApplication();
        return app;
    }


}
