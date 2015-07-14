package com.kuailedian.domain;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by maxzhang on 6/3/2015.
 */
public class OrderCart  {

    public int getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(int minMoney) {
        this.minMoney = minMoney;
    }

    private int minMoney;

    private int startTime;

    private int endTime;

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

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

    public List<CartItem> toArray()
    {
        List<CartItem> list = new ArrayList<CartItem>();
        for(CartItem item :  items.values())
        {
            if(item.IsSelected())
                list.add(item);
        }

        return list;
    }

    public int getTotalAmount()
    {
        int amout = 0;
       for(Object key : items.keySet()) {
           CartItem item = items.get(key);
           if(item.IsSelected())
                amout += item.getAmount();
       }
        return  amout;
    }

    public float getToalMoney()
    {
        float money = 0.0f;
        for(Object key : items.keySet()) {
            CartItem item = items.get(key);
            if(item.IsSelected())
                money += item.getAmount() * item.getMoney() ;
        }
        return money;
    }

    public int getSelectedItemsCount()
    {
        int count = 0;
        for(Object key : items.keySet()) {
            CartItem item = items.get(key);
            if(item.IsSelected())
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
