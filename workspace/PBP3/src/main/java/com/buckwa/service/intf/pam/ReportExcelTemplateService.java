package com.buckwa.service.intf.pam;

import com.buckwa.domain.common.BuckWaRequest;
/*
@Author : Taechapon Himarat (Su)
@Create : Dec 2, 2012 4:14:32 PM
 */
public interface ReportExcelTemplateService {
	
	public void createKpiSummaryReport(BuckWaRequest request);
	public void createKpiSummaryPersonReport(BuckWaRequest request);
	public void createKpiSummaryByTopicReport(BuckWaRequest request);
	public void createKpiSummaryByFacultyAndPersonReport(BuckWaRequest request);
	
}
