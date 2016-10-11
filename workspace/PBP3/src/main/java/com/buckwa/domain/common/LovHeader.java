package com.buckwa.domain.common;

import java.io.Serializable;
import java.util.List;

public class LovHeader implements Serializable{
	private static final long serialVersionUID = 4924601687478570852L;
	
	private Long lovHeaderId; 
	private String code;
	private String name;
	private String status;
	
	private String detailCode;
	private String detailName;
	private String detailstatus;
	private Long lovDetailId; 
	private List<LovDetail> detailList;
	
	public Long getLovHeaderId() {
		return lovHeaderId;
	}
	public void setLovHeaderId(Long lovHeaderId) {
		this.lovHeaderId = lovHeaderId;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<LovDetail> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<LovDetail> detailList) {
		this.detailList = detailList;
	}
	public String getDetailCode() {
		return detailCode;
	}
	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	public String getDetailName() {
		return detailName;
	}
	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}
	public String getDetailstatus() {
		return detailstatus;
	}
	public void setDetailstatus(String detailstatus) {
		this.detailstatus = detailstatus;
	}
	public Long getLovDetailId() {
		return lovDetailId;
	}
	public void setLovDetailId(Long lovDetailId) {
		this.lovDetailId = lovDetailId;
	} 
	 
}
