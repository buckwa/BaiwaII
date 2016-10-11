package com.buckwa.domain.pbp;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.buckwa.domain.BaseDomain;
import com.buckwa.util.BuckWaDateUtils;

public class Department extends BaseDomain{
	
	private Long departmentId;
	private Long facultyId;
	private String academicYear;
	private String headCitizenId;
	private String facultyCode;
	
	private Faculty faculty;
	
	private List<AcademicPerson> headList;
	
	private List<AcademicPerson> academicPersonList ;
	
	private AcademicPerson head;
	
	public String getFacultyCode() {
		return facultyCode;
	}
	public void setFacultyCode(String facultyCode) {
		this.facultyCode = facultyCode;
	}
	private List<PBPWorkType> pBPWorkTypeList;
	
	private String radarURL;
	
	public Faculty getFaculty() {
		return faculty;
	}
	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
	private List<PBPWorkType> totalMarkByWorkTypeList;
	
	public List<PBPWorkType> getTotalMarkByWorkTypeList() {
		return totalMarkByWorkTypeList;
	}
	public void setTotalMarkByWorkTypeList(List<PBPWorkType> totalMarkByWorkTypeList) {
		this.totalMarkByWorkTypeList = totalMarkByWorkTypeList;
	}
	public AcademicPerson getHead() {
		return head;
	}
	public void setHead(AcademicPerson head) {
		this.head = head;
	}
	public List<AcademicPerson> getAcademicPersonList() {
		return academicPersonList;
	}
	public void setAcademicPersonList(List<AcademicPerson> academicPersonList) {
		this.academicPersonList = academicPersonList;
	}
	public List<AcademicPerson> getHeadList() {
		return headList;
	}
	public void setHeadList(List<AcademicPerson> headList) {
		this.headList = headList;
	}
	public String getHeadCitizenId() {
		return headCitizenId;
	}
	public void setHeadCitizenId(String headCitizenId) {
		this.headCitizenId = headCitizenId;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public Long getFacultyId() {
		return facultyId;
	}
	public void setFacultyId(Long facultyId) {
		this.facultyId = facultyId;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public List<PBPWorkType> getpBPWorkTypeList() {
		return pBPWorkTypeList;
	}
	public void setpBPWorkTypeList(List<PBPWorkType> pBPWorkTypeList) {
		this.pBPWorkTypeList = pBPWorkTypeList;
	}
	public String getRadarURL() {
		return radarURL;
	}
	public void setRadarURL(String radarURL) {
		this.radarURL = radarURL;
	}
	
	
 
}
