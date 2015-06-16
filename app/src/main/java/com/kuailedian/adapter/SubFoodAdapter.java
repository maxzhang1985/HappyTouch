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
    // ���ݼ�
    private List<SubFoodEntity> mDataset;
    public SubFoodAdapter(List<SubFoodEntity> dataset) {
        super();
        mDataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // ����һ��View�������ֱ��ʹ��ϵͳ�ṩ�Ĳ��֣�����һ��TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.subfood_item, null);
        // ����һ��ViewHolder
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
