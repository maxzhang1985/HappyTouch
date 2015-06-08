package com.kuailedian.domain;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by maxzhang on 6/3/2015.
 */
public class OrderCart  {



    private HashMap<String,CartItem> items;

    public OrderCart()
    {
        items = new HashMap<String,CartItem>();
    }

    private static OrderCart instance;
    public static synchronized  OrderCart getOrderCart()
    {
            if (instance == null) {
                instance = new OrderCart();
            }
            return instance;
    }



    public void addCart(CartItem addtionitem)
    {

        if(items.containsKey(addtionitem.getId()))
        {
            CartItem item = items.get(addtionitem.getId());
            item.setAmount( item.getAmount() + 1  );

        }
        else
        {
            this.items.put(addtionitem.getId(),addtionitem);
        }

        onItemChanged();
    }

    public void selectItem(String id,boolean selected)
    {
        if(items.containsKey(id)) {
            CartItem item = items.get(id);
            item.setIsSelected(selected);
            onItemChanged();
        }
    }


    public void removeById(String id)
    {
        if(items.containsKey(id))
        {
            CartItem item = items.get(id);
            int amount = item.getAmount() -1 ;
            item.setAmount( amount  );
            if(amount<=0)
            {
                items.remove(id);
            }
            onItemChanged();
        }
    }


    public CartItem get(int pos)
    {
        return  (new ArrayList<CartItem>(items.values())).get(pos);
    }


    public int getTotalAmount()
    {
        int amout = 0;
       for(Object key : items.keySet()) {
           CartItem item = items.get(key);
           if(item.getIsSelected())
                amout += item.getAmount();
       }
        return  amout;
    }

    public float getToalMoney()
    {
        float money = 0.0f;
        for(Object key : items.keySet()) {
            CartItem item = items.get(key);
            if(item.getIsSelected())
                money += item.getAmount() * item.getMoney() ;
        }
        return money;
    }

    public int getSelectedItemsCount()
    {
        int count = 0;
        for(Object key : items.keySet()) {
            CartItem item = items.get(key);
            if(item.getIsSelected())
                count ++;
        }
        return  count;
    }

    public void clear()
    {
        items.clear();
    }

    public int size()
    {
        return items.size();
    }


    private void onItemChanged()
    {
        for(Object key : items.keySet()) {
            CartItem item = items.get(key);
            Log.v("ordercart", item.toString());
        }
    }

}
