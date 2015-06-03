package com.kuailedian.domain;

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
        return items.get(pos);
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
        int money = 0;
        for(Object key : items.keySet()) {
            CartItem item = items.get(key);
            if(item.getIsSelected())
                money += item.getAmount() * item.getMoney() ;
        }
        return money;
    }




    private void onItemChanged()
    {

    }

}
