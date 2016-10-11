package com.buckwa.dao.intf.admin;

import java.util.List;

import com.buckwa.domain.admin.LeaveQuota;
import com.buckwa.domain.admin.LeaveYearQuota;
import com.buckwa.domain.common.PagingBean;

public interface LeaveQuotaDao {	

	
	public LeaveYearQuota createYearQuota(LeaveYearQuota obj);
	public LeaveYearQuota updateYearQuota(LeaveYearQuota leaveYearQuota);
	public void deleteYearQuota(String leaveYearQuotaId);
	public LeaveYearQuota getYearQuotaById(String leaveYearQuotaId);
	public PagingBean getAllByOffset(PagingBean pagingBean);
	public boolean checkYearAlready(String id,String year);
	public List<LeaveQuota> getByYear(String year);
	public void deleteAll();
	public List<LeaveQuota> getAll();
	
	public LeaveQuota create(LeaveQuota obj);
	public LeaveQuota update(LeaveQuota leaveQuota);
	public void deleteAllLeaveQuota(String leaveYearQuotaId);
	public List<LeaveQuota> getByLeaveYearQuotaId(String leaveYearQuotaId);
	
}
