package com.buckwa.domain.pbp3;

public class ResponseObjPaging {
	
	private String status;
	private String description;
	private Object resObj;
	private Object resPagingBean;
	
	public Object getResPagingBean() {
		return resPagingBean;
	}
	public void setResPagingBean(Object resPagingBean) {
		this.resPagingBean = resPagingBean;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Object getResObj() {
		return resObj;
	}
	public void setResObj(Object resObj) {
		this.resObj = resObj;
	}
	
	

}
