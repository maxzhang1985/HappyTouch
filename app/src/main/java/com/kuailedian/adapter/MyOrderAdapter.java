package com.kuailedian.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kuailedian.alipay.OrderInfo;
import com.kuailedian.alipay.PayApiHelper;
import com.kuailedian.entity.MyOrderItemEntity;
import com.kuailedian.happytouch.R;
import com.kuailedian.repository.AsyncCallBack;

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
   private Context context;

    public MyOrderAdapter(Context context, List<MyOrderItemEntity> order_list)
    {
        super(context, R.layout.myorder_item);
        this.context = context;
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


        final MyOrderItemEntity item = orderList.get(position);
        holder.id.setText(item.getId());


        holder.date.setText(item.getDate());

        holder.price.setText(item.getPrice());

        holder.state.setText(item.getStatemessage());

        if(item.getState().equals("0") && item.getPaycode().trim().equals("002"))
            holder.state.setText( "货到付款" );

        holder.btnPay.setFocusable(false);

        holder.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderInfo info = new OrderInfo();
                info.ID = item.getId();
                info.Subject = "快乐购物体验 e点外卖商超 送货到家";
                info.Body =  "快乐购物体验 e点外卖商超 送货到家";
                info.Price = item.getPrice();
                PayApiHelper payApiHelper = new PayApiHelper(context);
                payApiHelper.payAsync(info , new AsyncCallBack() {
                    @Override
                    public void onDataReceive(Object data, Object statusCode) {
                        if(statusCode.toString().equals("9000")) {
                            final AlertDialog dialog =  new AlertDialog.Builder(context)
                                    .setIcon(R.mipmap.icn_2)
                                    .setTitle("提示")
                                    .setMessage("支付成功")
                                    .show();
                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                }
                            }, 2800L);
                        }
                    }
                });
            }
        });



        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.recyclerView.setLayoutManager(layoutManager);

        holder.recyclerView.setAdapter(new ImageListAdapter(item.getImglist()));

        if(item.getState().equals("0") && item.getPaycode().trim().equals("001"))
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

        @InjectView(R.id.recyclerview_horizontal)
        RecyclerView recyclerView;

        @InjectView(R.id.myorder_item_pay)
        Button btnPay;

        @InjectView(R.id.address_item_container)
        LinearLayout contaier;



        public MyOrderViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
