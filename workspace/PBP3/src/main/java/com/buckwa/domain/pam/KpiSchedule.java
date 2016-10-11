package com.buckwa.domain.pam;

import java.util.Date;

import com.buckwa.domain.BaseDomain;

/*
@Author : Taechapon Himarat (Su)
@Create : Sep 30, 2012 1:01:45 AM
 */
public class KpiSchedule extends BaseDomain {
	
	private Long kpiScheduleId;
	private String yearName;
	private Date teacherUploadStartDate1;
	private Date teacherUploadEndDate1;
	private Date teacherEvaluateStartDate1;
	private Date teacherEvaluateEndDate1;
	private Date teacherUploadStartDate2;
	private Date teacherUploadEndDate2;
	private Date teacherEvaluateStartDate2;
	private Date teacherEvaluateEndDate2;
	private Date staffUploadStartDate;
	private Date staffUploadEndDate;
	private Date staffEvaluateStartDate;
	private Date staffEvaluateEndDate;
	
	// Form
	private String teacherUploadStartDate1Str;
	private String teacherUploadEndDate1Str;
	private String teacherEvaluateStartDate1Str;
	private String teacherEvaluateEndDate1Str;
	private String teacherUploadStartDate2Str;
	private String teacherUploadEndDate2Str;
	private String teacherEvaluateStartDate2Str;
	private String teacherEvaluateEndDate2Str;
	private String staffUploadStartDateStr;
	private String staffUploadEndDateStr;
	private String staffEvaluateStartDateStr;
	private String staffEvaluateEndDateStr;
	
	public Long getKpiScheduleId() {
		return kpiScheduleId;
	}
	
	public void setKpiScheduleId(Long kpiScheduleId) {
		this.kpiScheduleId = kpiScheduleId;
	}

	public String getYearName() {
		return yearName;
	}

	public void setYearName(String yearName) {
		this.yearName = yearName;
	}

	public Date getTeacherUploadStartDate1() {
		return teacherUploadStartDate1;
	}

	public void setTeacherUploadStartDate1(Date teacherUploadStartDate1) {
		this.teacherUploadStartDate1 = teacherUploadStartDate1;
	}

	public Date getTeacherUploadEndDate1() {
		return teacherUploadEndDate1;
	}

	public void setTeacherUploadEndDate1(Date teacherUploadEndDate1) {
		this.teacherUploadEndDate1 = teacherUploadEndDate1;
	}

	public Date getTeacherEvaluateStartDate1() {
		return teacherEvaluateStartDate1;
	}

	public void setTeacherEvaluateStartDate1(Date teacherEvaluateStartDate1) {
		this.teacherEvaluateStartDate1 = teacherEvaluateStartDate1;
	}

	public Date getTeacherEvaluateEndDate1() {
		return teacherEvaluateEndDate1;
	}

	public void setTeacherEvaluateEndDate1(Date teacherEvaluateEndDate1) {
		this.teacherEvaluateEndDate1 = teacherEvaluateEndDate1;
	}

	public Date getTeacherUploadStartDate2() {
		return teacherUploadStartDate2;
	}

	public void setTeacherUploadStartDate2(Date teacherUploadStartDate2) {
		this.teacherUploadStartDate2 = teacherUploadStartDate2;
	}

	public Date getTeacherUploadEndDate2() {
		return teacherUploadEndDate2;
	}

	public void setTeacherUploadEndDate2(Date teacherUploadEndDate2) {
		this.teacherUploadEndDate2 = teacherUploadEndDate2;
	}

	public Date getTeacherEvaluateStartDate2() {
		return teacherEvaluateStartDate2;
	}

	public void setTeacherEvaluateStartDate2(Date teacherEvaluateStartDate2) {
		this.teacherEvaluateStartDate2 = teacherEvaluateStartDate2;
	}

	public Date getTeacherEvaluateEndDate2() {
		return teacherEvaluateEndDate2;
	}

	public void setTeacherEvaluateEndDate2(Date teacherEvaluateEndDate2) {
		this.teacherEvaluateEndDate2 = teacherEvaluateEndDate2;
	}

	public Date getStaffUploadStartDate() {
		return staffUploadStartDate;
	}

	public void setStaffUploadStartDate(Date staffUploadStartDate) {
		this.staffUploadStartDate = staffUploadStartDate;
	}

	public Date getStaffUploadEndDate() {
		return staffUploadEndDate;
	}

	public void setStaffUploadEndDate(Date staffUploadEndDate) {
		this.staffUploadEndDate = staffUploadEndDate;
	}

	public Date getStaffEvaluateStartDate() {
		return staffEvaluateStartDate;
	}

	public void setStaffEvaluateStartDate(Date staffEvaluateStartDate) {
		this.staffEvaluateStartDate = staffEvaluateStartDate;
	}

	public Date getStaffEvaluateEndDate() {
		return staffEvaluateEndDate;
	}

	public void setStaffEvaluateEndDate(Date staffEvaluateEndDate) {
		this.staffEvaluateEndDate = staffEvaluateEndDate;
	}

	public String getTeacherUploadStartDate1Str() {
		return teacherUploadStartDate1Str;
	}

	public void setTeacherUploadStartDate1Str(String teacherUploadStartDate1Str) {
		this.teacherUploadStartDate1Str = teacherUploadStartDate1Str;
	}

	public String getTeacherUploadEndDate1Str() {
		return teacherUploadEndDate1Str;
	}

	public void setTeacherUploadEndDate1Str(String teacherUploadEndDate1Str) {
		this.teacherUploadEndDate1Str = teacherUploadEndDate1Str;
	}

	public String getTeacherEvaluateStartDate1Str() {
		return teacherEvaluateStartDate1Str;
	}

	public void setTeacherEvaluateStartDate1Str(String teacherEvaluateStartDate1Str) {
		this.teacherEvaluateStartDate1Str = teacherEvaluateStartDate1Str;
	}

	public String getTeacherEvaluateEndDate1Str() {
		return teacherEvaluateEndDate1Str;
	}

	public void setTeacherEvaluateEndDate1Str(String teacherEvaluateEndDate1Str) {
		this.teacherEvaluateEndDate1Str = teacherEvaluateEndDate1Str;
	}

	public String getTeacherUploadStartDate2Str() {
		return teacherUploadStartDate2Str;
	}

	public void setTeacherUploadStartDate2Str(String teacherUploadStartDate2Str) {
		this.teacherUploadStartDate2Str = teacherUploadStartDate2Str;
	}

	public String getTeacherUploadEndDate2Str() {
		return teacherUploadEndDate2Str;
	}

	public void setTeacherUploadEndDate2Str(String teacherUploadEndDate2Str) {
		this.teacherUploadEndDate2Str = teacherUploadEndDate2Str;
	}

	public String getTeacherEvaluateStartDate2Str() {
		return teacherEvaluateStartDate2Str;
	}

	public void setTeacherEvaluateStartDate2Str(String teacherEvaluateStartDate2Str) {
		this.teacherEvaluateStartDate2Str = teacherEvaluateStartDate2Str;
	}

	public String getTeacherEvaluateEndDate2Str() {
		return teacherEvaluateEndDate2Str;
	}

	public void setTeacherEvaluateEndDate2Str(String teacherEvaluateEndDate2Str) {
		this.teacherEvaluateEndDate2Str = teacherEvaluateEndDate2Str;
	}

	public String getStaffUploadStartDateStr() {
		return staffUploadStartDateStr;
	}

	public void setStaffUploadStartDateStr(String staffUploadStartDateStr) {
		this.staffUploadStartDateStr = staffUploadStartDateStr;
	}

	public String getStaffUploadEndDateStr() {
		return staffUploadEndDateStr;
	}

	public void setStaffUploadEndDateStr(String staffUploadEndDateStr) {
		this.staffUploadEndDateStr = staffUploadEndDateStr;
	}

	public String getStaffEvaluateStartDateStr() {
		return staffEvaluateStartDateStr;
	}

	public void setStaffEvaluateStartDateStr(String staffEvaluateStartDateStr) {
		this.staffEvaluateStartDateStr = staffEvaluateStartDateStr;
	}

	public String getStaffEvaluateEndDateStr() {
		return staffEvaluateEndDateStr;
	}

	public void setStaffEvaluateEndDateStr(String staffEvaluateEndDateStr) {
		this.staffEvaluateEndDateStr = staffEvaluateEndDateStr;
	}
	
}
