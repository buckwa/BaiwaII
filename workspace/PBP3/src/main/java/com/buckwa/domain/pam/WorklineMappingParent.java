package com.buckwa.domain.pam;

import java.util.List;

/*
@Author : Teerawoot Charoenporn(Tor)
@Create : Aug 12, 2012 1:56:24 AM
 */
public class WorklineMappingParent {
	private Long id;
	private String worklineCode;
	private String worklineName;
	private String parentCode;
	private String status;
	private List<WorklineMappingParent> childWorklineList;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getWorklineCode() {
		return worklineCode;
	}
	public void setWorklineCode(String worklineCode) {
		this.worklineCode = worklineCode;
	}
	public String getWorklineName() {
		return worklineName;
	}
	public void setWorklineName(String worklineName) {
		this.worklineName = worklineName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<WorklineMappingParent> getChildWorklineList() {
		return childWorklineList;
	}
	public void setChildWorklineList(List<WorklineMappingParent> childWorklineList) {
		this.childWorklineList = childWorklineList;
	}
	
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PositionMappingParent [id=");
		builder.append(id);
		builder.append(", worklineCode=");
		builder.append(worklineCode);
		builder.append(", worklineName=");
		builder.append(worklineName);
		builder.append(", parentCode=");
		builder.append(parentCode);
		builder.append(", status=");
		builder.append(status);
		builder.append(", childWorklineList=");
		builder.append(childWorklineList);
		builder.append("]");
		return builder.toString();
	}
	
	
}
