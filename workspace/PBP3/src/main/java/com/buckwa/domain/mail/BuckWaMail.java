package com.buckwa.domain.mail;

public class BuckWaMail {
	
	
	private String sendFrom;	
	private String sendTo;
	private String sendToName;
	private String subject;
	private String text;	
	private String userSecureCode;
	private String message;
	
	private String appContext;
	
	
	
	public String getSendFrom() {
		return sendFrom;
	}
	public void setSendFrom(String sendFrom) {
		this.sendFrom = sendFrom;
	}
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSendToName() {
		return sendToName;
	}
	public void setSendToName(String sendToName) {
		this.sendToName = sendToName;
	}
	public String getUserSecureCode() {
		return userSecureCode;
	}
	public void setUserSecureCode(String userSecureCode) {
		this.userSecureCode = userSecureCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAppContext() {
		return appContext;
	}
	public void setAppContext(String appContext) {
		this.appContext = appContext;
	}
	
	
	 
}
