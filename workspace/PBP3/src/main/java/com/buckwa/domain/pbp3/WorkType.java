package com.buckwa.domain.pbp3;

import java.util.List;

import com.buckwa.domain.pbp.AcademicKPI;

public class WorkType {	
	
	private String workTypeName;
	
	private List<AcademicKPI> academicKPIList;

	public String getWorkTypeName() {
		return workTypeName;
	}

	public void setWorkTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
	}

	public List<AcademicKPI> getAcademicKPIList() {
		return academicKPIList;
	}

	public void setAcademicKPIList(List<AcademicKPI> academicKPIList) {
		this.academicKPIList = academicKPIList;
	}
	
	
	
	
	 
}
