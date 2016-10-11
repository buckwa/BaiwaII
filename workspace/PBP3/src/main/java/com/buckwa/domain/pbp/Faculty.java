package com.buckwa.domain.pbp;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class Faculty extends BaseDomain{
	
	 
	private Long facultyId;
	private List<Department> departmentList;
	private Department department;
	private String academicYear;
	private String deanCitizenId;
	
	private AcademicPerson dean;
	private List<AcademicPerson> deanList;
	
	 
	public AcademicPerson getDean() {
		return dean;
	}
	public void setDean(AcademicPerson dean) {
		this.dean = dean;
	}
	public List<AcademicPerson> getDeanList() {
		return deanList;
	}
	public void setDeanList(List<AcademicPerson> deanList) {
		this.deanList = deanList;
	}
	public String getDeanCitizenId() {
		return deanCitizenId;
	}
	public void setDeanCitizenId(String deanCitizenId) {
		this.deanCitizenId = deanCitizenId;
	}
	public Long getFacultyId() {
		return facultyId;
	}
	public void setFacultyId(Long facultyId) {
		this.facultyId = facultyId;
	}
	public List<Department> getDepartmentList() {
		return departmentList;
	}
	public void setDepartmentList(List<Department> departmentList) {
		this.departmentList = departmentList;
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
	
	
	
	
	

}
