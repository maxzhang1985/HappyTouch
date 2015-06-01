package com.kuailedian.entity;

import java.util.List;

/**
 * 一级Item实体类
 * 
 * @author zihao
 * 
 */
public class GroupStatusEntity {
	private String groupName;

	public boolean istoday() {
		return istoday;
	}

	public void setIstoday(boolean istoday) {
		this.istoday = istoday;
	}

	private boolean istoday;
	/** 二级Item数据列表 **/
	private List<ChildStatusEntity> childList;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<ChildStatusEntity> getChildList() {
		return childList;
	}

	public void setChildList(List<ChildStatusEntity> childList) {
		this.childList = childList;
	}

}