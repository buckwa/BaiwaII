package com.buckwa.domain.pbp.report;

public class WorkTypeCompareReport {
	
	private boolean type1;
	private boolean type2;
	private boolean type3;
	private boolean type4;
	private boolean type5;
	
	private int orderNo;
	private String categoryName;
	private String groupName;
	private String axisValue;
	
	public boolean isType1() {
		return type1;
	}

	public void setType1(boolean type1) {
		this.type1 = type1;
	}

	public boolean isType2() {
		return type2;
	}

	public void setType2(boolean type2) {
		this.type2 = type2;
	}

	public boolean isType3() {
		return type3;
	}

	public void setType3(boolean type3) {
		this.type3 = type3;
	}

	public boolean isType4() {
		return type4;
	}

	public void setType4(boolean type4) {
		this.type4 = type4;
	}

	public boolean isType5() {
		return type5;
	}

	public void setType5(boolean type5) {
		this.type5 = type5;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getAxisValue() {
		return axisValue;
	}

	public void setAxisValue(String axisValue) {
		this.axisValue = axisValue;
	}
	
}
