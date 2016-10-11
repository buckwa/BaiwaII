package com.buckwa.domain.pam;

import com.buckwa.domain.BaseDomain;

public class Subject extends BaseDomain  {
	
	private Long subjectId;
	private String nameTh;
	private String nameEn;
	private int credit;
	private int lecHr;
	private int prcHr;
	private int selfHr;
	private String details;
	private String teachName;
	private Long userId;
	private Long teacherId;
	private Long yearId;
	private Long semesterId;
	private String year;
	private String semester;
	private String code;
	private int degreeType;
	private String degree;
	
	
	public int getDegreeType() {
		return degreeType;
	}
	public void setDegreeType(int degreeType) {
		this.degreeType = degreeType;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public Long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public String getNameTh() {
		return nameTh;
	}
	public void setNameTh(String nameTh) {
		this.nameTh = nameTh;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public int getLecHr() {
		return lecHr;
	}
	public void setLecHr(int lecHr) {
		this.lecHr = lecHr;
	}
	public int getPrcHr() {
		return prcHr;
	}
	public void setPrcHr(int prcHr) {
		this.prcHr = prcHr;
	}
	
	public int getSelfHr() {
		return selfHr;
	}
	public void setSelfHr(int selfHr) {
		this.selfHr = selfHr;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getTeachName() {
		return teachName;
	}
	public void setTeachName(String teachName) {
		this.teachName = teachName;
	}
	
	
	
}
