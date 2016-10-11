package com.buckwa.domain.pam;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.buckwa.domain.BaseDomain;

public class ImportData extends BaseDomain  {
	
	private Long importId;
	private String importDesc;	
	private Timestamp importDate;
	private Long yearId;
	private Long semesterId;
	private List<PersonMapping> personMappingList;
	private Long classRoomProcessId;
	private List<Person> user1List;
	private List<Person> user2List;
	private CommonsMultipartFile fileDataTeacherTeach;
	private CommonsMultipartFile fileDataSubject;
	private CommonsMultipartFile fileDataTeacher;
	private CommonsMultipartFile fileDataTeachTable;
	private CommonsMultipartFile fileData;
	private CommonsMultipartFile fileDataTeacherTeachMaster;
	private CommonsMultipartFile fileDataSubjectMaster;
	private CommonsMultipartFile fileDataTeachTableMaster;
	
	
	
	public CommonsMultipartFile getFileDataTeacherTeachMaster() {
		return fileDataTeacherTeachMaster;
	}
	public void setFileDataTeacherTeachMaster(
			CommonsMultipartFile fileDataTeacherTeachMaster) {
		this.fileDataTeacherTeachMaster = fileDataTeacherTeachMaster;
	}
	public CommonsMultipartFile getFileDataSubjectMaster() {
		return fileDataSubjectMaster;
	}
	public void setFileDataSubjectMaster(CommonsMultipartFile fileDataSubjectMaster) {
		this.fileDataSubjectMaster = fileDataSubjectMaster;
	}
	public CommonsMultipartFile getFileDataTeachTableMaster() {
		return fileDataTeachTableMaster;
	}
	public void setFileDataTeachTableMaster(
			CommonsMultipartFile fileDataTeachTableMaster) {
		this.fileDataTeachTableMaster = fileDataTeachTableMaster;
	}
	public CommonsMultipartFile getFileData() {
		return fileData;
	}
	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}
	public CommonsMultipartFile getFileDataTeacherTeach() {
		return fileDataTeacherTeach;
	}
	public void setFileDataTeacherTeach(CommonsMultipartFile fileDataTeacherTeach) {
		this.fileDataTeacherTeach = fileDataTeacherTeach;
	}
	public CommonsMultipartFile getFileDataSubject() {
		return fileDataSubject;
	}
	public void setFileDataSubject(CommonsMultipartFile fileDataSubject) {
		this.fileDataSubject = fileDataSubject;
	}
	public CommonsMultipartFile getFileDataTeacher() {
		return fileDataTeacher;
	}
	public void setFileDataTeacher(CommonsMultipartFile fileDataTeacher) {
		this.fileDataTeacher = fileDataTeacher;
	}
	public CommonsMultipartFile getFileDataTeachTable() {
		return fileDataTeachTable;
	}
	public void setFileDataTeachTable(CommonsMultipartFile fileDataTeachTable) {
		this.fileDataTeachTable = fileDataTeachTable;
	}
	public List<Person> getUser1List() {
		return user1List;
	}
	public void setUser1List(List<Person> user1List) {
		this.user1List = user1List;
	}
	public List<Person> getUser2List() {
		return user2List;
	}
	public void setUser2List(List<Person> user2List) {
		this.user2List = user2List;
	}
	public Long getClassRoomProcessId() {
		return classRoomProcessId;
	}
	public void setClassRoomProcessId(Long classRoomProcessId) {
		this.classRoomProcessId = classRoomProcessId;
	}
	public List<PersonMapping> getPersonMappingList() {
		return personMappingList;
	}
	public void setPersonMappingList(List<PersonMapping> personMappingList) {
		this.personMappingList = personMappingList;
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
	
	
	public Long getImportId() {
		return importId;
	}
	public void setImportId(Long importId) {
		this.importId = importId;
	}
	public String getImportDesc() {
		return importDesc;
	}
	public void setImportDesc(String importDesc) {
		this.importDesc = importDesc;
	}
	public Timestamp getImportDate() {
		return importDate;
	}
	public void setImportDate(Timestamp importDate) {
		this.importDate = importDate;
	}

}
