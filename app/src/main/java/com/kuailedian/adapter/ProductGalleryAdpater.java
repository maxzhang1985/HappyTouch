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

    // ��ȡͼƬλ��
    @Override
    public Object getItem(int position) {
        return mImgList.get(position);
    }

    // ��ȡͼƬID
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageview = new ImageView(mContext);
        ImageLoader.getInstance().displayImage(mImgList.get(position),imageview);
	// ���ò��� ͼƬ120��120��ʾ
        imageview.setScaleType(ImageView.ScaleType.CENTER);				// ������ʾ�������ͣ������ţ�
        return imageview;

    }
}