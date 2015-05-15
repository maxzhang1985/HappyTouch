package com.kuailedian.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuailedian.applictionservice.IOrderCartOperator;
import com.kuailedian.entity.ChildStatusEntity;
import com.kuailedian.entity.GroupStatusEntity;
import com.kuailedian.happytouch.R;

import java.util.List;

public class StatusExpandAdapter extends BaseExpandableListAdapter {
	private LayoutInflater inflater = null;
	private final List<GroupStatusEntity> groupList;
	private Context context;
	private IOrderCartOperator ordercartOperator;


	public StatusExpandAdapter(Context context,IOrderCartOperator operator,
			List<GroupStatusEntity> group_list) {
		this.groupList = group_list;
		this.context = context;
		this.ordercartOperator = operator;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * 返回一级Item总数
	 */
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groupList.size();
	}

	/**
	 * 返回二级Item总数
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		if (groupList.get(groupPosition).getChildList() == null) {
			return 0;
		} else {
			return groupList.get(groupPosition).getChildList().size();
		}
	}

	/**
	 * 获取一级Item内容
	 */
	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groupList.get(groupPosition);
	}

	/**
	 * 获取二级Item内容
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return groupList.get(groupPosition).getChildList().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		GroupViewHolder holder = new GroupViewHolder();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.group_status_item, null);
		}
		holder.groupName = (TextView) convertView
				.findViewById(R.id.one_status_name);

		holder.Week = (ImageView) convertView.findViewById(R.id.imageView1);

		Log.v("happlyLog","found week imageview");

		holder.groupName.setText(groupList.get(groupPosition).getGroupName());


		Drawable db = getResourceDrawableByName("week" + (groupPosition+1));

		holder.Week.setImageDrawable(db);
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final int pos = childPosition;

		ChildViewHolder viewHolder = null;
		ChildStatusEntity entity = (ChildStatusEntity) getChild(groupPosition,
				childPosition);
		if (convertView != null) {
			viewHolder = (ChildViewHolder) convertView.getTag();
		} else {
			viewHolder = new ChildViewHolder();
			convertView = inflater.inflate(R.layout.child_status_item, null);

			viewHolder.productName = (TextView) convertView
					.findViewById(R.id.tv_productname);

			viewHolder.productMoney = (TextView) convertView
					.findViewById(R.id.tv_productmoney);

			viewHolder.btnAddProduct =  (Button)convertView
					.findViewById(R.id.btn_addproduct);

			viewHolder.productPicture =  (ImageView)convertView
					.findViewById(R.id.img_productimg);


		}


		viewHolder.productName.setText(entity.getProductName());

		viewHolder.btnAddProduct.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				ordercartOperator.AddProducts(String.valueOf(pos));
			}
		});



		convertView.setTag(viewHolder);
		return convertView;
	}

	private Drawable getResourceDrawableByName(String name) {
		Log.v("happlyLog",name);
		Resources resources = context.getResources();
		final int resourceId = resources.getIdentifier(name, "mipmap",
				context.getPackageName());
		return resources.getDrawable(resourceId);
	}


	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

	private class GroupViewHolder {
		ImageView Week;
		TextView groupName;
	}

	private class ChildViewHolder {
		public ImageView productPicture;
		public TextView productName;
		public TextView productMoney;
		public Button btnAddProduct;
	}

}
