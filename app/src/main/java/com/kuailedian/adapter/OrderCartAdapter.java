package com.kuailedian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kuailedian.domain.CartItem;
import com.kuailedian.domain.OrderCart;
import com.kuailedian.happytouch.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 磊 on 2015/6/5.
 */
public class OrderCartAdapter extends BaseAdapter  {

    private LayoutInflater inflater = null;
    private Context context;
    private OrderCart orderCart;

    public OrderCartAdapter(Context _context)
    {
        this.context = _context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        orderCart = OrderCart.getOrderCart();
    }


    @Override
    public int getCount() {
        return orderCart.size();
    }

    @Override
    public Object getItem(int position) {
        return orderCart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        final int pos = position;
        CartViewHolder holder;
        if (view != null) {
            holder = (CartViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.popupwindow_item, parent, false);
            holder = new CartViewHolder(view);
            view.setTag(holder);
        }

        CartItem item = orderCart.get(pos);

        holder.name.setText(item.getName());
        CharSequence money = String.valueOf( item.getAmount() );
        holder.amount.setText(money);
        CharSequence cs =  "￥" + String.valueOf( item.getMoney());
        holder.price.setText(cs);
        holder.selected.setChecked(item.getIsSelected());

        return view;
    }



    static class CartViewHolder {
        @InjectView(R.id.cartitem_name)
        TextView name;
        @InjectView(R.id.cartitem_amount)
        TextView amount;
        @InjectView(R.id.cartitem_price)
        TextView price;
        @InjectView(R.id.cartitem_selected)
        CheckBox selected;
        @InjectView(R.id.btn_cartitem_add)
        Button btnAdd;
        @InjectView(R.id.btn_cartitem_sub)
        Button btnSub;


        public CartViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
