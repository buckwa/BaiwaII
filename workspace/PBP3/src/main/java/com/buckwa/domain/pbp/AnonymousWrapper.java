package com.buckwa.domain.pbp;

import com.buckwa.domain.BaseDomain;

public class AnonymousWrapper extends BaseDomain{
	
	private AcademicYearWrapper academicYearWrapper;
	
	private PBPWorkTypeWrapper pBPWorkTypeWrapper;
	
	private String radarURL;
	private String radarURL2;
	
	
	
	public String getRadarURL2() {
		return radarURL2;
	}

	public void setRadarURL2(String radarURL2) {
		this.radarURL2 = radarURL2;
	}

	public String getRadarURL() {
		return radarURL;
	}

	public void setRadarURL(String radarURL) {
		this.radarURL = radarURL;
	}

	private MarkRankWrapper markRankWrapper;
	private String academicYear;

	public AcademicYearWrapper getAcademicYearWrapper() {
		return academicYearWrapper;
	}

	public void setAcademicYearWrapper(AcademicYearWrapper academicYearWrapper) {
		this.academicYearWrapper = academicYearWrapper;
	}

	public PBPWorkTypeWrapper getpBPWorkTypeWrapper() {
		return pBPWorkTypeWrapper;
	}

	public void setpBPWorkTypeWrapper(PBPWorkTypeWrapper pBPWorkTypeWrapper) {
		this.pBPWorkTypeWrapper = pBPWorkTypeWrapper;
	}

	public MarkRankWrapper getMarkRankWrapper() {
		return markRankWrapper;
	}

	public void setMarkRankWrapper(MarkRankWrapper markRankWrapper) {
		this.markRankWrapper = markRankWrapper;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	
	
	

}
