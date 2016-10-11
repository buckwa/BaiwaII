package com.buckwa.domain.project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.buckwa.domain.BaseDomain;
import com.buckwa.domain.common.ImagePath;

public class Vision extends BaseDomain{
	
	private Long visionId; 
	private String summary;
	private String detail;
	private String projectName;
	private Long projectId;
	private String reference;
	private List<ImagePath> imagePathList;
	private List<ImagePath> filePathList;
	
	private CommonsMultipartFile fileData;
	
	private CommonsMultipartFile referenceFileData;
	
	public Long getVisionId() {
		return visionId;
	}
	public void setVisionId(Long visionId) {
		this.visionId = visionId;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
	public List<ImagePath> getImagePathList() {
		if(imagePathList==null){
			return new ArrayList<ImagePath>();
		}else{
			return imagePathList;
		}
		
	}
	
	
	public void setImagePathList(List<ImagePath> imagePathList) {
		this.imagePathList = imagePathList;
	}
	
	
	
	public List<ImagePath> getFilePathList() {
		if(filePathList==null){
			return new ArrayList<ImagePath>();
		}else{
			return filePathList;
		}
		 
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
	public CommonsMultipartFile getReferenceFileData() {
		return referenceFileData;
	}
	public void setReferenceFileData(CommonsMultipartFile referenceFileData) {
		this.referenceFileData = referenceFileData;
	}
 
 

}
