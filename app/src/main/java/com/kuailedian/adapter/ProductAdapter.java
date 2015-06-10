package com.kuailedian.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuailedian.applictionservice.IOrderCartOperator;
import com.kuailedian.domain.CartItem;
import com.kuailedian.entity.ProductEntity;
import com.kuailedian.happytouch.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by root on 15-5-23.
 */
public class ProductAdapter extends ArrayAdapter<ProductEntity> {
    List<ProductEntity> productList = new ArrayList<ProductEntity>();
    private IOrderCartOperator ordercartOperator;
    private LayoutInflater inflater = null;

    public ProductAdapter(Context context,IOrderCartOperator operator,
                          List<ProductEntity> product_list) {
        super(context, R.layout.products_item);
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.ordercartOperator = operator;
        if(product_list!=null)
            this.productList = product_list;


    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public void clear() {
        this.productList.clear();
    }

    @Override
    public ProductEntity getItem(int position) {

       return productList.get(position);


    }

    @Override
    public boolean isEmpty() {
        return  this.productList==null || this.productList.isEmpty();
    }

    @Override
    public void addAll(Collection<? extends ProductEntity> collection) {
        productList.addAll(collection);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;

        ChildViewHolder viewHolder = null;
        final ProductEntity entity = getItem(position);
        if (convertView != null) {
            viewHolder = (ChildViewHolder) convertView.getTag();
        } else {
            viewHolder = new ChildViewHolder();
            convertView = inflater.inflate(R.layout.products_item, null);

            viewHolder.productName = (TextView) convertView
                    .findViewById(R.id.tv_productname);

            viewHolder.productMoney = (TextView) convertView
                    .findViewById(R.id.tv_productmoney);

            viewHolder.btnAddProduct =  (Button)convertView
                    .findViewById(R.id.btn_addproduct);

            viewHolder.productPicture =  (ImageView)convertView
                    .findViewById(R.id.img_productimg);


        }

        Log.v("productsdata",String.valueOf(pos));


        viewHolder.productName.setText(entity.getProductname());

        viewHolder.productMoney.setText(entity.getProductmoney());

        String imgurl = entity.getImgurl();
        if(!imgurl.equals(""))
        {
            ImageLoader.getInstance().displayImage(imgurl , viewHolder.productPicture );
        }


        viewHolder.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CartItem item = new CartItem();
                item.setId(entity.getProductid());
                item.setName(entity.getProductname());
                item.setMoney( Float.parseFloat( entity.getProductmoney() ));
                item.setIsSelected(true);
                item.setAmount(1);
                item.setType("product");

                ordercartOperator.AddProducts(v,item);
            }
        });



        convertView.setTag(viewHolder);
        return convertView;

    }




    private class ChildViewHolder {
        public ImageView productPicture;
        public TextView productName;
        public TextView productMoney;
        public Button btnAddProduct;
    }

}


