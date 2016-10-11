package com.buckwa.domain.pbp.report;

import java.io.Serializable;

public class RadarPlotReport implements Serializable{
	
	private int orderNo;
	private String axisName;
	private String axisValue;
	
	private String axisName2;
	private String axisValue2;
	
	private String mean;
	
	public String getMean() {
		return mean;
	}
	public void setMean(String mean) {
		this.mean = mean;
	}
	public String getAxisName() {
		return axisName;
	}
	public void setAxisName(String axisName) {
		this.axisName = axisName;
	}
	public String getAxisValue() {
		return axisValue;
	}
	public void setAxisValue(String axisValue) {
		this.axisValue = axisValue;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public String getAxisName2() {
		return axisName2;
	}
	public void setAxisName2(String axisName2) {
		this.axisName2 = axisName2;
	}
	public String getAxisValue2() {
		return axisValue2;
	}
	public void setAxisValue2(String axisValue2) {
		this.axisValue2 = axisValue2;
	}
 
 

}
