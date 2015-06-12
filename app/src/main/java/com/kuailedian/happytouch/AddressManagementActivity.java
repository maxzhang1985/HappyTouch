package com.kuailedian.happytouch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kuailedian.adapter.AddressAdapter;
import com.kuailedian.entity.AddressEntity;
import com.kuailedian.repository.AddressManagentRepository;
import com.kuailedian.repository.AsyncCallBack;
import com.marshalchen.common.ui.FloatActionButton;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AddressManagementActivity extends ActionBarActivity {

    private FloatActionButton fabButton;
    private AddressManagentRepository repository = new AddressManagentRepository();
    private AddressAdapter addressAdapter;

    @InjectView(R.id.toolbar) Toolbar mToolbar;

    @InjectView(R.id.address_listview) ListView addressListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_management);
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
        mToolBarTextView.setText("地址管理");


        addressAdapter = new AddressAdapter(this,new ArrayList<AddressEntity>());
        addressListView.setAdapter(addressAdapter);

        fabButton = new FloatActionButton.Builder(this)
                .withDrawable(getResources().getDrawable(R.mipmap.fab1))
                .withButtonColor(Color.TRANSPARENT)
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0, 0, 16, 16)
                .create();


        repository.Get(null,new AsyncCallBack(){
            @Override
            public void onDataReceive(Object data, Object statusCode) {
                addressAdapter.addAll((ArrayList<AddressEntity>)data);
                addressAdapter.notifyDataSetChanged();
            }
        });


        Intent intent = getIntent();
        boolean callback = intent.getBooleanExtra("callback",false);
        Log.v("callback",String.valueOf(callback));
        if(callback) {
            addressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AddressEntity address = addressAdapter.getItem(position);
                    Intent intent = new Intent();
                    intent.putExtra("address",address);

                    setResult(RESULT_OK,intent);
                    AddressManagementActivity.this.finish();
                }
            });
        }


        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //fabButton.showFloatingActionButton();
    }

    private HTApplication getAppliction()
    {
        HTApplication app =(HTApplication)this.getApplication();
        return app;
    }
}
