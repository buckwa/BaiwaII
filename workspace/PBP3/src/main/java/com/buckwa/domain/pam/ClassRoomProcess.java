package com.buckwa.domain.pam;

import com.buckwa.domain.BaseDomain;

public class ClassRoomProcess extends BaseDomain  {
	
	private Long classRoomProcessId;
	private Long yearId;
	private Long semesterId;
	private int flag;
	private String createDateStr;
	private String updateDateStr;
	private String year;
	private String semester;
	private String user1List;
	private String user2List;
	
	
	public String getUser2List() {
		return user2List;
	}
	public void setUser2List(String user2List) {
		this.user2List = user2List;
	}
	public String getUser1List() {
		return user1List;
	}
	public void setUser1List(String user1List) {
		this.user1List = user1List;
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
	public String getCreateDateStr() {
		return createDateStr;
	}
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
	public String getUpdateDateStr() {
		return updateDateStr;
	}
	public void setUpdateDateStr(String updateDateStr) {
		this.updateDateStr = updateDateStr;
	}
	public Long getClassRoomProcessId() {
		return classRoomProcessId;
	}
	public void setClassRoomProcessId(Long classRoomProcessId) {
		this.classRoomProcessId = classRoomProcessId;
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
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
}
