package com.buckwa.domain.pbp;

public class PagingMessage {
	private int pageStrart;
	private int pageEnd;
	private String data;
	private int totalMessage;
	private String departmentName;
	private String user;
	
	public int getPageStrart() {
		return pageStrart;
	}
	public void setPageStrart(int pageStrart) {
		this.pageStrart = pageStrart;
	}
	public int getPageEnd() {
		return pageEnd;
	}
	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getTotalMessage() {
		return totalMessage;
	}
	public void setTotalMessage(int totalMessage) {
		this.totalMessage = totalMessage;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
}
