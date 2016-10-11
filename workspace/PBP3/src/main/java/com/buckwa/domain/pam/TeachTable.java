package com.buckwa.domain.pam;

import java.sql.Time;
import java.util.Date;

import com.buckwa.domain.BaseDomain;

public class TeachTable extends BaseDomain  {
	
	private Long teachTableId;
	private Long subjectId;
	private Long semesterId;
	private Long yearId;
	private String facultyId;
	private String deptId;
	private int currId;
	private String currId2;
	private String classRoom;
	private String program;
	private int section;
	private int teachDay;
	private Time teachTime;
	private Time teachTime2;
	private String subjectName;
	private Date startDate; 
	private Date endDate;
	private int day;
	private int month;
	private int year;
	private String subjectCode;
	private String timeTeach;
	private String room;
	private String building;
	private String roomNo;
	private String buildingNo;
	private int degree;
	
	
	
	public int getDegree() {
		return degree;
	}
	public void setDegree(int degree) {
		this.degree = degree;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getBuildingNo() {
		return buildingNo;
	}
	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getTimeTeach() {
		return timeTeach;
	}
	public void setTimeTeach(String timeTeach) {
		this.timeTeach = timeTeach;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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
	public int getCurrId() {
		return currId;
	}
	public void setCurrId(int currId) {
		this.currId = currId;
	}
	public String getCurrId2() {
		return currId2;
	}
	public void setCurrId2(String currId2) {
		this.currId2 = currId2;
	}
	public String getClassRoom() {
		return classRoom;
	}
	public void setClassRoom(String classRoom) {
		this.classRoom = classRoom;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public int getSection() {
		return section;
	}
	public void setSection(int section) {
		this.section = section;
	}
	public Long getTeachTableId() {
		return teachTableId;
	}
	public void setTeachTableId(Long teachTableId) {
		this.teachTableId = teachTableId;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public Long getSemesterId() {
		return semesterId;
	}
	public void setSemesterId(Long semesterId) {
		this.semesterId = semesterId;
	}
	public Long getYearId() {
		return yearId;
	}
	public void setYearId(Long yearId) {
		this.yearId = yearId;
	}
	public int getTeachDay() {
		return teachDay;
	}
	public void setTeachDay(int teachDay) {
		this.teachDay = teachDay;
	}
	public Time getTeachTime() {
		return teachTime;
	}
	public void setTeachTime(Time teachTime) {
		this.teachTime = teachTime;
	}
	public Time getTeachTime2() {
		return teachTime2;
	}
	public void setTeachTime2(Time teachTime2) {
		this.teachTime2 = teachTime2;
	}
	
	
	
}
