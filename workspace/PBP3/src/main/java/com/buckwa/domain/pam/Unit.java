package com.buckwa.domain.pam;

import com.buckwa.domain.BaseDomain;

public class Unit extends BaseDomain  {
	
	private Long unitId;
	private String code;
	private String name;
	 
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
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
 
	
	
	 
}
