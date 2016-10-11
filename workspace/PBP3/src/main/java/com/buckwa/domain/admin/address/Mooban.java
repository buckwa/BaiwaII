package com.buckwa.domain.admin.address;

import com.buckwa.domain.BaseDomain;

public class Mooban extends BaseDomain  {
	
	private Long moobanId;
	private String code;
	private String name;
	public Long getMoobanId() {
		return moobanId;
	}
	public void setMoobanId(Long moobanId) {
		this.moobanId = moobanId;
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
