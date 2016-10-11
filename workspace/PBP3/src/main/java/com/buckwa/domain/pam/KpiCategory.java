package com.buckwa.domain.pam;

import java.util.List;

import com.buckwa.domain.BaseDomain;
import com.buckwa.domain.common.LovDetail;

public class KpiCategory extends BaseDomain  {
	
	private Long kpiCategoryId;
	private String code;
	private String name;
	private String employeeType;
	private List<LovDetail> lovEmployeeTypeList;
	
	
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	public List<LovDetail> getLovEmployeeTypeList() {
		return lovEmployeeTypeList;
	}
	public void setLovEmployeeTypeList(List<LovDetail> lovEmployeeTypeList) {
		this.lovEmployeeTypeList = lovEmployeeTypeList;
	}
	public Long getKpiCategoryId() {
		return kpiCategoryId;
	}
	public void setKpiCategoryId(Long kpiCategoryId) {
		this.kpiCategoryId = kpiCategoryId;
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
	
	
	
	 
}
