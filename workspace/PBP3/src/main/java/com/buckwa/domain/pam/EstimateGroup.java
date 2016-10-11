package com.buckwa.domain.pam;

import java.util.List;

import com.buckwa.domain.BaseDomain;
import com.buckwa.domain.common.LovDetail;
import com.buckwa.util.BuckWaDateUtils;

public class EstimateGroup extends BaseDomain  {
	
	private Long estimateGroupId;
	private String name;
	private List<EstimateByUser> estimateByUserList;
	private List<EstimateByUser> estimateByUserAdditionalList;
	private List<EstimateUser> estimateUserList;
	private List<Person> personByUserList;
	private List<Person> personUserList;
	private int actionPage;
	private boolean isNameAlready;
	private String createStr;
	private List<LovDetail> facultyList ;
	private String faculty;
	
	
	public List<EstimateByUser> getEstimateByUserAdditionalList() {
		return estimateByUserAdditionalList;
	}

	public void setEstimateByUserAdditionalList(
			List<EstimateByUser> estimateByUserAdditionalList) {
		this.estimateByUserAdditionalList = estimateByUserAdditionalList;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public List<LovDetail> getFacultyList() {
		return facultyList;
	}

	public void setFacultyList(List<LovDetail> facultyList) {
		this.facultyList = facultyList;
	}

	public String getCreateStr() {
		return  BuckWaDateUtils.get_current_ddMMMyyyyhhmmss_thai_from_date(createDate);
	}
	
	public List<Person> getPersonByUserList() {
		return personByUserList;
	}
	public void setPersonByUserList(List<Person> personByUserList) {
		this.personByUserList = personByUserList;
	}
	public List<Person> getPersonUserList() {
		return personUserList;
	}
	public void setPersonUserList(List<Person> personUserList) {
		this.personUserList = personUserList;
	}
	public boolean isNameAlready() {
		return isNameAlready;
	}
	public void setNameAlready(boolean isNameAlready) {
		this.isNameAlready = isNameAlready;
	}
	public int getActionPage() {
		return actionPage;
	}
	public void setActionPage(int actionPage) {
		this.actionPage = actionPage;
	}
	public List<EstimateByUser> getEstimateByUserList() {
		return estimateByUserList;
	}
	public void setEstimateByUserList(List<EstimateByUser> estimateByUserList) {
		this.estimateByUserList = estimateByUserList;
	}
	public List<EstimateUser> getEstimateUserList() {
		return estimateUserList;
	}
	public void setEstimateUserList(List<EstimateUser> estimateUserList) {
		this.estimateUserList = estimateUserList;
	}
	public Long getEstimateGroupId() {
		return estimateGroupId;
	}
	public void setEstimateGroupId(Long estimateGroupId) {
		this.estimateGroupId = estimateGroupId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
