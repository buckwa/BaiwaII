package com.buckwa.domain.admin;

import java.io.Serializable;
import java.util.List;

public class LeaveQuotaForm implements Serializable {
	
	private List<LeaveQuota> leaveQuotaList;

	
	public List<LeaveQuota> getLeaveQuotaList() {
		return leaveQuotaList;
	}

	public void setLeaveQuotaList(List<LeaveQuota> leaveQuotaList) {
		this.leaveQuotaList = leaveQuotaList;
	}
	
}
