package com.kuailedian.happytouch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.kuailedian.adapter.StatusExpandAdapter;
import com.kuailedian.applictionservice.IOrderCartOperator;
import com.kuailedian.entity.ChildStatusEntity;
import com.kuailedian.entity.GroupStatusEntity;

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

    @InjectView(R.id.ordercart_num)
    TextView orderNumView;


    private StatusExpandAdapter statusAdapter;
   // private Context context;


    // TODO: Rename and change types and number of parameters
    public static ReservationFragment newInstance() {
        ReservationFragment fragment = new ReservationFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
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

        initExpandListView();




    }


    /**
     * 初始化可拓展列表
     */
    private void initExpandListView() {
        statusAdapter = new StatusExpandAdapter(context, this, getListData());
        expandlistView.setAdapter(statusAdapter);
        expandlistView.setGroupIndicator(null); // 去掉默认带的箭头



        // 遍历所有group,将所有项设置成默认展开
        int groupCount = expandlistView.getCount();
        for (int i = 0; i < groupCount; i++) {
            expandlistView.expandGroup(i);
        }
        expandlistView.setSelection(7);// 设置默认选中项
        expandlistView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // TODO Auto-generated method stub
                expandlistView.expandGroup(groupPosition);
                return true;
            }
        });

        expandlistView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                return true;
            }
        });



    }

    private List<GroupStatusEntity> getListData() {
        List<GroupStatusEntity> groupList;
        String[] strArray = new String[] { "2015年5月4日", "2015年5月5日", "2015年5月6日", "2015年5月7日", "2015年5月8日", "2015年5月9日", "2015年5月10日" };
        String[][] childTimeArray = new String[][] {
                { "俯卧撑十次", "仰卧起坐二十次", "大喊我爱紫豪二十次", "每日赞紫豪一次" },
                { "亲，快快滴点赞哦~" }, { "没有赞的，赶紧去赞哦~" },   { "俯卧撑十次", "仰卧起坐二十次", "大喊我爱紫豪二十次", "每日赞紫豪一次" },
                { "亲，快快滴点赞哦~" }, { "没有赞的，赶紧去赞哦~" },   { "俯卧撑十次", "仰卧起坐二十次", "大喊我爱紫豪二十次", "每日赞紫豪一次" },
                };

        groupList = new ArrayList<GroupStatusEntity>();
        for (int i = 0; i < strArray.length; i++) {
            GroupStatusEntity groupStatusEntity = new GroupStatusEntity();
            groupStatusEntity.setGroupName(strArray[i]);

            List<ChildStatusEntity> childList = new ArrayList<ChildStatusEntity>();

            for (int j = 0; j < childTimeArray[i].length; j++) {
                ChildStatusEntity childStatusEntity = new ChildStatusEntity();
                childStatusEntity.setProductName(childTimeArray[i][j]);
                childList.add(childStatusEntity);
            }

            groupStatusEntity.setChildList(childList);
            groupList.add(groupStatusEntity);
        }
        return groupList;
    }

    @Override
    public void AddProducts(String productsid) {
        orderNumView.setText(productsid);



    }
}
