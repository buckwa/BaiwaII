package com.buckwa.domain.pam;

import java.util.Comparator;

/*
@Author : Taechapon Himarat (Su)
@Create : Mar 9, 2012 20:41:26 PM
 */
public class ReportKpiSummaryWeightScoreComparator implements Comparator<ReportKpiSummary> {
	
	private static class singletonHolder {
		private static final ReportKpiSummaryWeightScoreComparator INSTANCE = new ReportKpiSummaryWeightScoreComparator();
	}

	public static ReportKpiSummaryWeightScoreComparator getInstance() {
		return singletonHolder.INSTANCE;
	}
	
	@Override
	public int compare(ReportKpiSummary kpiSummary1, ReportKpiSummary kpiSummary2) {
		return kpiSummary1.getWeightScore().compareTo(kpiSummary2.getWeightScore()) * (-1);
	}

}
