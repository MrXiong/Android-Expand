package com.bear.expanddemos.bean;

import java.io.Serializable;

public class StoreTypeNode implements Serializable {
	private String storeTypeId;
	private String storeTypeName;
	private String pid;
	private StoreTypeNode[] children;
	private int position;

	public String getStoreTypeId() {
		return storeTypeId;
	}

	public void setStoreTypeId(String storeTypeId) {
		this.storeTypeId = storeTypeId;
	}

	public String getStoreTypeName() {
		return storeTypeName;
	}

	public void setStoreTypeName(String storeTypeName) {
		this.storeTypeName = storeTypeName;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public StoreTypeNode[] getChildren() {
		return children;
	}

	public void setChildren(StoreTypeNode[] children) {
		this.children = children;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
