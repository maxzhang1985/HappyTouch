package com.kuailedian.happytouch;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kuailedian.adapter.StatusExpandAdapter;
import com.kuailedian.applictionservice.IOrderCartOperator;
import com.kuailedian.components.DetailsPopupWindow;
import com.kuailedian.domain.OrderCart;
import com.kuailedian.entity.ChildStatusEntity;
import com.kuailedian.entity.GroupStatusEntity;
import com.kuailedian.repository.AsyncCallBack;
import com.kuailedian.repository.IAsyncRepository;
import com.kuailedian.repository.ReservationRepository;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by maxzhang on 4/22/2015.
 */
public class ReservationFragment extends OrderFragmentBase implements IOrderCartOperator {

    @InjectView(R.id.expandlist)
    ExpandableListView expandlistView;

    @InjectView(R.id.courses_title)
    TextView tv_phone;

    private ProgressDialog pd;

    private StatusExpandAdapter statusAdapter;
   // private Context context;
    IAsyncRepository repository = new  ReservationRepository();



    // TODO: Rename and change types and number of parameters
    public static ReservationFragment newInstance() {

        ReservationFragment  _fragment = new ReservationFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return _fragment;
    }

   public ReservationFragment(){

   }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_reservation, container, false);

        ButterKnife.inject(this, view);

        super.onViewCreated(view, savedInstanceState);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OrderCart cart = OrderCart.getOrderCart();
        tv_phone.setText(cart.getMobilePhone());
        initExpandListView();




    }


    /**
     * 初始化可拓展列表
     */
    private void initExpandListView() {

        final List<GroupStatusEntity> list = new ArrayList<GroupStatusEntity>();
        statusAdapter = new StatusExpandAdapter(context, this, list);
        expandlistView.setAdapter(statusAdapter);
        expandlistView.setGroupIndicator(null); // 去掉默认带的箭头
        final Handler handler = new Handler();

        pd = ProgressDialog.show(context, "提示", "加载中，请稍后……");
        repository.Get(new RequestParams(), new AsyncCallBack() {
            @Override
            public void onDataReceive(Object data, Object statusCode) {
                if(data!=null) {
                    List<GroupStatusEntity> entityList = (List<GroupStatusEntity>) data;
                    statusAdapter.AddItems(entityList);
                    statusAdapter.notifyDataSetChanged();
                    // 遍历所有group,将所有项设置成默认展开
                    int groupCount = expandlistView.getCount();
                    Log.v("display item count",String.valueOf(groupCount)) ;
                    for (int i = 0; i < groupCount; i++) {
                        expandlistView.expandGroup(i);

                        final int pos = i;

                        if (entityList.get(i).istoday()) {

                            handler.postDelayed(new Runnable(){
                                public void run() {
                                    Log.v("aa selection item of",String.valueOf(pos ) );
                                    expandlistView.setSelectedGroup(pos);
                                }
                            }, 800);

                        }
                    }
                }
                else
                {
                    Toast.makeText(context, "网格错误！", Toast.LENGTH_LONG).show();
                }
                expandlistView.setSelection(2);// 设置默认选中项
                pd.dismiss();
            }
        });







        expandlistView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // TODO Auto-generated method stub
                Log.v("popwindow","show the window111");
                expandlistView.expandGroup(groupPosition);
                return true;
            }
        });

        expandlistView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.v("popwindow", "show the window");
                ChildStatusEntity entity = (ChildStatusEntity) statusAdapter.getChild(groupPosition, childPosition);
                if (entity != null && !entity.getProductsid().equals("")) {

                    DetailsPopupWindow window = new DetailsPopupWindow(context, "reservation", entity.getProductsid());
                    window.showAtLocation(rootView, Gravity.CENTER, 0, 0);
                }
                return true;
            }
        });



    }



}
