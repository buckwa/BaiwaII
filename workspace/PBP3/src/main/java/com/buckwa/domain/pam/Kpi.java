package com.buckwa.domain.pam;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class Kpi extends BaseDomain  {
	
	private Long kpiId;
	private String code;
	private String name;
	private Long kpiCategoryId;
	private String check;
	
	private String categoryName;
	

	
	public Long getKpiId() {
		return kpiId;
	}
	public void setKpiId(Long kpiId) {
		this.kpiId = kpiId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getKpiCategoryId() {
		return kpiCategoryId;
	}
	public void setKpiCategoryId(Long kpiCategoryId) {
		this.kpiCategoryId = kpiCategoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
 
	
	 
}
