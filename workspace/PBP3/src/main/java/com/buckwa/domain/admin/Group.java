package com.buckwa.domain.admin;

import java.util.List;

import com.buckwa.domain.BaseDomain;

public class Group  extends BaseDomain  {
	
	private String groupId;
	private String userId;
	private String groupName;
	private String groupDesc;
	private boolean enable;
	private List roleList;
	
	private  String [] authorizes;
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List getRoleList() {
		return roleList;
	}
	public void setRoleList(List roleList) {
		this.roleList = roleList;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public String getGroupDesc() {
		return groupDesc;
	}
	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}
	public String[] getAuthorizes() {
		return authorizes;
	}
	public void setAuthorizes(String[] authorizes) {
		this.authorizes = authorizes;
	}

	

}
