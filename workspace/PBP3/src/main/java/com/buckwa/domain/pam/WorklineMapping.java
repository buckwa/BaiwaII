package com.buckwa.domain.pam;

import com.buckwa.domain.BaseDomain;

/*
@Author : Teerawoot Charoenporn(Tor)
@Create : Aug 11, 2012 11:10:26 PM
 */
public class WorklineMapping extends BaseDomain{
	private Long id;
	private String worklineCode;
	private String worklineName;
	private String parentName;
	private String parentCode;
	
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

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getWorklineName() {
		return worklineName;
	}

	public void setWorklineName(String worklineName) {
		this.worklineName = worklineName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorklineMapping [id=");
		builder.append(id);
		builder.append(", worklineCode=");
		builder.append(worklineCode);
		builder.append(", worklineName=");
		builder.append(worklineName);
		builder.append(", parentName=");
		builder.append(parentName);
		builder.append(", parentCode=");
		builder.append(parentCode);
		builder.append("]");
		return builder.toString();
	}
	
}
