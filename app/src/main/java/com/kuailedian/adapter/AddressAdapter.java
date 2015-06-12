package com.kuailedian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kuailedian.entity.AddressEntity;
import com.kuailedian.happytouch.R;

import java.util.List;

import butterknife.ButterKnife;

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
    public boolean isEmpty() {
        return this.addressList == null || this.addressList.isEmpty();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AddressViewHolder viewHolder = null;
        AddressEntity entity = getItem(position);
        if (convertView != null) {
            viewHolder = (AddressViewHolder) convertView.getTag();
        } else {
            viewHolder = new AddressViewHolder(convertView);
            convertView = inflater.inflate(R.layout.products_item, null);
            convertView.setTag(viewHolder);
        }
        AddressEntity address = addressList.get(position);


        viewHolder.Name.setText(address.getName());
        viewHolder.Mobile.setText(address.getMobile());
        viewHolder.Address.setText(address.getAddress());




        return null;
    }



    static class AddressViewHolder {
        public TextView Name;
        public TextView Mobile;
        public TextView Address;
        public TextView ActionDelete;
        public TextView ActionDefalut;
        public TextView ActionModify;

        public AddressViewHolder(View view)
        {
            ButterKnife.inject(this, view);
        }

    }
}