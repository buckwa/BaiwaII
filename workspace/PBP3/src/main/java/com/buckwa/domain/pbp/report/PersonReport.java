package com.buckwa.domain.pbp.report;

import java.io.Serializable;

public class PersonReport implements Serializable{
	
	private String thaiName;
	private String thanSurName;
	private String regId;
	
	public String getThaiName() {
		return thaiName;
	}
	public void setThaiName(String thaiName) {
		this.thaiName = thaiName;
	}
	public String getThanSurName() {
		return thanSurName;
	}
	public void setThanSurName(String thanSurName) {
		this.thanSurName = thanSurName;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	
	
	 
}
