package com.buckwa.domain.pam;

import com.buckwa.domain.BaseDomain;

public class WorkPersonKpi extends BaseDomain  {
	
	private Long workPersonKpiId;
	private Long workPersonId;
	private Long kpiId;
	private Long workPersonAttrId;
	private int flagCalculate;
	
	
	public int getFlagCalculate() {
		return flagCalculate;
	}
	public void setFlagCalculate(int flagCalculate) {
		this.flagCalculate = flagCalculate;
	}
	public Long getWorkPersonAttrId() {
		return workPersonAttrId;
	}
	public void setWorkPersonAttrId(Long workPersonAttrId) {
		this.workPersonAttrId = workPersonAttrId;
	}
	public Long getWorkPersonKpiId() {
		return workPersonKpiId;
	}
	public void setWorkPersonKpiId(Long workPersonKpiId) {
		this.workPersonKpiId = workPersonKpiId;
	}
	public Long getWorkPersonId() {
		return workPersonId;
	}
	public void setWorkPersonId(Long workPersonId) {
		this.workPersonId = workPersonId;
	}
	public Long getKpiId() {
		return kpiId;
	}
	public void setKpiId(Long kpiId) {
		this.kpiId = kpiId;
	}
	
	
}
