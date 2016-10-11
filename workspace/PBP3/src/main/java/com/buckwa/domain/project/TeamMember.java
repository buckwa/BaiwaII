package com.buckwa.domain.project;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class TeamMember extends BaseDomain{
	
	private Long teamMemberId;
	private String role;
	private String startDate;
	private String endDate;
	private String imagePath;
	private String telNo;
	
	private List<Artiface> artifaceList;

	public Long getTeamMemberId() {
		return teamMemberId;
	}

	public void setTeamMemberId(Long teamMemberId) {
		this.teamMemberId = teamMemberId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public List<Artiface> getArtifaceList() {
		return artifaceList;
	}

	public void setArtifaceList(List<Artiface> artifaceList) {
		this.artifaceList = artifaceList;
	}
	
 

}
