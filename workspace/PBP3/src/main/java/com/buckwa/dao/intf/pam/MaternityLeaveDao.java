package com.buckwa.dao.intf.pam;

import com.buckwa.domain.pam.MaternityLeave;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:36:23 PM
 */
public interface MaternityLeaveDao {
	public MaternityLeave create(MaternityLeave obj);
	public String generateDocNo();
	public MaternityLeave getById(String id);
	public MaternityLeave getByDocNo(String docNo);
	public MaternityLeave getLastDate();
}

