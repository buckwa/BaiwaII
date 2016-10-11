package com.buckwa.domain.pbp.report;

import java.io.Serializable;

public class MinMaxBean implements Serializable{
	
	private String minValue;
	private String maxValue;
	private String meanValue;
	
	private String minDesc;
	private String maxDesc;
	
	
	public String getMinValue() {
		return minValue;
	}
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	public String getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	public String getMeanValue() {
		return meanValue;
	}
	public void setMeanValue(String meanValue) {
		this.meanValue = meanValue;
	}
	public String getMinDesc() {
		return minDesc;
	}
	public void setMinDesc(String minDesc) {
		this.minDesc = minDesc;
	}
	public String getMaxDesc() {
		return maxDesc;
	}
	public void setMaxDesc(String maxDesc) {
		this.maxDesc = maxDesc;
	}
	
	

}
