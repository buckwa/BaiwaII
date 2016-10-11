package com.buckwa.dao.intf.pam;

import com.buckwa.domain.pam.Leave;
import com.buckwa.domain.pam.VacationLeave;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:36:23 PM
 */
public interface VacationLeaveDao {
	public VacationLeave create(VacationLeave obj);
	public String generateDocNo();
	public VacationLeave getById(String id);
	public VacationLeave getByDocNo(String docNo);
}

