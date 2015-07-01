package com.kuailedian.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuailedian.entity.SubFoodEntity;
import com.kuailedian.happytouch.R;
import com.marshalchen.common.ui.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Maxzhang on 2015/7/1.
 */

public class ImageListAdapter  extends RecyclerView.Adapter<ImageListAdapter.ViewHolder>{

    private List<String> mDataset;
    public ImageListAdapter(List<String> dataset) {
        super();
        mDataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.subfood_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        String sub = mDataset.get(i);
        viewHolder.Image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.Image.setImageResource(R.mipmap.no_photo);
        ImageLoader.getInstance().displayImage(sub,viewHolder.Image);

    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }





    static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView Name;
        public RoundedImageView Image;


        public ViewHolder(View itemView) {
            super(itemView);
            this.Name = (TextView)itemView.findViewById(R.id.subfood_item_name);
            this.Image = (RoundedImageView)itemView.findViewById(R.id.subfood_item_img);
        }
    }
}