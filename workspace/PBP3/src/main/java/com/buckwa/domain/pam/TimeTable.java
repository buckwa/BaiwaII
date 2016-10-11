package com.buckwa.domain.pam;

import java.util.Date;

import com.buckwa.domain.BaseDomain;

/**
 * 
 @Author : Kroekpong Sakulchai
 * @Create : Aug 10, 2012 1:51:44 AM
 * 
 **/

public class TimeTable extends BaseDomain {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3218483578899895937L;
	private Integer timeTableCd;
	private String facultyId;
	private String deptId;
	private Integer currId;
	private String currId2;
	private String subjectId;
	private Integer semester;
	private String year;
	private String level;
	private String program;
	private String lectOrPrac;
	private Integer priority;
	private String timeStamp;
	private String sectionCd;
	private String teacherId;
	private Integer sectionId;
	
	private String thaiName;
	private String engName;
	public Integer getTimeTableCd() {
		return timeTableCd;
	}
	public void setTimeTableCd(Integer timeTableCd) {
		this.timeTableCd = timeTableCd;
	}
	public String getFacultyId() {
		return facultyId;
	}
	public void setFacultyId(String facultyId) {
		this.facultyId = facultyId;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public Integer getCurrId() {
		return currId;
	}
	public void setCurrId(Integer currId) {
		this.currId = currId;
	}
	public String getThaiName() {
		return thaiName;
	}
	public void setThaiName(String thaiName) {
		this.thaiName = thaiName;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public String getCurrId2() {
		return currId2;
	}
	public void setCurrId2(String currId2) {
		this.currId2 = currId2;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public Integer getSemester() {
		return semester;
	}
	public void setSemester(Integer semester) {
		this.semester = semester;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getLectOrPrac() {
		return lectOrPrac;
	}
	public void setLectOrPrac(String lectOrPrac) {
		this.lectOrPrac = lectOrPrac;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getSectionCd() {
		return sectionCd;
	}
	public void setSectionCd(String sectionCd) {
		this.sectionCd = sectionCd;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public Integer getSectionId() {
		return sectionId;
	}
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}
	
	
	
	
	
}
