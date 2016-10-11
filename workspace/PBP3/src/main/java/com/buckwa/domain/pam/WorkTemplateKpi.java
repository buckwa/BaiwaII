package com.buckwa.domain.pam;

import com.buckwa.domain.BaseDomain;

public class WorkTemplateKpi extends BaseDomain  {
	
	private Long workTemplateKpiId;
	private Long workTemplateId;
	private Long kpiId;
	private Long workTemplateAttrId;
	private int flagCalculate;
	
	
	
	
	public int getFlagCalculate() {
		return flagCalculate;
	}
	public void setFlagCalculate(int flagCalculate) {
		this.flagCalculate = flagCalculate;
	}
	public Long getWorkTemplateAttrId() {
		return workTemplateAttrId;
	}
	public void setWorkTemplateAttrId(Long workTemplateAttrId) {
		this.workTemplateAttrId = workTemplateAttrId;
	}
	public Long getWorkTemplateKpiId() {
		return workTemplateKpiId;
	}
	public void setWorkTemplateKpiId(Long workTemplateKpiId) {
		this.workTemplateKpiId = workTemplateKpiId;
	}
	public Long getWorkTemplateId() {
		return workTemplateId;
	}
	public void setWorkTemplateId(Long workTemplateId) {
		this.workTemplateId = workTemplateId;
	}
	public Long getKpiId() {
		return kpiId;
	}
	public void setKpiId(Long kpiId) {
		this.kpiId = kpiId;
	}
	
	
	
}
