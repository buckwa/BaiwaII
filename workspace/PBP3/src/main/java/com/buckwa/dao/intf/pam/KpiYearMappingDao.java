package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.KpiYearMapping;
import com.buckwa.domain.pam.Year;
import com.buckwa.domain.pam.nodetree.KpiTree;
/*
@Author : Teerawoot Charoenporn(Tor)
@Create : Aug 6, 2012 12:58:13 AM
 */
public interface KpiYearMappingDao {
	
	public PagingBean getByOffset(PagingBean pagingBean);
	public Long create(KpiYearMapping kpiYearMapping);
	public void update(KpiYearMapping kpiYearMapping); 
	public void updateStatus(KpiYearMapping kpiYearMapping); 
	public List<KpiYearMapping> getAll();
	public List<KpiYearMapping> getBySemester(String semesterId);
	public KpiYearMapping getById(String id);
	public void deleteById(String id);
	public boolean isAlreadyUsege(String id);
	public boolean isExist(String name);	 
	public boolean isExistForUpdate(String name,Long id);
	
	public void initialMappingByYear(List<KpiYearMapping> yearMappingList);
	
	public boolean isExistForCreate(KpiYearMapping kpiYearMapping);
	 
	
	public Long createKpiYearMapping(KpiYearMapping kpiYearMapping);
	
	

	
	
}
