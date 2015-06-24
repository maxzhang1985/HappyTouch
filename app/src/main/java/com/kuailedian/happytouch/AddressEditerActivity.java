package com.kuailedian.happytouch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kuailedian.domain.Account;
import com.kuailedian.entity.AddressEntity;
import com.kuailedian.repository.HostsPath;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync;

import org.apache.http.Header;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by maxzhang on 6/23/2015.
 */
public class AddressEditerActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar)  Toolbar mToolbar;

    @InjectView(R.id.address_default_switch)
    CheckBox sch_default;

    @InjectView(R.id.address_name)  EditText edit_addressName;

    @InjectView(R.id.address_mobile)  EditText edit_mobile;

    @InjectView(R.id.address_address)  EditText edit_address;

    @InjectView(R.id.btn_address_save) Button btn_Save;

    ProgressDialog pd;

    private AddressEntity address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_editer);
        ButterKnife.inject(this);

        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolBarTextView.setText("收货地址维护");


        Intent intent = getIntent();
        final boolean isAddtion = intent.getBooleanExtra("addtion",false);

        address =  (AddressEntity)intent.getSerializableExtra("address");

        if(!isAddtion)
        {
            edit_addressName.setText(address.getName());
            edit_mobile.setText(address.getMobile());
            edit_address.setText(address.getAddress());
            sch_default.setChecked(address.getIsdefault());

        }


        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isAddtion)
                {
                    addAddress();
                }
                else
                {
                    updateAddress();
                }

            }
        });




    }

    private void addAddress()
    {
        String url = HostsPath.HostUri + "OrderAppInterFace.ashx?method=UserAddressAdd";
        HTApplication app = getAppliction();
        Account account = app.GetSystemDomain(Account.class);

        if(account!=null) {

            RequestParams params = new RequestParams();
            AddressEntity addressEntity = getAddressEntity(account.getMobilePhone());
            //params.add("userCode",account.getMobilePhone());

            String jsonAddress = JSON.toJSONString(addressEntity);
            params.add("addressdetails", jsonAddress );

            Log.v("params",jsonAddress);
            pd = ProgressDialog.show(this, "提示", "加载中，请稍后……");
            HttpUtilsAsync.post(url, params, new TextHttpResponseHandler("GB2312") {
                @Override
                public void onSuccess(int i, Header[] headers, String responseString) {

                    JSONObject stateObject = JSON.parseObject(responseString);
                    //String code = stateObject.getString("statecode");
                    Toast.makeText(AddressEditerActivity.this, stateObject.getString("msg"), Toast.LENGTH_LONG).show();
                    pd.dismiss();
                    ending();
                }

                @Override
                public void onFailure(int i, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(AddressEditerActivity.this, "网络错误！",
                            Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
            });


        }
        else
        {
            Toast.makeText(AddressEditerActivity.this, "请重新登录系统！",
                    Toast.LENGTH_LONG).show();

        }

    }


    private void updateAddress()
    {
        HTApplication app = getAppliction();
        Account account = app.GetSystemDomain(Account.class);

        String url = HostsPath.HostUri + "OrderAppInterFace.ashx?method=UserAddressUpdate";

        if(account!=null) {
            pd = ProgressDialog.show(this, "提示", "加载中，请稍后……");
            RequestParams params = getResquestParams();
            params.add("id",address.getId());
            Log.v("params",params.toString());
            HttpUtilsAsync.post(url, params, new TextHttpResponseHandler("GB2312") {
                @Override
                public void onSuccess(int i, Header[] headers, String responseString) {

                    JSONObject stateObject = JSON.parseObject(responseString);
                    //String code = stateObject.getString("statecode");
                    Toast.makeText(AddressEditerActivity.this, stateObject.getString("msg"), Toast.LENGTH_LONG).show();
                    pd.dismiss();
                    ending();
                }

                @Override
                public void onFailure(int i, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(AddressEditerActivity.this, "网络错误！",
                            Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
            });


        }
        else
        {
            Toast.makeText(AddressEditerActivity.this, "请重新登录系统！",
                    Toast.LENGTH_LONG).show();
        }

    }



    private RequestParams getResquestParams()
    {

        RequestParams params = new RequestParams();

        params.add("name", edit_addressName.getText().toString());

        params.add("tel" ,edit_mobile.getText().toString());

        params.add("address" ,edit_address.getText().toString());

        String flag = "0";
        if(sch_default.isChecked())
            flag = "1";

        params.add("flag", flag );



        return params;
    }

    private AddressEntity getAddressEntity(String userCode)
    {
        AddressEntity entity = new AddressEntity();
        entity.setUsercode(userCode);
        entity.setId("");
        entity.setName(edit_addressName.getText().toString());
        entity.setMobile(edit_mobile.getText().toString());
        entity.setAddress(edit_address.getText().toString());
        entity.setIsdefault(sch_default.isChecked());
        return entity;
    }



    private HTApplication getAppliction()
    {
        HTApplication app =(HTApplication)this.getApplication();
        return app;
    }


    @Override
    public void onBackPressed() {
        ending();
        super.onBackPressed();
    }

    private void ending()
    {
        Intent intent = new Intent();
        intent.putExtra("result",true);
        setResult(RESULT_OK,intent);
        this.finish();
    }


}
