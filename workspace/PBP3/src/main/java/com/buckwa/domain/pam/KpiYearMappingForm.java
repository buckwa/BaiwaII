package com.buckwa.domain.pam;

import java.util.List;

import com.buckwa.domain.BaseDomain;
 
public class KpiYearMappingForm {
	
	private List<KpiYearMapping> kpiYearMappingAllList;
	private Long semesterId;
	private Long yearId;

	public List<KpiYearMapping> getKpiYearMappingAllList() {
		return kpiYearMappingAllList;
	}

	public void setKpiYearMappingAllList(List<KpiYearMapping> kpiYearMappingAllList) {
		this.kpiYearMappingAllList = kpiYearMappingAllList;
	}

	public Long getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(Long semesterId) {
		this.semesterId = semesterId;
	}

	public Long getYearId() {
		return yearId;
	}

	public void setYearId(Long yearId) {
		this.yearId = yearId;
	}

	@Override
	public String toString() {
		return "KpiYearMappingForm [kpiYearMappingAllList="
				+ kpiYearMappingAllList + ", semesterId=" + semesterId
				+ ", yearId=" + yearId + "]";
	}

}
