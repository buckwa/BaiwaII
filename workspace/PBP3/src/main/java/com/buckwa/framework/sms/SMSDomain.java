package com.buckwa.framework.sms;

import java.io.Serializable;

public class SMSDomain implements Serializable{	
	private String sendToNo;
	private String senderName;
	private String smsBody;
	private int failCount;
	private String smsURL;
	
	
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSmsBody() {
		return smsBody;
	}
	public void setSmsBody(String smsBody) {
		this.smsBody = smsBody;
	}
	public int getFailCount() {
		return failCount;
	}
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}
	public String getSendToNo() {
		return sendToNo;
	}
	public void setSendToNo(String sendToNo) {
		this.sendToNo = sendToNo;
	}
	public String getSmsURL() {
		return smsURL;
	}
	public void setSmsURL(String smsURL) {
		this.smsURL = smsURL;
	}

	
	
	
}
