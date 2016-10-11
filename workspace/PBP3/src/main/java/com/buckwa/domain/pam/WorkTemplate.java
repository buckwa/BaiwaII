package com.buckwa.domain.pam;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class WorkTemplate extends BaseDomain  {
	
	private Long workTemplateId;
	private String name;
	private String employeeType;
	private Integer year;
	private boolean isNameAlready;
	private List<WorkTemplateAttr> workTemplateAttrList;
	private String workAttrIdDel;
	private List<WorkPerson> workPersonList;
	private String[] kpi;
	private List<WorkTemplateKpi> workTemplateKpiList;
	private int groupId;
	private int isFile;
	private int isClassRoom;
	private String groupName;
	
	
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getIsClassRoom() {
		return isClassRoom;
	}
	public void setIsClassRoom(int isClassRoom) {
		this.isClassRoom = isClassRoom;
	}
	public int getIsFile() {
		return isFile;
	}
	public void setIsFile(int isFile) {
		this.isFile = isFile;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String[] getKpi() {
		return kpi;
	}
	public void setKpi(String[] kpi) {
		this.kpi = kpi;
	}
	
	public List<WorkTemplateKpi> getWorkTemplateKpiList() {
		return workTemplateKpiList;
	}
	public void setWorkTemplateKpiList(List<WorkTemplateKpi> workTemplateKpiList) {
		this.workTemplateKpiList = workTemplateKpiList;
	}
	public String getWorkAttrIdDel() {
		return workAttrIdDel;
	}
	public void setWorkAttrIdDel(String workAttrIdDel) {
		this.workAttrIdDel = workAttrIdDel;
	}
	public List<WorkTemplateAttr> getWorkTemplateAttrList() {
		return workTemplateAttrList;
	}
	public void setWorkTemplateAttrList(List<WorkTemplateAttr> workTemplateAttrList) {
		this.workTemplateAttrList = workTemplateAttrList;
	}
	public Long getWorkTemplateId() {
		return workTemplateId;
	}
	public void setWorkTemplateId(Long workTemplateId) {
		this.workTemplateId = workTemplateId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public boolean isNameAlready() {
		return isNameAlready;
	}
	public void setNameAlready(boolean isNameAlready) {
		this.isNameAlready = isNameAlready;
	}
	public List<WorkPerson> getWorkPersonList() {
		return workPersonList;
	}
	public void setWorkPersonList(List<WorkPerson> workPersonList) {
		this.workPersonList = workPersonList;
	}
	
	
	
}
