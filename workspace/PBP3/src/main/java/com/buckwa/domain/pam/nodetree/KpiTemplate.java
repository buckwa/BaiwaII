package com.buckwa.domain.pam.nodetree;

import java.math.BigDecimal;

public class KpiTemplate {
	
	private Long kpiTemplateId;
	private Long parentId;
	private Long yearId;
	private Long categoryId;
	private String code;
	private String name;
	private String number;
	
	private String isLeave;
	
	private BigDecimal mark;
	
	private Long unitId;
	 private String markType;
	 
	private String unitDesc;
	 private String markTypeDesc;
	
	 private BigDecimal weight;
   
	 private BigDecimal weightTotal;
	
	private int childOrder;
	
	
	
	
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getWeightTotal() {
		return weightTotal;
	}
	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}
	public String getUnitDesc() {
		return unitDesc;
	}
	public void setUnitDesc(String unitDesc) {
		this.unitDesc = unitDesc;
	}
 
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getIsLeave() {
		return isLeave;
	}
	public void setIsLeave(String isLeave) {
		this.isLeave = isLeave;
	}
	public BigDecimal getMark() {
		return mark;
	}
	public void setMark(BigDecimal mark) {
		this.mark = mark;
	}
	public int getChildOrder() {
		return childOrder;
	}
	public void setChildOrder(int childOrder) {
		this.childOrder = childOrder;
	}
	public Long getKpiTemplateId() {
		return kpiTemplateId;
	}
	public void setKpiTemplateId(Long kpiTemplateId) {
		this.kpiTemplateId = kpiTemplateId;
	}
	public String getMarkType() {
		return markType;
	}
	public void setMarkType(String markType) {
		this.markType = markType;
	}
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	public String getMarkTypeDesc() {
		return markTypeDesc;
	}
	public void setMarkTypeDesc(String markTypeDesc) {
		this.markTypeDesc = markTypeDesc;
	}
	public Long getYearId() {
		return yearId;
	}
	public void setYearId(Long yearId) {
		this.yearId = yearId;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	
	

}
