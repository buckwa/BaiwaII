package com.buckwa.domain.pam;

import com.buckwa.domain.BaseDomain;

/*
@Author : Taechapon Himarat (Su)
@Create : Dec 12, 2012 10:51:02 PM
 */
public class ReportWorkPerson extends BaseDomain {

	private static final long serialVersionUID = 4247580881475485049L;
	
	private int id;
	private String name;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
