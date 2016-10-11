package com.buckwa.domain.pbp;

import com.buckwa.domain.BaseDomain;

public class AcademicKPIMessage   extends BaseDomain{
	
	private Long kpiMessageId;
	private String userName;
	private String messageDesc;
	public Long getKpiMessageId() {
		return kpiMessageId;
	}
	public void setKpiMessageId(Long kpiMessageId) {
		this.kpiMessageId = kpiMessageId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMessageDesc() {
		return messageDesc;
	}
	public void setMessageDesc(String messageDesc) {
		this.messageDesc = messageDesc;
	}
	
	

}
