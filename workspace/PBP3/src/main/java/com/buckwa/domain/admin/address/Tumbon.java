package com.buckwa.domain.admin.address;

import com.buckwa.domain.BaseDomain;

public class Tumbon extends BaseDomain  {
	
	private Long tumbonId;
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
	public Long getTumbonId() {
		return tumbonId;
	}
	public void setTumbonId(Long tumbonId) {
		this.tumbonId = tumbonId;
	}
	
	
	

}
