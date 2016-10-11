package com.buckwa.domain.pam;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class TeacherTeach extends BaseDomain  {
	
	private Long teacherTeachId;
	private Long yearId;
	private Long semesterId;
	private Long subjectId;
	private Long teacherId;
	private String subjectCode;
	private List<TeachTable> teachTableList;
	
	
	
	
	public List<TeachTable> getTeachTableList() {
		return teachTableList;
	}
	public void setTeachTableList(List<TeachTable> teachTableList) {
		this.teachTableList = teachTableList;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public Long getTeacherTeachId() {
		return teacherTeachId;
	}
	public void setTeacherTeachId(Long teacherTeachId) {
		this.teacherTeachId = teacherTeachId;
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
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public Long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	
	
}
