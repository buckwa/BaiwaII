package com.buckwa.dao.intf.pam;

import com.buckwa.domain.pam.Leave;
import com.buckwa.domain.pam.MonkhoodLeave;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:36:23 PM
 */
public interface MonkhoodLeaveDao {
	public MonkhoodLeave create(MonkhoodLeave obj);
	public String generateDocNo();
	public MonkhoodLeave getById(String id);
	public MonkhoodLeave getByDocNo(String docNo);
}

