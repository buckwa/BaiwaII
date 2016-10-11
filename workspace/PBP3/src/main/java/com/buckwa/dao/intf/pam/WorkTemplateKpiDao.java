package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.pam.WorkTemplateKpi;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:36:23 PM
 */
public interface WorkTemplateKpiDao {
	public WorkTemplateKpi create(WorkTemplateKpi obj);
	public void deleteByWorkTemplateId(String id);
	public List<WorkTemplateKpi> getByWorkTemplateId(String id);
	public List<WorkTemplateKpi> getByWorkTemplateAndAttrId(String id,String workTemplateAttrId) ;
}

