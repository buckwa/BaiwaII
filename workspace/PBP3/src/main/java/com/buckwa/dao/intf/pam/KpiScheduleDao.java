package com.buckwa.dao.intf.pam;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.KpiSchedule;

/*
@Author : Taechapon Himarat (Su)
@Create : Sep 30, 2012 1:18:35 AM
 */
public interface KpiScheduleDao {
	
	public PagingBean getByOffset(PagingBean pagingBean);
	public Long create(KpiSchedule domain);
	public void update(KpiSchedule domain);
	public KpiSchedule getById(Long kpiScheduleId);
	public KpiSchedule getByYearName(String yearName);
	public void deleteById(Long kpiScheduleId, String username);
	public boolean isExist(String yearName);
	
}
