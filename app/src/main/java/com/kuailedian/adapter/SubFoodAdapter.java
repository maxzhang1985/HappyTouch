package com.kuailedian.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kuailedian.entity.SubFoodEntity;
import com.kuailedian.happytouch.R;

import java.util.List;

/**
 * Created by maxzhang on 6/16/2015.
 */
public class SubFoodAdapter  extends RecyclerView.Adapter<SubFoodAdapter.ViewHolder>{
    // 数据集
    private List<SubFoodEntity> mDataset;
    public SubFoodAdapter(List<SubFoodEntity> dataset) {
        super();
        mDataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.subfood_item, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {


    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }





    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;



        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }
    }
}
