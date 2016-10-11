package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Kpi;
import com.buckwa.domain.pam.Person;
import com.buckwa.domain.pam.Workline;
import com.buckwa.domain.pam.WorklineMapping;
import com.buckwa.domain.pam.WorklineMappingParent;

/*
@Author : Teerawoot Charoenporn(Tor)
@Create : Aug 13, 2012 1:38:55 PM
 */
public interface WorklineDao {
	public PagingBean getByOffset(PagingBean pagingBean);
	public Long create(Workline domain);
	public void update(Workline domain); 
	public List<Workline> getAll();
	public Workline getById(String id);
	public void deleteById(String id);
	public boolean isAlreadyUsege(String id);
	public boolean isAlreadyUsege(String worklinecode, boolean isParent);
	public boolean isExist(String name, String code);	 
	public boolean isExistForUpdate(String name, String code, Long id);
	
	public List<WorklineMappingParent> getWorklineMappingByWorklineCode(String worklineCode);
	public List<Workline> getUnassignedWorkline();
	public List<Workline> getUnassignedPersonWorkline();
	public Workline getByWorklineCode(String worklineCode);
	public void addWorklineMapping(String worklineCode, String parentCode);
	public void updateWorklineMapping(String worklineCode, String parentCode);
	public void deleteWorklineMapping(String worklineCode);
	
	public PagingBean getWorklinePersonByOffset(PagingBean pagingBean);
	public void updateWorklinePerson(Person domain);
	public void addWorklinePerson(Person domain);
	public void deleteWorklinePerson(Person domain);
}
