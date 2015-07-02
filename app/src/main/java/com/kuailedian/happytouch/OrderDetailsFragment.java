package com.kuailedian.happytouch;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kuailedian.adapter.SettleOrderAdapter;
import com.kuailedian.domain.Account;
import com.kuailedian.domain.CartItem;
import com.kuailedian.repository.HostsPath;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.marshalchen.common.commonUtils.urlUtils.HttpUtilsAsync;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by maxzhang on 7/2/2015.
 */
public class OrderDetailsFragment extends Fragment {
    private Context context;
    ProgressDialog pd;

    @InjectView(R.id.order_details_address)
    TextView details_address;

    @InjectView(R.id.order_details_orderid)
    TextView details_orderid;

    @InjectView(R.id.order_details_createtime)
    TextView details_createtime;

    @InjectView(R.id.order_details_paytime)
    TextView details_paytime;

    @InjectView(R.id.order_details_deliverytime)
    TextView details_deliverytime;

    @InjectView(R.id.order_details_complatetime)
    TextView details_complatetime;

    @InjectView(R.id.order_details_listview)
    ListView details_listview;


    // TODO: Rename and change types and number of parameters
    public static OrderDetailsFragment newInstance(String id) {

        OrderDetailsFragment   fragment = new OrderDetailsFragment();
        Bundle args = new Bundle();
        args.putString("orderid", id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_order_details, container, false);

        ButterKnife.inject(this, rootView);

        context = rootView.getContext();

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        String orderid = bundle.getString("orderid");
        String url = HostsPath.HostUri + "OrderAppInterFace.ashx?method=GetOrderDetails";
        RequestParams params = new RequestParams();
        params.setContentEncoding("GB2312");
        params.add("orderid", orderid );

        pd = ProgressDialog.show(context, "提示", "正在查询，请稍后……");
        HttpUtilsAsync.post(url, params, new TextHttpResponseHandler("GB2312") {
            @Override
            public void onSuccess(int i, Header[] headers, String responseString) {
                pd.dismiss();
                JSONObject jsonObject = JSON.parseObject(responseString);
                details_address.setText(jsonObject.getString("addresscode"));
                details_orderid.setText(jsonObject.getString("orderid"));
                details_complatetime.setText(jsonObject.getString("complatetime"));
                details_deliverytime.setText(jsonObject.getString("deliverytime"));
                details_paytime.setText(jsonObject.getString("paytime"));
                details_createtime.setText(jsonObject.getString("createtime"));

                JSONArray jsonArray =  jsonObject.getJSONArray("cart");
                List<CartItem> cartItemList = new ArrayList<CartItem>();

                for(int index=0;index<=jsonArray.size()-1;index++)
                {

                    JSONObject jsonCartItem = jsonArray.getJSONObject(index);
                    CartItem item = new CartItem();
                    item.setId(jsonCartItem.getString("id") );
                    item.setAmount(Integer.valueOf( jsonCartItem.getString("amount")) );
                    item.setMoney(Float.valueOf(jsonCartItem.getString("price")) );
                    item.setType( jsonCartItem.getString("type") );
                    item.setName(  jsonCartItem.getString("name")  );

                    cartItemList.add(item);
                }

                details_listview.setAdapter(new SettleOrderAdapter(context,cartItemList));


            }

            @Override
            public void onFailure(int i, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(context, "网络错误！",
                        Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        });



            }

            private Account getAccount() {
                HTApplication app = (HTApplication) getActivity().getApplication();
                Account account = app.GetSystemDomain(Account.class);
                return account;
            }

 }
