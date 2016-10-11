package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.pam.WorkPersonAttr;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:36:23 PM
 */
public interface WorkPersonAttrDao {
	public WorkPersonAttr create(WorkPersonAttr obj);
	public void deleteByWorkPersonId(String id);
	public List<WorkPersonAttr> getByWorkPersonId(String id);
}

