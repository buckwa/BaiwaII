package com.buckwa.domain.pam;
/*
@Author : Taechapon Himarat (Su)
@Create : Oct 18, 2012 10:47:47 PM
 */
public class ReportPersonSummary {
	
	private String code;
	private String employeeType;
	private Integer teacherTotal;
	private Integer teacherWorking;
	private Integer teacherLeave;
	private Integer research;
	private Integer support;
	private Integer personTotal;
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEmployeeType() {
		return employeeType;
	}
	
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public Integer getTeacherTotal() {
		return teacherTotal;
	}

	public void setTeacherTotal(Integer teacherTotal) {
		this.teacherTotal = teacherTotal;
	}

	public Integer getTeacherWorking() {
		return teacherWorking;
	}

	public void setTeacherWorking(Integer teacherWorking) {
		this.teacherWorking = teacherWorking;
	}

	public Integer getTeacherLeave() {
		return teacherLeave;
	}

	public void setTeacherLeave(Integer teacherLeave) {
		this.teacherLeave = teacherLeave;
	}

	public Integer getResearch() {
		return research;
	}

	public void setResearch(Integer research) {
		this.research = research;
	}

	public Integer getSupport() {
		return support;
	}

	public void setSupport(Integer support) {
		this.support = support;
	}

	public Integer getPersonTotal() {
		return personTotal;
	}

	public void setPersonTotal(Integer personTotal) {
		this.personTotal = personTotal;
	}
	
}
