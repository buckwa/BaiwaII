package com.buckwa.domain.project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.buckwa.domain.BaseDomain;
import com.buckwa.domain.common.ImagePath;

public class UseCase extends BaseDomain{
	
	private Long usecaseId;
	private String code;
	private String name;
	private String summary;
	private String actor;
	private String basicFlow;
	private String alternateFlow;
	private Long moduleId;
	private Long projectId;
	
	private String reference;
	
	private CommonsMultipartFile fileData;
	
	private List<ImagePath> filePathList = new ArrayList();
	
	private List<DetailDesign> detailDesignList;
	
	public Long getUsecaseId() {
		return usecaseId;
	}
	public void setUsecaseId(Long usecaseId) {
		this.usecaseId = usecaseId;
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	public String getBasicFlow() {
		return basicFlow;
	}
	public void setBasicFlow(String basicFlow) {
		this.basicFlow = basicFlow;
	}
	public String getAlternateFlow() {
		return alternateFlow;
	}
	public void setAlternateFlow(String alternateFlow) {
		this.alternateFlow = alternateFlow;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public CommonsMultipartFile getFileData() {
		return fileData;
	}
	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}
	public List<ImagePath> getFilePathList() {
		return filePathList;
	}
	public void setFilePathList(List<ImagePath> filePathList) {
		this.filePathList = filePathList;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public List<DetailDesign> getDetailDesignList() {
		return detailDesignList;
	}
	public void setDetailDesignList(List<DetailDesign> detailDesignList) {
		this.detailDesignList = detailDesignList;
	}	 
	
	
	
	

}
