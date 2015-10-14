package com.kuailedian.components;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kuailedian.adapter.ProductGalleryAdpater;
import com.kuailedian.adapter.SubFoodAdapter;
import com.kuailedian.entity.FoodDetailEntity;
import com.kuailedian.entity.ProductDetailEntity;
import com.kuailedian.happytouch.R;
import com.kuailedian.repository.AsyncCallBack;
import com.kuailedian.repository.ProductDetailRepository;
import com.kuailedian.repository.ReservationDetailRepository;
import com.loopj.android.http.RequestParams;
/**
 * Created by maxzhang on 5/25/2015.
 */
public class DetailsPopupWindow extends PopupWindow {

    private Context _context;
    private String _type;


    public DetailsPopupWindow(final Context context,String type,String productid)
    {
        _context = context;
        _type = type;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.detailspopup_window, null);


        this.setContentView(view);

        Button btn_close = (Button)view.findViewById(R.id.btn_detail_close);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsPopupWindow.this.dismiss();
            }
        });

        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_horizontal);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


        recyclerView.setLayoutManager(layoutManager);
        final Gallery gallery = (Gallery)view.findViewById(R.id.detail_gallery);
        LinearLayout detail_layout =  (LinearLayout)view.findViewById(R.id.detail_peicai_layout);
        RequestParams params = new RequestParams();
        params.add("productid", productid);

        if(type.equals("product"))
        {

            detail_layout.setVisibility(View.GONE);

            ProductDetailRepository repository = new ProductDetailRepository();

            repository.Get(params,new AsyncCallBack(){
                @Override
                public void onDataReceive(Object data, Object statusCode) {
                    ProductDetailEntity detailEntity  =  (ProductDetailEntity)data;

                    setDetailInfomation(view,detailEntity.getRemark(),detailEntity.getDeliveryare());

                    gallery.setAdapter(new ProductGalleryAdpater(context,detailEntity.getImglist()));

                }
            });
            

        }
        else
        {
            detail_layout.setVisibility(View.VISIBLE);


            recyclerView.setVisibility(View.VISIBLE);

            ReservationDetailRepository repository = new ReservationDetailRepository();
            repository.Get(params,new AsyncCallBack(){
                @Override
                public void onDataReceive(Object data, Object statusCode) {
                    FoodDetailEntity detailEntity =  (FoodDetailEntity)data;
                    setDetailInfomation(view,detailEntity.getRemark(),detailEntity.getDeliveryare());
                    gallery.setAdapter(new ProductGalleryAdpater(context,detailEntity.getImglist()));
                    recyclerView.setAdapter(new SubFoodAdapter(detailEntity.getSubfoodlist()));
                }
            });









        }

        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);

        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimBottom);

        this.setBackgroundDrawable(new ColorDrawable(
                android.graphics.Color.TRANSPARENT));


    }


    private void setDetailInfomation(View view,String remark,String deliveryare)
    {
        TextView tvRemark = (TextView)view.findViewById(R.id.detail_remark);
        TextView tvDeliveryare = (TextView)view.findViewById(R.id.detail_deliveryare);
        CharSequence csRemark = remark;
        CharSequence csDeliveryare = deliveryare;
        tvRemark.setText(csRemark);
        tvDeliveryare.setText(csDeliveryare);
    }



}
