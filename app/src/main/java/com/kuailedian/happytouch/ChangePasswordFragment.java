package com.kuailedian.happytouch;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kuailedian.components.MyEditText;
import com.kuailedian.repository.HostsPath;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync;

import org.apache.http.Header;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChangePasswordFragment extends Fragment {
    Context context;

    CountDownTimer timer;

    ProgressDialog pd;


    @InjectView(R.id.edit_reg_mobilephone)
    MyEditText editPhone;

    @InjectView(R.id.edit_reg_msgcode)
    MyEditText editMsgCode;

    @InjectView(R.id.btn_sendcodesms)
    Button btnSendSms;


    @InjectView(R.id.btn_displaysmstiem)
    Button sendSmsCountTimer;

    @InjectView(R.id.btn_chengepasswrod_submit)
    Button btn_chengepasswrod_submit;


    @InjectView(R.id.edit_new1_password)
    MyEditText editPasswrod1;

    @InjectView(R.id.edit_new2_password)
    MyEditText editPasswrod2;


    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ChangePasswordFragment newInstance() {

        ChangePasswordFragment   fragment = new ChangePasswordFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.inject(this, rootView);
        context = rootView.getContext();
        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                    sendMessagecode(phone);
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

        btn_chengepasswrod_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = editPhone.getText().toString();
                String passwrod1 = editPasswrod1.getText().toString();
                String passwrod2 = editPasswrod2.getText().toString();
                String messagecode = editMsgCode.getText().toString();

                if (!phone.equals("") && !passwrod1.equals("")  &&  !passwrod2.equals("")  && !messagecode.equals("")) {
                    if(passwrod1.equals(passwrod2))
                        changePassword(phone, passwrod1,passwrod2, messagecode);
                    else
                        Toast.makeText(context, "两次密码输入不一致！", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "必填字段不能为空！", Toast.LENGTH_LONG).show();
                }


            }
        });

    }


    private void sendMessagecode(String mobilephone)
    {
        //PostMessageCode
        String hosturi = HostsPath.HostUri + "OrderAppInterFace.ashx?method=PostMessageCodeEpw";
        RequestParams params = new RequestParams();
        params.add("phone", mobilephone );
        pd = ProgressDialog.show(context, "提示", "正在提交，请稍后……");
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


    private void changePassword(String phone, String password1,String password2,String messagecode)
    {

        String hosturi = HostsPath.HostUri + "OrderAppInterFace.ashx?method=PwdEdit";
        RequestParams params = new RequestParams();
        params.add("phone", phone );
        params.add("newpwd", password1 );
        params.add("newpwdq", password2 );
        params.add("messagecode", messagecode );
        pd = ProgressDialog.show(context, "提示", "正在提交，请稍后……");
        HttpUtilsAsync.get(hosturi, params, new TextHttpResponseHandler("GB2312") {
            @Override
            public void onSuccess(int i, Header[] headers, String responseString) {
                Log.v("getdatalog", responseString);
                JSONObject stateObject = JSON.parseObject(responseString);
                String code = stateObject.getString("statecode");
                Log.v("show state code", code);
                //register suress
                if (code.equals("0000")) {
                    final AlertDialog dialog = new AlertDialog.Builder(context)
                            .setIcon(R.mipmap.icn_2)
                            .setTitle("提示")
                            .setMessage("密码修改成功,请重新登录.")
                            .show();
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            ChangePasswordFragment.this.getActivity().onBackPressed();
                        }
                    }, 2800L);

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




}
