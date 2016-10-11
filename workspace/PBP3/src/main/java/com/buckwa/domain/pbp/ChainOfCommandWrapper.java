package com.buckwa.domain.pbp;

import java.util.List;

import com.buckwa.domain.BaseDomain;
import com.buckwa.domain.admin.User;

public class ChainOfCommandWrapper  extends BaseDomain{
	
	private List<Faculty> facultyList;
	private String academicYear;
	
	private Faculty faculty;
	private Department department;
	
	private User user;
	
	private Long facultyId;
	private String oldDeanUserName;
	
	private Long departmentId;
	private String oldHeadUserName;	
	
	
	private AcademicPerson dean;
	private AcademicPerson head;
	private AcademicPerson president;
	
	
	private String oldPresidentUserName;	
	
	
	
	
	public String getOldPresidentUserName() {
		return oldPresidentUserName;
	}
	public void setOldPresidentUserName(String oldPresidentUserName) {
		this.oldPresidentUserName = oldPresidentUserName;
	}
	public AcademicPerson getPresident() {
		return president;
	}
	public void setPresident(AcademicPerson president) {
		this.president = president;
	}
	public String getOldDeanUserName() {
		return oldDeanUserName;
	}
	public void setOldDeanUserName(String oldDeanUserName) {
		this.oldDeanUserName = oldDeanUserName;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public String getOldHeadUserName() {
		return oldHeadUserName;
	}
	public void setOldHeadUserName(String oldHeadUserName) {
		this.oldHeadUserName = oldHeadUserName;
	}
	public Faculty getFaculty() {
		return faculty;
	}
	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
	public List<Faculty> getFacultyList() {
		return facultyList;
	}
	public void setFacultyList(List<Faculty> facultyList) {
		this.facultyList = facultyList;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public AcademicPerson getDean() {
		return dean;
	}
	public void setDean(AcademicPerson dean) {
		this.dean = dean;
	}
	public AcademicPerson getHead() {
		return head;
	}
	public void setHead(AcademicPerson head) {
		this.head = head;
	}
	public Long getFacultyId() {
		return facultyId;
	}
	public void setFacultyId(Long facultyId) {
		this.facultyId = facultyId;
	}
    
    
    
    
	

}
