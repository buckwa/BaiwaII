package com.buckwa.domain.admin.address;

import com.buckwa.domain.BaseDomain;

public class Province extends BaseDomain  {
	
	private Long provinceId;
	private String code;
	private String name;
	
	
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
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
