package com.buckwa.dao.intf.pam;

import com.buckwa.domain.pam.PersonalLeave;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:36:23 PM
 */
public interface PersonalLeaveDao {
	public PersonalLeave create(PersonalLeave obj);
	public String generateDocNo();
	public PersonalLeave getById(String id);
	public PersonalLeave getByDocNo(String docNo);
	public PersonalLeave getLastDate();
}

