package com.kuailedian.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.kuailedian.domain.CartItem;
import com.kuailedian.domain.OrderCart;
import com.kuailedian.happytouch.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 磊 on 2015/6/5.
 */
public class SettleOrderAdapter extends BaseAdapter  {

    private LayoutInflater inflater = null;
    private Context context;
    private List<CartItem> cartItemList;

    public SettleOrderAdapter(Context _context,List<CartItem> list)
    {
        this.context = _context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cartItemList = list;
    }


    @Override
    public int getCount() {
        return cartItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        SettleOrderViewHolder holder;
        if (view != null) {
            holder = (SettleOrderViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.settle_cart_item, parent, false);
            holder = new SettleOrderViewHolder(view);
            view.setTag(holder);
        }


        CartItem item = cartItemList.get(position);
        holder.name.setText(item.getName());
        CharSequence money = "X" + String.valueOf( item.getAmount() );
        holder.amount.setText(money);
        CharSequence cs =  "￥" + String.valueOf( item.getMoney());
        holder.money.setText(cs);



        return view;
    }






    static class SettleOrderViewHolder {
        @InjectView(R.id.settle_item_name)
        TextView name;
        @InjectView(R.id.settle_item_amount)
        TextView amount;
        @InjectView(R.id.settle_item_money)
        TextView money;

        public SettleOrderViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
