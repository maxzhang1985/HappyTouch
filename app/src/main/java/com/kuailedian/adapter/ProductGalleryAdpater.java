package com.kuailedian.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by maxzhang on 6/10/2015.
 */

public class ProductGalleryAdpater extends BaseAdapter {
    private Context mContext;

    private ArrayList<String> mImgList;

    public ProductGalleryAdpater(Context c , ArrayList<String> imglist) {
        mContext = c;
        mImgList = imglist;
    }

    @Override
    public int getCount() {
        return mImgList.size();
    }

    // 获取图片位置
    @Override
    public Object getItem(int position) {
        return mImgList.get(position);
    }

    // 获取图片ID
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageview = new ImageView(mContext);
        ImageLoader.getInstance().displayImage(mImgList.get(position),imageview);
	// 设置布局 图片120×120显示
        imageview.setScaleType(ImageView.ScaleType.CENTER);				// 设置显示比例类型（不缩放）
        return imageview;

    }
}