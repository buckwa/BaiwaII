package com.buckwa.domain.pbp;

import java.sql.Timestamp;
import java.util.List;

import com.buckwa.domain.BaseDomain;
import com.buckwa.domain.pam.Semester;
import com.buckwa.util.BuckWaDateUtils;

public class AcademicYear extends BaseDomain{
	
	private Long academicYearId;
	private String name;
	private String startDateStr;
	private String endDateStr;
	
	private Timestamp startDate;
	private Timestamp endDate;
	
	private String startDateThaiShort;
	private String endDateThaiShort;
	
	private String canEdit;
	
	private List<Semester> semesterList;
 
	
	
	public List<Semester> getSemesterList() {
		return semesterList;
	}
	public void setSemesterList(List<Semester> semesterList) {
		this.semesterList = semesterList;
	}
	public String getCanEdit() {
		return canEdit;
	}
	public void setCanEdit(String canEdit) {
		this.canEdit = canEdit;
	}
	public String getStartDateThaiShort() {
		if(this.startDate!=null){
			return BuckWaDateUtils.getShortThaiMonthFromDate(this.startDate);
		}else{
			return "";
		}
		 
	}
	public void setStartDateThaiShort(String startDateThaiShort) {
		this.startDateThaiShort = startDateThaiShort;
	}
	public String getEndDateThaiShort() {
		if(this.endDate!=null){
			return BuckWaDateUtils.getShortThaiMonthFromDate(this.endDate);
		}else{
			return "";
		}
	}
	public void setEndDateThaiShort(String endDateThaiShort) {
		this.endDateThaiShort = endDateThaiShort;
	}
	
	
	public Long getAcademicYearId() {
		return academicYearId;
	}
	public void setAcademicYearId(Long academicYearId) {
		this.academicYearId = academicYearId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartDateStr() {
		return startDateStr;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
 
	
	

}
