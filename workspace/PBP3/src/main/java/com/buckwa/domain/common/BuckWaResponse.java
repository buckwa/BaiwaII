package com.buckwa.domain.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BuckWaResponse implements Serializable{
	
	private int status =0;
	private String errorCode;
	private String errorDesc;
	private String successCode;
	private String successDesc;
	private Map resObj;
	
	public void addResponse(String key,Object object){
		if(resObj==null){
			resObj = new HashMap();
		}		
		resObj.put(key, object);		
	}
	
	public Object getResObj(String key){
		if(resObj!=null){
			return resObj.get(key);
		}else{
			return null;
		}
	}

	public Map getResObj() {
		return resObj;
	}

	public void setResObj(Map resObj) {
		this.resObj = resObj;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String getSuccessCode() {
		return successCode;
	}

	public void setSuccessCode(String successCode) {
		this.successCode = successCode;
	}

	public String getSuccessDesc() {
		return successDesc;
	}

	public void setSuccessDesc(String successDesc) {
		this.successDesc = successDesc;
	}

	
}
