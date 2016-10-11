package com.buckwa.domain.project;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.buckwa.domain.BaseDomain;
import com.buckwa.domain.admin.Group;

public class Team extends BaseDomain{	
	private Long teamId;
	private Long projectId;
	private String projectName;

	private Long userId;
	private String username;
	private String []groups;
	private List<Group> groupList;	
	private GrantedAuthority [] projectAuthorities;
	
	
	private List<Team> pmList;
	private List<Team> saList;
	private List<Team> devList;
	private List<Team> testerList;
	private List<Team> noAssignList;
	
	
	
	public GrantedAuthority[] getProjectAuthorities() {
		return projectAuthorities;
	}

	public void setProjectAuthorities(GrantedAuthority[] projectAuthorities) {
		this.projectAuthorities = projectAuthorities;
	}

	public Long getTeamId() {
		return teamId;
	}

	public String[] getGroups() {
		return groups;
	}

	public void setGroups(String[] groups) {
		this.groups = groups;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
 
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	} 

	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<Team> getPmList() {
		return pmList;
	}

	public void setPmList(List<Team> pmList) {
		this.pmList = pmList;
	}

	public List<Team> getSaList() {
		return saList;
	}

	public void setSaList(List<Team> saList) {
		this.saList = saList;
	}

	public List<Team> getDevList() {
		return devList;
	}

	public void setDevList(List<Team> devList) {
		this.devList = devList;
	}

	public List<Team> getTesterList() {
		return testerList;
	}

	public void setTesterList(List<Team> testerList) {
		this.testerList = testerList;
	}

	public List<Team> getNoAssignList() {
		return noAssignList;
	}

	public void setNoAssignList(List<Team> noAssignList) {
		this.noAssignList = noAssignList;
	}
	 
	
	
	

}
