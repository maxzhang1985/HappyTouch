package com.kuailedian.happytouch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kuailedian.adapter.SettleOrderAdapter;
import com.kuailedian.domain.OrderCart;
import com.kuailedian.entity.AddressEntity;
import com.kuailedian.repository.AddressManagentRepository;
import com.kuailedian.repository.AsyncCallBack;
import com.loopj.android.http.RequestParams;

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

    private AddressEntity selectedAddress;

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

        OrderCart orderCart = OrderCart.getOrderCart();

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

        AddressManagentRepository repository = new AddressManagentRepository();
        repository.Get(new RequestParams(),new AsyncCallBack(){
            @Override
            public void onDataReceive(Object data, Object statusCode) {
                if(data!=null) {
                    List<AddressEntity> addressEntityList = (List<AddressEntity>) data;
                    if(addressEntityList.size() > 0)
                    {
                        for(int i =0 ; i<=addressEntityList.size() -1 ; i++)
                        {
                            AddressEntity item = addressEntityList.get(i);
                            if(item.getIsdefault())
                                selectedAddress = item;
                        }
                        if(selectedAddress == null)
                            selectedAddress = addressEntityList.get(0);

                        settleAddressName.setText(selectedAddress.getName());
                        settleDetailAddress.setText(selectedAddress.getAddress());

                    }

                }
            }
        });




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        AddressEntity entity = (AddressEntity)data.getSerializableExtra("address");
        if(!entity.getId().equals("")  || !(entity.getId()==null) ) {
            Log.v("address", entity.toString());
            selectedAddress = entity;
            settleAddressName.setText(entity.getName());
            settleDetailAddress.setText(entity.getAddress());
        }
    }



    private HTApplication getAppliction()
    {
        HTApplication app =(HTApplication)this.getApplication();
        return app;
    }

}
