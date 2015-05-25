package com.kuailedian.components;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.kuailedian.happytouch.R;

/**
 * Created by maxzhang on 5/25/2015.
 */
public class DetailsPopupWindow extends PopupWindow {

    private Context _context;

    public DetailsPopupWindow(Context context)
    {
        _context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.detailspopup_window, null);


        this.setContentView(view);
        //����SelectPicPopupWindow��������Ŀ�
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        //����SelectPicPopupWindow��������ĸ�
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //����SelectPicPopupWindow��������ɵ��
        this.setFocusable(true);
        //����SelectPicPopupWindow�������嶯��Ч��
        this.setAnimationStyle(R.style.AnimBottom);

        this.setBackgroundDrawable(new ColorDrawable(
                android.graphics.Color.TRANSPARENT));


    }

}
