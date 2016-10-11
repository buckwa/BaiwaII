package com.buckwa.domain.common;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.buckwa.service.intf.util.PathUtil;

public class ImagePath implements Serializable{
	
	private Long imagePathId;
	private String projectId;
	private String imageCode;
	private String imagePath;
	private String  imageType;
	
	private String fullImagePath;
	private String fileName;
	

	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public Long getImagePathId() {
		return imagePathId;
	}
	public void setImagePathId(Long imagePathId) {
		this.imagePathId = imagePathId;
	}
	public String getImageCode() {
		return imageCode;
	}
	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public String getFullImagePath() {
		return fullImagePath;
	}
	public void setFullImagePath(String fullImagePath) {
		this.fullImagePath = fullImagePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
 
	

}
