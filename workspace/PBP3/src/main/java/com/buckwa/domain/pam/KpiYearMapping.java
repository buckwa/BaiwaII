package com.buckwa.domain.pam;

import com.buckwa.domain.BaseDomain;
/*
@Author : Teerawoot Charoenporn(Tor)
@Create : Aug 6, 2012 01:00:13 AM
 */
public class KpiYearMapping extends BaseDomain  {
	
	private Long kpiYearMappingId;
	private Long kpiId;
	private Long categoryId;
	private String kpiName;
	private String kpiCode;
	private Long yearId;
	private Long semesterId;
	private Long score;
	private String status;
	private Boolean isSelected;
	private Boolean isDirty;
	
	private String employeeType;
	
	
	private String categoryIdStr;
	private String yearIdStr;
	
	private String yearName;
	private String categoryName;
	
	public String getYearName() {
		return yearName;
	}
	public void setYearName(String yearName) {
		this.yearName = yearName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Long getKpiId() {
		return kpiId;
	}
	public void setKpiId(Long kpiId) {
		this.kpiId = kpiId;
	}
	public Long getYearId() {
		return yearId;
	}
	public void setYearId(Long yearId) {
		this.yearId = yearId;
	}
	public Long getSemesterId() {
		return semesterId;
	}
	public void setSemesterId(Long semesterId) {
		this.semesterId = semesterId;
	}
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
	}
	public String getKpiName() {
		return kpiName;
	}
	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	public String getKpiCode() {
		return kpiCode;
	}
	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Boolean getIsDirty() {
		return isDirty;
	}
	public void setIsDirty(Boolean isDirty) {
		this.isDirty = isDirty;
	}
	
	
	
	public String getCategoryIdStr() {
		return categoryIdStr;
	}
	public void setCategoryIdStr(String categoryIdStr) {
		this.categoryIdStr = categoryIdStr;
	}
	public String getYearIdStr() {
		return yearIdStr;
	}
	public void setYearIdStr(String yearIdStr) {
		this.yearIdStr = yearIdStr;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getKpiYearMappingId() {
		return kpiYearMappingId;
	}
	public void setKpiYearMappingId(Long kpiYearMappingId) {
		this.kpiYearMappingId = kpiYearMappingId;
	}
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	@Override
	public String toString() {
		return "KpiYearMapping [kpiId=" + kpiId + ", kpiName=" + kpiName
				+ ", kpiCode=" + kpiCode + ", yearId=" + yearId
				+ ", semesterId=" + semesterId + ", score=" + score
				+ ", status=" + status + ", isSelected=" + isSelected
				+ ", isDirty=" + isDirty + "]";
	}
	
	
}
