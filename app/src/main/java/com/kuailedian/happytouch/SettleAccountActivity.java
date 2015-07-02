package com.kuailedian.happytouch;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kuailedian.adapter.SettleOrderAdapter;
import com.kuailedian.alipay.OrderInfo;
import com.kuailedian.alipay.PayApiHelper;
import com.kuailedian.domain.Account;
import com.kuailedian.domain.OrderCart;
import com.kuailedian.entity.AddressEntity;
import com.kuailedian.entity.MyOrderEntity;
import com.kuailedian.repository.AddressManagentRepository;
import com.kuailedian.repository.AsyncCallBack;
import com.kuailedian.repository.HostsPath;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync;

import org.apache.http.Header;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SettleAccountActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.settle_address_name)
    TextView settleAddressName;

    @InjectView(R.id.settle_address_address)
    TextView settleDetailAddress;

    @InjectView(R.id.settle_order_listview)
    ListView settleListView;

    @InjectView(R.id.settle_address_select)
    LinearLayout addressSelecter;

    @InjectView(R.id.settle_order_price)
    TextView settleTotalPrice;

    @InjectView(R.id.btn_ordersubmit)
    ImageButton orderSubmit;

    private AddressEntity selectedAddress;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle_account);
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
        mToolBarTextView.setText("结算");

       final OrderCart orderCart = OrderCart.getOrderCart();

        settleTotalPrice.setText(String.valueOf(orderCart.getToalMoney()));

        SettleOrderAdapter settleOrderAdapter = new SettleOrderAdapter(this,orderCart.toArray());
        settleListView.setAdapter(settleOrderAdapter);

        addressSelecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SettleAccountActivity.this,AddressManagementActivity.class);
                intent1.putExtra("callback",true);
                startActivityForResult(intent1, 1);
            }
        });

        HTApplication app = getAppliction();
        final Account account = app.GetSystemDomain(Account.class);

        orderSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedAddress!=null) {

                    MyOrderEntity orderEntity = new MyOrderEntity(account.getMobilePhone(), selectedAddress.getId());
                    orderEntity.setCart( orderCart.toArray() );

                    String orderjson  = JSON.toJSONString(orderEntity);

                    if(account!=null) {

                        String url = HostsPath.HostUri + "OrderAppInterFace.ashx?method=GenerateOrders";
                        RequestParams params = new RequestParams();
                        params.setContentEncoding("GB2312");
                        params.add("orderdetails", orderjson );
                        Log.v("params",orderjson);
                        pd = ProgressDialog.show(SettleAccountActivity.this, "提示", "正在生成订单，请稍后……");
                        HttpUtilsAsync.post(url, params, new TextHttpResponseHandler("GB2312") {
                            @Override
                            public void onSuccess(int i, Header[] headers, String responseString) {

                                final JSONObject stateObject = JSON.parseObject(responseString);
                                String code = stateObject.getString("statecode");
                                Toast.makeText(SettleAccountActivity.this, stateObject.getString("msg"), Toast.LENGTH_LONG).show();
                                pd.dismiss();

                                //订单生成成功，开始支付
                                if(code.equals("0000")) {
                                    OrderInfo info = new OrderInfo();
                                    info.ID = stateObject.getString("msg");
                                    info.Subject = "快乐购物体验 e点外卖商超 送货到家";
                                    info.Body =  "快乐购物体验 e点外卖商超 送货到家";
                                    info.Price = String.valueOf( orderCart.getToalMoney() );
                                    PayApiHelper payApiHelper = new PayApiHelper(SettleAccountActivity.this);
                                    payApiHelper.payAsync(info , new AsyncCallBack() {
                                        @Override
                                        public void onDataReceive(Object data, Object statusCode) {
                                            if(statusCode.toString().equals("9000")) {

                                               final AlertDialog dialog =  new AlertDialog.Builder(SettleAccountActivity.this)
                                                        .setIcon(R.mipmap.icn_2)
                                                        .setTitle("提示")
                                                        .setMessage("支付成功")
                                                        .show();
                                                Handler mHandler = new Handler();
                                                mHandler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        dialog.dismiss();
                                                        SettleAccountActivity.this.finish();
                                                    }
                                                }, 800L);
                                            }
                                        }
                                    });
                                }

                                //startActivity(new Intent(SettleAccountActivity.this, PayDemoActivity.class));
                                //这里写支付接口

                            }

                            @Override
                            public void onFailure(int i, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(SettleAccountActivity.this, "网络错误！",
                                        Toast.LENGTH_LONG).show();
                                pd.dismiss();
                            }
                        });


                    }
                    else
                    {
                        Toast.makeText(SettleAccountActivity.this, "请重新登录系统！",
                                Toast.LENGTH_LONG).show();

                    }




                }

            }
        });


        AddressManagentRepository repository = new AddressManagentRepository();
        RequestParams params = new RequestParams();
        params.add("usercode",account.getMobilePhone());
        repository.Get(params,new AsyncCallBack(){
            @Override
            public void onDataReceive(Object data, Object statusCode) {
                if(data!=null) {
                    List<AddressEntity> addressEntityList = (List<AddressEntity>) data;
                    if(addressEntityList.size() > 0)
                    {
                        for(int i =0 ; i<=addressEntityList.size() -1 ; i++)
                        {
                            AddressEntity item = addressEntityList.get(i);
                            if(item.Isdefault())
                                selectedAddress = item;
                        }
                        if(selectedAddress == null)
                            selectedAddress = addressEntityList.get(0);

                        Log.v("selectedaddress",selectedAddress.toString());
                        settleAddressName.setText(selectedAddress.getName());
                        settleDetailAddress.setText(selectedAddress.getAddress());

                    }

                }
            }
        });




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            AddressEntity entity = (AddressEntity) data.getSerializableExtra("address");
            if (!entity.getId().equals("") || !(entity.getId() == null)) {
                Log.v("address", entity.toString());
                selectedAddress = entity;
                settleAddressName.setText(entity.getName());
                settleDetailAddress.setText(entity.getAddress());
            }
        }catch (Exception e){

        }
    }



    private HTApplication getAppliction()
    {
        HTApplication app =(HTApplication)this.getApplication();
        return app;
    }

}
