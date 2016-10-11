package com.buckwa.domain.pam;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class EstimateByUser extends BaseDomain  {
	
	private Long estimateByUserId;
	private Long estimateGroupId;
	private Long userId;
	private int type;
	private List<EstimateGroup> estimateGroupList;
	private List<EstimateUser> esimateUserList;
	private List<Person> personList;
	private Person person;
	
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public List<Person> getPersonList() {
		return personList;
	}
	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}
	public List<EstimateUser> getEsimateUserList() {
		return esimateUserList;
	}
	public void setEsimateUserList(List<EstimateUser> esimateUserList) {
		this.esimateUserList = esimateUserList;
	}
	public List<EstimateGroup> getEstimateGroupList() {
		return estimateGroupList;
	}
	public void setEstimateGroupList(List<EstimateGroup> estimateGroupList) {
		this.estimateGroupList = estimateGroupList;
	}
	public Long getEstimateByUserId() {
		return estimateByUserId;
	}
	public void setEstimateByUserId(Long estimateByUserId) {
		this.estimateByUserId = estimateByUserId;
	}
	public Long getEstimateGroupId() {
		return estimateGroupId;
	}
	public void setEstimateGroupId(Long estimateGroupId) {
		this.estimateGroupId = estimateGroupId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
