package com.kuailedian.happytouch;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.kuailedian.domain.CartItem;
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

    @InjectView(R.id.op_payaway)
    TextView op_payaway;

    String paycode = "";

    private AddressEntity selectedAddress;

    ProgressDialog pd;

    boolean iscancel = false;

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
        mToolBarTextView.setText("订单确认");

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


        //select pay away
        op_payaway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] str={"支付宝","货到付款"};
                AlertDialog ad = new AlertDialog.Builder(SettleAccountActivity.this)
                        .setTitle("选择支付方式")
                        .setSingleChoiceItems(str, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which == 0)
                                    paycode = "001";
                                else if(which == 1)
                                    paycode = "002";

                                Log.v("paycode",paycode + ":" + String.valueOf( which));
                                op_payaway.setText(str[which]);
                                dialog.dismiss();
                            }
                        }).create();
                ad.show();

            }
        });



        //submit orders


        orderSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!paycode.equals("") && selectedAddress!=null  && orderCart.getTotalAmount() > 0) {

                    if(!orderCart.getIsBuyEnable() && orderCart.getToalMoney() < orderCart.getMinMoney())
                    {
                       // Toast.makeText(SettleAccountActivity.this,"需要加5元钱" , Toast.LENGTH_LONG).show();
                        AlertDialog dialog1 = new AlertDialog.Builder(SettleAccountActivity.this)
                                .setIcon(R.mipmap.icn_1)
                                .setTitle("提示")
                                .setMessage("订单中不包含订餐商品，不足"+ String.valueOf(orderCart.getMinMoney())  + "元,需要支付5元运费,确定订单吗?"   )
                                 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                     public void onClick(DialogInterface dialog, int which) {
                                         CartItem plusItem = new CartItem();
                                         plusItem.setName("运费");
                                         plusItem.setType("C");
                                         plusItem.setAmount(1);
                                         plusItem.setMoney(5.0f);
                                         plusItem.setIsSelected(true);
                                         orderCart.addCart(plusItem);

                                         payOrder();
                                     }
                                 })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        SettleAccountActivity.this.iscancel = true;
                                    }
                                })
                        .show();
                    }
                    else
                    {
                        payOrder();
                    }


                }
                else
                {
                    Toast.makeText(SettleAccountActivity.this, "请选择正确的信息！",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        HTApplication app = getAppliction();
        final Account account = app.GetSystemDomain(Account.class);
        if(account!=null) {

            AddressManagentRepository repository = new AddressManagentRepository();
            RequestParams params = new RequestParams();
            params.add("usercode", account.getMobilePhone());
            repository.Get(params, new AsyncCallBack() {
                @Override
                public void onDataReceive(Object data, Object statusCode) {
                    if (data != null) {
                        List<AddressEntity> addressEntityList = (List<AddressEntity>) data;
                        if (addressEntityList.size() > 0) {
                            for (int i = 0; i <= addressEntityList.size() - 1; i++) {
                                AddressEntity item = addressEntityList.get(i);
                                if (item.Isdefault())
                                    selectedAddress = item;
                            }
                            if (selectedAddress == null)
                                selectedAddress = addressEntityList.get(0);

                            Log.v("selectedaddress", selectedAddress.toString());
                            settleAddressName.setText(selectedAddress.getName());
                            settleDetailAddress.setText(selectedAddress.getAddress());

                        }

                    }
                }
            });


        }

    }


    private void payOrder()
    {
        HTApplication app = getAppliction();
        final Account account = app.GetSystemDomain(Account.class);
        final OrderCart orderCart = OrderCart.getOrderCart();

        if(account!=null) {
            MyOrderEntity orderEntity = new MyOrderEntity(account.getMobilePhone(), selectedAddress.getId());
            orderEntity.setPaycode(paycode);
            orderEntity.setCart( orderCart.toArray() );

            String orderjson  = JSON.toJSONString(orderEntity);

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
                    Toast.makeText(SettleAccountActivity.this, "订单:" + stateObject.getString("msg"), Toast.LENGTH_LONG).show();
                    pd.dismiss();

                    //订单生成成功，开始支付
                    if (code.equals("0000")) {

                        if (paycode == "002") {
                            final AlertDialog dialog1 = new AlertDialog.Builder(SettleAccountActivity.this)
                                    .setIcon(R.mipmap.icn_2)
                                    .setTitle("提示")
                                    .setMessage("订单成功,正在为你配送,请耐心等待.")
                                    .show();
                            orderCart.clear();
                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog1.dismiss();
                                    SettleAccountActivity.this.finish();
                                }
                            }, 2800L);
                        } else {
                            OrderInfo info = new OrderInfo();
                            info.ID = stateObject.getString("msg");
                            info.Subject = "快乐购物体验 e点外卖商超 送货到家";
                            info.Body = "快乐购物体验 e点外卖商超 送货到家";
                            info.Price = String.valueOf(orderCart.getToalMoney());
                            PayApiHelper payApiHelper = new PayApiHelper(SettleAccountActivity.this);
                            payApiHelper.payAsync(info, new AsyncCallBack() {
                                @Override
                                public void onDataReceive(Object data, Object statusCode) {
                                    if (statusCode.toString().equals("9000")) {

                                        final AlertDialog dialog = new AlertDialog.Builder(SettleAccountActivity.this)
                                                .setIcon(R.mipmap.icn_2)
                                                .setTitle("提示")
                                                .setMessage("支付成功,正在为你配送,请耐心等待.")
                                                .show();
                                        orderCart.clear();
                                        Handler mHandler = new Handler();
                                        mHandler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.dismiss();
                                                SettleAccountActivity.this.finish();
                                            }
                                        }, 2800L);
                                    }
                                }
                            });
                        }

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
