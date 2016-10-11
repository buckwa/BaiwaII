package com.buckwa.dao.intf.pam;

import java.util.List;
import java.util.Map;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.pam.SummaryLeave;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:36:23 PM
 */
public interface SummaryLeaveDao {
	public void create(SummaryLeave obj);
	public void update(SummaryLeave obj);
	public void deleteByYear(int year);
	public List<SummaryLeave> getSummary(int year,String leaveTypeCode,Long userid);
	public int getSummaryLeaveByYear(int year,int yearCurrent);
	public List<SummaryLeave> getSummaryByUserAndYear(int year,Long userid);
	public void updateByYearLeaveTypeCode(SummaryLeave obj);
	public List<SummaryLeave> getSummaryAllByYear(int year);
	public List<SummaryLeave> getSummaryByUser(Long userid);
	public List<Map<String, Object>> getAllLeaveSummaryByYear(String year);
	public List<SummaryLeave> getSummaryVacationByYear(int year);
	public void updateFlagAccumulate();
	public List<Map<String, Object>> getAllLeaveSummaryByCriteria(BuckWaRequest request);
}
