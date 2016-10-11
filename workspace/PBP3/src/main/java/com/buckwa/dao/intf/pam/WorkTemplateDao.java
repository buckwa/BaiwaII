package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.WorkTemplate;
import com.buckwa.domain.pam.WorkTemplateAttr;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:36:23 PM
 */
public interface WorkTemplateDao {
	public WorkTemplate create(WorkTemplate obj);
	public WorkTemplate update(WorkTemplate obj);
	public void delete(String id);
	public WorkTemplate getById(String id);
	public PagingBean getAllRoleByOffset(PagingBean pagingBean);
	public boolean checkNameAlready(String id,String name);
	public List<WorkTemplate> getByGroupId(String groupId);
	public WorkTemplate getByClassRoom() ;
	public List<WorkTemplate> getByGroupIdNonTimeTable(String groupId);
	public List<WorkTemplate> getByEmployeeTypeNonTimeTable(String employeeType);
	public List<WorkTemplate> getByPersonTypeNonTimeTable(String employeeType);
}
