package com.buckwa.domain.project;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class LabCategory extends BaseDomain{
	
	private Long labCategoryId;
	private String code;
	private String name;
	private String detail;
	private Long projectId;
	
	private Long childCount;
	
	private List<Lab> labList;
	
	private List<DetailDesign> detailDesignList;
	
 
	public Long getLabCategoryId() {
		return labCategoryId;
	}
	public void setLabCategoryId(Long labCategoryId) {
		this.labCategoryId = labCategoryId;
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
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
 
	public List<DetailDesign> getDetailDesignList() {
		return detailDesignList;
	}
	public void setDetailDesignList(List<DetailDesign> detailDesignList) {
		this.detailDesignList = detailDesignList;
	}
	public Long getChildCount() {
		return childCount;
	}
	public void setChildCount(Long childCount) {
		this.childCount = childCount;
	}
	public List<Lab> getLabList() {
		return labList;
	}
	public void setLabList(List<Lab> labList) {
		this.labList = labList;
	}
 
	 

}
