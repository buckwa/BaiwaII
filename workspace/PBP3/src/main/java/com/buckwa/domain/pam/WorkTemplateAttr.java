package com.buckwa.domain.pam;

import com.buckwa.domain.BaseDomain;

public class WorkTemplateAttr extends BaseDomain  {
	
	private Long workTemplateAttrId;
	private Long workTemplateId;
	private String label;
	private Long unitId;
	private String type;
	private String unitName;
	private int isDel;
	private Long kpiId;
	private Integer isFile;
	private Integer flagCalculate;
	
	
	
	public Integer getFlagCalculate() {
		return flagCalculate;
	}
	public void setFlagCalculate(Integer flagCalculate) {
		this.flagCalculate = flagCalculate;
	}
	public Integer getIsFile() {
		return isFile;
	}
	public void setIsFile(Integer isFile) {
		this.isFile = isFile;
	}
	public Long getKpiId() {
		return kpiId;
	}
	public void setKpiId(Long kpiId) {
		this.kpiId = kpiId;
	}
	
	public int getIsDel() {
		return isDel;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Long getWorkTemplateAttrId() {
		return workTemplateAttrId;
	}
	public void setWorkTemplateAttrId(Long workTemplateAttrId) {
		this.workTemplateAttrId = workTemplateAttrId;
	}
	public Long getWorkTemplateId() {
		return workTemplateId;
	}
	public void setWorkTemplateId(Long workTemplateId) {
		this.workTemplateId = workTemplateId;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
