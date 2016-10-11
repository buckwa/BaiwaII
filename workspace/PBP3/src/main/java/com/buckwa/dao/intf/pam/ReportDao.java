package com.buckwa.dao.intf.pam;

import java.util.Date;
import java.util.List;
/*
@Author : Taechapon Himarat (Su)
@Create : Oct 18, 2012 10:46:47 PM
 */

import com.buckwa.domain.pam.KpiPersonEvaluateMapping;
import com.buckwa.domain.pam.ReportPersonByFaculty;
import com.buckwa.domain.pam.ReportPersonSummary;
import com.buckwa.domain.pam.SummaryLeave;
public interface ReportDao {
	
	// Raise Salary Report
	public List<SummaryLeave> getSummaryLeaveByUserIdAndSemester(Long userId, Date fromDate, Date toDate);
	
	public List<ReportPersonSummary> getPersonSummaryReport();
	
	public List<ReportPersonByFaculty> getPersonByFacultyReport();
	
	// Kpi Summary Report
	public List<KpiPersonEvaluateMapping> getKpiSummaryScoreList(String yearId);
	
	// Kpi Summary In Faculty Report
	public List<KpiPersonEvaluateMapping> getKpiSummaryScoreList(String yearId, List<String> userIdList);
	
}
