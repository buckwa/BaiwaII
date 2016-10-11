package com.buckwa.domain.common;

import java.io.Serializable;

public class LovDetail implements Serializable{
	private static final long serialVersionUID = 4924601687478570852L;
	
	private Long lovId;
	private String type;
	private String code;
	private String name;
	private String desc;
	private String typeName;
	private String status;
	private Long headerId;
	
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
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getLovId() {
		return lovId;
	}
	public void setLovId(Long lovId) {
		this.lovId = lovId;
	}
	public Long getHeaderId() {
		return headerId;
	}
	public void setHeaderId(Long headerId) {
		this.headerId = headerId;
	}
	
 

}
