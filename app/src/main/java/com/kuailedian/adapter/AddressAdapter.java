package com.kuailedian.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kuailedian.entity.AddressEntity;
import com.kuailedian.happytouch.R;

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




        return convertView;
    }



    static class AddressViewHolder {
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