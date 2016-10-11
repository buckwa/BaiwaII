package com.buckwa.util;

public class BuckWaException extends Exception {
	
	String code;
	String message;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public BuckWaException(String codeIn,String messageIn){
		code =codeIn;
		message = messageIn;
	}

}
