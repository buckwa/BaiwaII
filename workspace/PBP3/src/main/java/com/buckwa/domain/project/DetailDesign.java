package com.buckwa.domain.project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.buckwa.domain.BaseDomain;
import com.buckwa.domain.common.ImagePath;

public class DetailDesign extends BaseDomain{
	
	private Long detailDesignId;
	private String code;
	private String name;
	private String summary;
	private String step;
	private String dataPart;
 
	private Long moduleId;
	private String moduleName;
	private Long usecaseId;
	private String usecaseName;
	private Long projectId;
	
 
	
	private String reference;
	private List<ImagePath> screenImagePathList = new ArrayList();
	
	private List<ImagePath> filePathList = new ArrayList();
	 
	private CommonsMultipartFile screenFileData;
	private CommonsMultipartFile fileData;
	
	private List<TestCase> testCaseList;
	
	private UseCase usecase;
	
 
	public Long getDetailDesignId() {
		return detailDesignId;
	}
	public void setDetailDesignId(Long detailDesignId) {
		this.detailDesignId = detailDesignId;
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
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	 
	public String getDataPart() {
		return dataPart;
	}
	public void setDataPart(String dataPart) {
		this.dataPart = dataPart;
	}
	public List<ImagePath> getScreenImagePathList() {
		return screenImagePathList;
	}
	public void setScreenImagePathList(List<ImagePath> screenImagePathList) {
		this.screenImagePathList = screenImagePathList;
	}
	public List<ImagePath> getFilePathList() {
		return filePathList;
	}
	public void setFilePathList(List<ImagePath> filePathList) {
		this.filePathList = filePathList;
	}
	public CommonsMultipartFile getScreenFileData() {
		return screenFileData;
	}
	public void setScreenFileData(CommonsMultipartFile screenFileData) {
		this.screenFileData = screenFileData;
	}
	public CommonsMultipartFile getFileData() {
		return fileData;
	}
	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
 
	public Long getUsecaseId() {
		return usecaseId;
	}
	public void setUsecaseId(Long usecaseId) {
		this.usecaseId = usecaseId;
	}
	public String getUsecaseName() {
		return usecaseName;
	}
	public void setUsecaseName(String usecaseName) {
		this.usecaseName = usecaseName;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public List<TestCase> getTestCaseList() {
		return testCaseList;
	}
	public void setTestCaseList(List<TestCase> testCaseList) {
		this.testCaseList = testCaseList;
	}
	public UseCase getUsecase() {
		return usecase;
	}
	public void setUsecase(UseCase usecase) {
		this.usecase = usecase;
	}	 
	
	
	
	

}
