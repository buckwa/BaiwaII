package com.buckwa.domain.pam;

import java.util.Comparator;

/*
@Author : Taechapon Himarat (Su)
@Create : Nov 23, 2012 11:22:22 PM
 */
public class ReportKpiSummaryUploadScoreComparator implements Comparator<ReportKpiSummary> {

	@Override
	public int compare(ReportKpiSummary kpiSummary1, ReportKpiSummary kpiSummary2) {
		return kpiSummary1.getUploadScore().compareTo(kpiSummary2.getUploadScore()) * (-1);
	}

}
