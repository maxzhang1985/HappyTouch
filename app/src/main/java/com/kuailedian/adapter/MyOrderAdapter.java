package com.kuailedian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kuailedian.alipay.PayApiHelper;
import com.kuailedian.entity.MyOrderItemEntity;
import com.kuailedian.happytouch.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Maxzhang on 2015/6/26.
 */
public class MyOrderAdapter extends ArrayAdapter<MyOrderItemEntity> {

    List<MyOrderItemEntity> orderList = new ArrayList<MyOrderItemEntity>();
    private LayoutInflater inflater = null;

    public MyOrderAdapter(Context context, List<MyOrderItemEntity> order_list)
    {
        super(context, R.layout.myorder_item);
        this.orderList = order_list;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public void clear() {
        this.orderList.clear();
    }

    @Override
    public MyOrderItemEntity getItem(int position) {

        return orderList.get(position);


    }

    @Override
    public boolean isEmpty() {
        return  this.orderList==null || this.orderList.isEmpty();
    }

    @Override
    public void addAll(Collection<? extends MyOrderItemEntity> collection) {
        orderList.addAll(collection);
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        MyOrderViewHolder holder;
        if (view != null) {
            holder = (MyOrderViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.myorder_item, parent, false);
            holder = new MyOrderViewHolder(view);
            view.setTag(holder);
        }


        MyOrderItemEntity item = orderList.get(position);
        holder.id.setText(item.getId());


        holder.date.setText(item.getDate());

        holder.price.setText(item.getPrice());

        holder.state.setText(item.getState());

        if(item.getState().equals("0"))
        {
            holder.donelayout.setVisibility(View.GONE);
            holder.pedonelayout.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.donelayout.setVisibility(View.VISIBLE);
            holder.pedonelayout.setVisibility(View.GONE);
        }



        return view;
    }

    static class MyOrderViewHolder {
        @InjectView(R.id.myorder_item_id)
        TextView id;
        @InjectView(R.id.myorder_item_datetime)
        TextView date;
        @InjectView(R.id.myorder_item_totalmoney)
        TextView price;
        @InjectView(R.id.myorder_item_state)
        TextView state;
        @InjectView(R.id.myorder_item_done)
        LinearLayout donelayout;

        @InjectView(R.id.myorder_item_pedone)
        LinearLayout pedonelayout;

        public MyOrderViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
