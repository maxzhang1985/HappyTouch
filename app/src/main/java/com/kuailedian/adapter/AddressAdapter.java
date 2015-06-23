package com.kuailedian.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kuailedian.domain.Account;
import com.kuailedian.entity.AddressEntity;
import com.kuailedian.happytouch.AddressEditerActivity;
import com.kuailedian.happytouch.HTApplication;
import com.kuailedian.happytouch.R;
import com.kuailedian.repository.HostsPath;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync;

import org.apache.http.Header;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by maxzhang on 6/11/2015.
 */
public class AddressAdapter extends ArrayAdapter<AddressEntity> {

    List<AddressEntity> addressList;
    private LayoutInflater inflater = null;
    private Context context;
    ProgressDialog pd;

    public AddressAdapter(Context c, List<AddressEntity> adlist) {
        super(c, R.layout.address_item);
        context = c;
        addressList = adlist;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return addressList.size();
    }

    @Override
    public void clear() {
        this.addressList.clear();
    }

    @Override
    public AddressEntity getItem(int position) {

        return addressList.get(position);


    }

    @Override
    public void addAll(Collection<? extends AddressEntity> collection) {
        addressList.addAll(collection);
    }

    @Override
    public boolean isEmpty() {
        return this.addressList == null || this.addressList.isEmpty();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AddressViewHolder viewHolder = null;
        if (convertView != null) {
            viewHolder = (AddressViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.address_item, null);
            viewHolder = new AddressViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        AddressEntity address = addressList.get(position);

        viewHolder.Name.setText(address.getName());
        viewHolder.Mobile.setText(address.getMobile());
        viewHolder.Address.setText(address.getAddress());

        if(address.getIsdefault())
        {
            viewHolder.container.setBackgroundResource(R.drawable.popo_or);

            viewHolder.ActionDefalut.setTextColor(context.getResources().getColor(R.color.grey));
        }

        viewHolder.ActionDelete.setTag(position);
        viewHolder.ActionDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int pos = Integer.valueOf(v.getTag().toString());
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("提示")
                        .setMessage("确认要删除这个地址吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AddressEntity addressEntity =  addressList.get(pos);
                                deleteAddress(addressEntity.getId());
                            }

                        })
                        .setNegativeButton("取消", null)
                        .show();




            }
        });

        viewHolder.ActionModify.setTag(position);
        viewHolder.ActionModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = Integer.valueOf(v.getTag().toString());
               AddressEntity addressEntity =  addressList.get(pos);
                Intent intent = new Intent(context, AddressEditerActivity.class);
                intent.putExtra("address", addressEntity);
                ((Activity) context).startActivityForResult(intent, 2);
            }
        });


        return convertView;
    }

    private HTApplication getAppliction()
    {
        HTApplication app =(HTApplication)context.getApplicationContext();
        return app;
    }

    private void deleteAddress(String id)
    {
        String url = HostsPath.HostUri + "OrderAppInterFace.ashx?method=UserAddressDelete";
        HTApplication app = getAppliction();
        Account account = app.GetSystemDomain(Account.class);

        if(account!=null) {
            RequestParams params = new RequestParams();
            params.add("id",id);
            pd = ProgressDialog.show(context, "提示", "加载中，请稍后……");
            HttpUtilsAsync.get(url, params, new TextHttpResponseHandler("GB2312") {
                @Override
                public void onSuccess(int i, Header[] headers, String responseString) {

                    JSONObject stateObject = JSON.parseObject(responseString);
                    //String code = stateObject.getString("statecode");
                    Toast.makeText(context, stateObject.getString("msg"), Toast.LENGTH_LONG).show();
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
        else
        {
            Toast.makeText(context, "请重新登录系统！",
                    Toast.LENGTH_LONG).show();

        }
    }






    static class AddressViewHolder {
        @InjectView(R.id.address_item_container)
        LinearLayout container;
        @InjectView(R.id.address_item_name)
        TextView Name;
        @InjectView(R.id.address_item_mobile)
        TextView Mobile;
        @InjectView(R.id.address_item_address)
        TextView Address;
        @InjectView(R.id.address_item_action_delete)
        TextView ActionDelete;
        @InjectView(R.id.address_item_action_default)
        TextView ActionDefalut;
        @InjectView(R.id.address_item_action_modify)
        TextView ActionModify;

        public AddressViewHolder(View view)
        {
            ButterKnife.inject(this, view);
        }

    }
}