package com.kuailedian.components;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kuailedian.adapter.OrderCartAdapter;
import com.kuailedian.domain.CartItem;
import com.kuailedian.domain.OrderCart;
import com.kuailedian.happytouch.OrderFragmentBase;
import com.kuailedian.happytouch.R;
import com.kuailedian.happytouch.SettleAccountActivity;

import java.util.ArrayList;

/**
 * Created by maxzhang on 4/17/2015.
 */
public class BottomPopupWindow extends PopupWindow {


    private  Context owner;
    private Fragment content;
    private View _view;

    private ArrayList<String> dataSource = new ArrayList<String>();

    private OrderCartAdapter adapter;

    TextView tvpop_count;
    TextView tvpop_price;
    CheckBox cb_selectedall;

    public void setShowBottomView(View view)
    {
        _view = view;
    }


    public BottomPopupWindow(final Context context,Fragment fragment)
    {
        super(context);
        this.owner = context;
        this.content = fragment;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.bottompopup_window, null);


        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);

        this.setBackgroundDrawable(new ColorDrawable(
                android.graphics.Color.TRANSPARENT));


        //control order popup view ,show or hide;
        ImageButton btnShowOrder  = (ImageButton) view.findViewById(R.id.btn_showolder);
        btnShowOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               BottomPopupWindow.this.dismiss();
            }
        });


        //submit the order
        ImageButton btnOrderSubmit  = (ImageButton) view.findViewById(R.id.btn_ordersubmit);
        btnOrderSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(OrderFragmentBase.isOnBuy(owner))
                    context.startActivity(new Intent(context, SettleAccountActivity.class));

            }
        });

        cb_selectedall = (CheckBox)view.findViewById(R.id.cb_selectedall);
        cb_selectedall.setChecked(true);
        cb_selectedall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                OrderCart cart = OrderCart.getOrderCart();
                for(int i=0;i<=cart.size()-1;i++)
                {
                    CartItem item = cart.get(i);
                    item.setIsSelected(isChecked);
                }
                adapter.notifyDataSetChanged();
            }
        });


        ListView ls = (ListView)view.findViewById(R.id.popupListView);

        adapter = new OrderCartAdapter(view.getContext());

        ls.setAdapter(adapter);

        tvpop_count = (TextView) view.findViewById(R.id.bpop_count);
        tvpop_price = (TextView) view.findViewById(R.id.bpop_price);


        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                OrderCart cart = OrderCart.getOrderCart();
                CharSequence strcount = String.valueOf(cart.getSelectedItemsCount());
                tvpop_count.setText(strcount);
                CharSequence strprice = String.valueOf(cart.getToalMoney());
                tvpop_price.setText(strprice);
            }
        });



        //getData();

    }





//    private void getData()
//    {
//        ArrayList<String> array = new ArrayList<String>();
//        array.add("the item 1");
//        array.add("the item 2");
//        array.add("the item 3");
//        array.add("the item 4");
//        array.add("the item 5");
//
//        adapter.addAll(array);
//        adapter.notifyDataSetChanged();
//
//    }



    public void Show(View v)
    {
        this.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }

    public void Show()
    {
        if(_view!=null)
            Show(_view);
    }


}
