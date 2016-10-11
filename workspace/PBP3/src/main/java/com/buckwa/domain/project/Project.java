package com.buckwa.domain.project;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class Project  extends BaseDomain{
	
	private Long projectId;
	private String projectName;
	private String projectType;
	
	private boolean enable;
	
	private Vision vision;
	
	private List<DetailDesign> detailDesignList;
	
	private Team team;
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public List<DetailDesign> getDetailDesignList() {
		return detailDesignList;
	}
	public void setDetailDesignList(List<DetailDesign> detailDesignList) {
		this.detailDesignList = detailDesignList;
	}
	public Vision getVision() {
		return vision;
	}
	public void setVision(Vision vision) {
		this.vision = vision;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	
	
	

}
