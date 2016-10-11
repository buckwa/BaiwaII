package com.buckwa.framework.sms;

import java.io.Serializable;

public class SMSResult implements Serializable{ 
	
	
	private int status;
	private String statusDesc;
	private SMSDomain smsDomain;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public SMSDomain getSmsDomain() {
		return smsDomain;
	}
	public void setSmsDomain(SMSDomain smsDomain) {
		this.smsDomain = smsDomain;
	}
	
	
	

}
