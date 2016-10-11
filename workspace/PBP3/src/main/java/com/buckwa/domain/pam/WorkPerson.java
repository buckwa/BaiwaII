package com.buckwa.domain.pam;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.buckwa.domain.BaseDomain;

public class WorkPerson extends BaseDomain  {
	
	private Long workPersonId;
	private String name;
	private Long userid;
	private int year;
	private String[] kpi;
	private List<WorkPersonKpi> workPersonKpiList;
	private boolean isNameAlready;
	private Long workTemplateId;
	private List<String> labels;
	private List<Long> units;
	private List<String> values;
	private List<WorkPersonAttr> workPersonAttrList;
	
	private CommonsMultipartFile fileData;
	private List<FileLocation> fileLocationList;
	
	private Long workPersonAttrMappingId;
	private List<WorkTemplateAttr> workTemplateAttrMappingList;
	private List<WorkPersonAttr> workPersonAttrMappingList;

    private int isClassRoom;
	private Long yearId;
	private Long semesterId;
	private String yearStr;
	private String semesterStr;
	
	
	
	
	public int getIsClassRoom() {
		return isClassRoom;
	}
	public void setIsClassRoom(int isClassRoom) {
		this.isClassRoom = isClassRoom;
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
	public String getYearStr() {
		return yearStr;
	}
	public void setYearStr(String yearStr) {
		this.yearStr = yearStr;
	}
	public String getSemesterStr() {
		return semesterStr;
	}
	public void setSemesterStr(String semesterStr) {
		this.semesterStr = semesterStr;
	}
	public List<WorkPersonAttr> getWorkPersonAttrList() {
		return workPersonAttrList;
	}
	public void setWorkPersonAttrList(List<WorkPersonAttr> workPersonAttrList) {
		this.workPersonAttrList = workPersonAttrList;
	}
	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public List<Long> getUnits() {
		return units;
	}
	public void setUnits(List<Long> units) {
		this.units = units;
	}
	public Long getWorkTemplateId() {
		return workTemplateId;
	}
	public void setWorkTemplateId(Long workTemplateId) {
		this.workTemplateId = workTemplateId;
	}
	public boolean isNameAlready() {
		return isNameAlready;
	}
	public void setNameAlready(boolean isNameAlready) {
		this.isNameAlready = isNameAlready;
	}
	public String[] getKpi() {
		return kpi;
	}
	public void setKpi(String[] kpi) {
		this.kpi = kpi;
	}
	
	public Long getWorkPersonId() {
		return workPersonId;
	}
	public void setWorkPersonId(Long workPersonId) {
		this.workPersonId = workPersonId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<WorkPersonKpi> getWorkPersonKpiList() {
		return workPersonKpiList;
	}
	public void setWorkPersonKpiList(List<WorkPersonKpi> workPersonKpiList) {
		this.workPersonKpiList = workPersonKpiList;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public CommonsMultipartFile getFileData() {
		return fileData;
	}
	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}
	public List<FileLocation> getFileLocationList() {
		return fileLocationList;
	}
	public void setFileLocationList(List<FileLocation> fileLocationList) {
		this.fileLocationList = fileLocationList;
	}
	public Long getWorkPersonAttrMappingId() {
		return workPersonAttrMappingId;
	}
	public void setWorkPersonAttrMappingId(Long workPersonAttrMappingId) {
		this.workPersonAttrMappingId = workPersonAttrMappingId;
	}
	public List<WorkTemplateAttr> getWorkTemplateAttrMappingList() {
		return workTemplateAttrMappingList;
	}
	public void setWorkTemplateAttrMappingList(List<WorkTemplateAttr> workTemplateAttrMappingList) {
		this.workTemplateAttrMappingList = workTemplateAttrMappingList;
	}
	public List<WorkPersonAttr> getWorkPersonAttrMappingList() {
		return workPersonAttrMappingList;
	}
	public void setWorkPersonAttrMappingList(List<WorkPersonAttr> workPersonAttrMappingList) {
		this.workPersonAttrMappingList = workPersonAttrMappingList;
	}
	
}
