package com.kuailedian.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.kuailedian.happytouch.R;

/**
 * Created by maxzhang on 6/10/2015.
 */
public class ProductGalleryAdpater {
}

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    //  ͼƬ����Դ
    private Integer[] imgs = { R.drawable.img1, R.drawable.img2,
            R.drawable.img3, R.drawable.img4, R.drawable.img5,
            R.drawable.img6, R.drawable.img7};

    public ImageAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    // ��ȡͼƬλ��
    @Override
    public Object getItem(int position) {
        return imgs[position];
    }

    // ��ȡͼƬID
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageview = new ImageView(mContext);

        imageview.setImageResource(imgs[position]);
        imageview.setLayoutParams(new Gallery.LayoutParams(240, 120));		// ���ò��� ͼƬ120��120��ʾ
        imageview.setScaleType(ImageView.ScaleType.CENTER);				// ������ʾ�������ͣ������ţ�
        return imageview;
    }
}