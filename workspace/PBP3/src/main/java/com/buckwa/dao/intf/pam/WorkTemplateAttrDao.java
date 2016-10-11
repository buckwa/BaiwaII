package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.pam.WorkTemplateAttr;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:36:23 PM
 */
public interface WorkTemplateAttrDao {
	public WorkTemplateAttr create(WorkTemplateAttr obj);
	public void deleteByWorkTemplateId(String id);
	public List<WorkTemplateAttr> getByWorkTemplateId(String id);
	public List<WorkTemplateAttr> getIsFileListByWorkTemplateId(String id);
}

