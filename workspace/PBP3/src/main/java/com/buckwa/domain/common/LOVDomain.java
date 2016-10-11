package com.buckwa.domain.common;

import java.io.Serializable;

public class LOVDomain implements Serializable{

	private static final long serialVersionUID = 4924601687478570852L;
	
	private String type;
	private String code;
	private String name;
	private String desc;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
 

}
