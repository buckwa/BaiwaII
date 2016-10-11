package com.buckwa.domain.pam;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.buckwa.domain.BaseDomain;

public class EstimateUser extends BaseDomain  {
	
	private Long estimateUserId;
	private Long estimateGroupId;
	private Long userId;
	private Person person;
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Long getEstimateUserId() {
		return estimateUserId;
	} 
	public void setEstimateUserId(Long estimateUserId) {
		this.estimateUserId = estimateUserId;
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
