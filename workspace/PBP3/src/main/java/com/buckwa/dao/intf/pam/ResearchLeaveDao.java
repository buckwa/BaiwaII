package com.buckwa.dao.intf.pam;

import com.buckwa.domain.pam.Leave;
import com.buckwa.domain.pam.ResearchLeave;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:36:23 PM
 */
public interface ResearchLeaveDao {
	public ResearchLeave create(ResearchLeave obj);
	public String generateDocNo();
	public ResearchLeave getById(String id);
	public ResearchLeave getByDocNo(String docNo);
}

