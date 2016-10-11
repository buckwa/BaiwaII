package com.buckwa.dao.intf.pam;

import com.buckwa.domain.pam.Leave;
import com.buckwa.domain.pam.SickLeave;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:36:23 PM
 */
public interface SickLeaveDao {
	public SickLeave create(SickLeave obj);
	public String generateDocNo();
	public SickLeave getById(String id);
	public SickLeave getByDocNo(String docNo);
	public SickLeave getLastDate();
}

