package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.pam.WorkPersonKpi;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:36:23 PM
 */
public interface WorkPersonKpiDao {
	public WorkPersonKpi create(WorkPersonKpi obj);
	public void deleteByWorkPersonId(String id);
	public List<WorkPersonKpi> getByWorkPersonId(String id);
}

