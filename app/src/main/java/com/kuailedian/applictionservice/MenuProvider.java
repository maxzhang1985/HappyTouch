package com.kuailedian.applictionservice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import com.kuailedian.happytouch.R;
import com.yalantis.contextmenu.lib.MenuObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxzhang on 4/16/2015.
 */
public class MenuProvider {

    public static List<MenuObject> getMenuObjects(Context context) {
        // You can use any [resource, bitmap, drawable, color] as image:
        // item.setResource(...)
        // item.setBitmap(...)
        // item.setDrawable(...)
        // item.setColor(...)
        // You can set image ScaleType:
        // item.setScaleType(ScaleType.FIT_XY)
        // You can use any [resource, drawable, color] as background:
        // item.setBgResource(...)
        // item.setBgDrawable(...)
        // item.setBgColor(...)
        // You can use any [color] as text color:
        // item.setTextColor(...)
        // You can set any [color] as divider color:
        // item.setDividerColor(...)

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.mipmap.ic_renyimen_open);

        MenuObject send = new MenuObject("购物");
        send.setResource(R.mipmap.ic_shoppingcart);

        MenuObject like = new MenuObject("美食");
        Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_food);
        like.setBitmap(b);

        MenuObject addFr = new MenuObject("个人中心");
        BitmapDrawable bd = new BitmapDrawable(context.getResources(),
                BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_user));
        addFr.setDrawable(bd);

        MenuObject addFav = new MenuObject("注销");
        addFav.setResource(R.mipmap.ic_zhuxiao);


        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(like);
        menuObjects.add(addFr);
        menuObjects.add(addFav);
        return menuObjects;
    }


}
