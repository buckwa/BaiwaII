package com.buckwa.domain.pam;

import com.buckwa.domain.BaseDomain;

/*
@Author : Teerawoot Charoenporn(Tor)
@Create : Aug 11, 2012 11:00:33 PM
 */
public class Workline extends BaseDomain{
	private Long id;
	private String worklineCode;
	private String worklineName;
	
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Position [id=");
		builder.append(id);
		builder.append(", worklineCode=");
		builder.append(worklineCode);
		builder.append(", worklineName=");
		builder.append(worklineName);
		builder.append(", status=");
		builder.append(getStatus());
		builder.append("]");
		return builder.toString();
	}
	
}
