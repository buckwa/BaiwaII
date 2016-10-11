package com.buckwa.domain.pbp;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.buckwa.domain.BaseDomain;

public class AcademicKPIUserMappingWrapper   extends BaseDomain{
	
	 
	private String academicYear;
	private List<AcademicKPIUserMapping> academicKPIUserMappingList;
	
	private AcademicKPIUserMapping academicKPIUserMapping;
	private PBPWorkType pBPWorkType;
	private List<AcademicKPIAttributeValue> academicKPIAttributeValueList;
	
	private Department department;
	private CommonsMultipartFile fileData;
	
	private String replyMessage;
	
	private String errorDesc;
	
	private int totalApproved;
	private int totalNotApprove;
	private int total;
	
	private String academicSelectId;
	
	private List<AcademicYear> academicYearList;
	
	
	
	public List<AcademicYear> getAcademicYearList() {
		return academicYearList;
	}
	public void setAcademicYearList(List<AcademicYear> academicYearList) {
		this.academicYearList = academicYearList;
	}
	public PBPWorkType getpBPWorkType() {
		return pBPWorkType;
	}
	public void setpBPWorkType(PBPWorkType pBPWorkType) {
		this.pBPWorkType = pBPWorkType;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTotalApproved() {
		return totalApproved;
	}
	public void setTotalApproved(int totalApproved) {
		this.totalApproved = totalApproved;
	}
	public int getTotalNotApprove() {
		return totalNotApprove;
	}
	public void setTotalNotApprove(int totalNotApprove) {
		this.totalNotApprove = totalNotApprove;
	}
	
	
	public String getAcademicSelectId() {
		return academicSelectId;
	}
	public void setAcademicSelectId(String academicSelectId) {
		this.academicSelectId = academicSelectId;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public List<AcademicKPIUserMapping> getAcademicKPIUserMappingList() {
		return academicKPIUserMappingList;
	}
	public void setAcademicKPIUserMappingList(
			List<AcademicKPIUserMapping> academicKPIUserMappingList) {
		this.academicKPIUserMappingList = academicKPIUserMappingList;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public AcademicKPIUserMapping getAcademicKPIUserMapping() {
		return academicKPIUserMapping;
	}
	public void setAcademicKPIUserMapping(
			AcademicKPIUserMapping academicKPIUserMapping) {
		this.academicKPIUserMapping = academicKPIUserMapping;
	}
	public CommonsMultipartFile getFileData() {
		return fileData;
	}
	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}
	public List<AcademicKPIAttributeValue> getAcademicKPIAttributeValueList() {
		return academicKPIAttributeValueList;
	}
	public void setAcademicKPIAttributeValueList(
			List<AcademicKPIAttributeValue> academicKPIAttributeValueList) {
		this.academicKPIAttributeValueList = academicKPIAttributeValueList;
	}
	public String getReplyMessage() {
		return replyMessage;
	}
	public void setReplyMessage(String replyMessage) {
		this.replyMessage = replyMessage;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	
	
	 
}
