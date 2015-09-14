package com.kuailedian.happytouch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kuailedian.domain.OrderCart;
import com.kuailedian.repository.HostsPath;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync;

import org.apache.http.Header;

public class SplashscreenActivity extends ActionBarActivity {

    private final int SPLASH_DISPLAY_LENGHT = 1800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        String HostUri = HostsPath.HostUri  + "OrderAppInterFace.ashx?method=EnterInfo" ;
        HttpUtilsAsync.get(HostUri, new RequestParams() , new TextHttpResponseHandler("GB2312") {

            @Override
            public void onSuccess(int i, Header[] headers, String responseString) {

                JSONObject object = JSON.parseObject(responseString);
                OrderCart cart = OrderCart.getOrderCart();
                cart.setMinMoney(object.getInteger("Enter_min"));

                String strStartTime =(object.getString("enter_sdate"));
                String strEndTime =(object.getString("enter_edate"));
                String strSCStartTime =(object.getString("enter_sc_sdate"));
                String strSCEndTime =(object.getString("enter_sc_edate"));
                String enter_tel = (object.getString("enter_tel"));

                cart.setMobilePhone(enter_tel);
                cart.setDcstime(strStartTime);
                cart.setDcetime(strEndTime);
                cart.setScstime(strSCStartTime);
                cart.setScetime(strSCEndTime);
            }

            @Override
            public void onFailure(int i, Header[] headers, String responseString, Throwable throwable) {

            }


        });


        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashscreenActivity.this,MyBaseActivity.class);
                SplashscreenActivity.this.startActivity(mainIntent);
                overridePendingTransition(R.anim.ac_transition_unzoom_in, R.anim.abc_fade_out);
                SplashscreenActivity.this.finish();
            }

        }, SPLASH_DISPLAY_LENGHT);


    }


}
