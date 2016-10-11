package com.buckwa.domain.project;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class Module extends BaseDomain{
	
	private Long moduleId;
	private String code;
	private String name;
	private String detail;
	private Long projectId;
	
	private Long childCount;
	
	private List<UseCase> useCaseList;
	
	private List<DetailDesign> detailDesignList;
	
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
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
	public List<UseCase> getUseCaseList() {
		return useCaseList;
	}
	public void setUseCaseList(List<UseCase> useCaseList) {
		this.useCaseList = useCaseList;
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
 
	 

}
