package com.kuailedian.entity;

/**
 * 二级Item实体类
 *
 * @author zihao
 *
 */
public class ChildStatusEntity {
	/** 预计完成时间 **/
	private String productName;
	/** 是否已完成 **/
	private boolean isfinished;

	public String getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(String unitprice) {
		this.unitprice = unitprice;
	}

	private String unitprice;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String name) {
		this.productName = name;
	}


	public String getProductsid() {
		return productsid;
	}

	public void setProductsid(String productsid) {
		this.productsid = productsid;
	}

	private String productsid;


	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	private String img;


}
