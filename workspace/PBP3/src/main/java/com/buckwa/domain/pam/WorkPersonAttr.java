package com.buckwa.domain.pam;

import com.buckwa.domain.BaseDomain;

public class WorkPersonAttr extends BaseDomain  {
	
	private Long workPersonAttrId;
	private Long workTemplateId;
	private Long workPersonId;
	private String label;
	private String value;
	private Long unitId;
	private String unitName;
	private Long kpiId;
	private int isFile;
	private int isClassRoom;
	private int flagCalculate;
	
	
	
	
	public int getFlagCalculate() {
		return flagCalculate;
	}
	public void setFlagCalculate(int flagCalculate) {
		this.flagCalculate = flagCalculate;
	}
	public int getIsClassRoom() {
		return isClassRoom;
	}
	public void setIsClassRoom(int isClassRoom) {
		this.isClassRoom = isClassRoom;
	}
	public int getIsFile() {
		return isFile;
	}
	public void setIsFile(int isFile) {
		this.isFile = isFile;
	}
	public Long getKpiId() {
		return kpiId;
	}
	public void setKpiId(Long kpiId) {
		this.kpiId = kpiId;
	}
	public Long getWorkPersonAttrId() {
		return workPersonAttrId;
	}
	public void setWorkPersonAttrId(Long workPersonAttrId) {
		this.workPersonAttrId = workPersonAttrId;
	}
	public Long getWorkTemplateId() {
		return workTemplateId;
	}
	public void setWorkTemplateId(Long workTemplateId) {
		this.workTemplateId = workTemplateId;
	}
	public Long getWorkPersonId() {
		return workPersonId;
	}
	public void setWorkPersonId(Long workPersonId) {
		this.workPersonId = workPersonId;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	
}
