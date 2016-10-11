package com.buckwa.domain.pbp;

import com.buckwa.domain.BaseDomain;

public class AcademicKPIAttributeValue  extends BaseDomain{
 
	private Long kpiAtributeValueId; 
	private Long kpiAtributeId; 
	private String academicKPICode;
	private String value; 
	private String academicYear;
	
	private String isValidateNumber;
	
	private Long academicKPIMappingId;
	//new field by tum 19/07/2014
	private Integer ratio;
	private String isCalculate;
	
	public Long getAcademicKPIMappingId() {
		return academicKPIMappingId;
	}
	public void setAcademicKPIMappingId(Long academicKPIMappingId) {
		this.academicKPIMappingId = academicKPIMappingId;
	}
	public Long getKpiAtributeValueId() {
		return kpiAtributeValueId;
	}
	public void setKpiAtributeValueId(Long kpiAtributeValueId) {
		this.kpiAtributeValueId = kpiAtributeValueId;
	}
	public String getAcademicKPICode() {
		return academicKPICode;
	}
	public void setAcademicKPICode(String academicKPICode) {
		this.academicKPICode = academicKPICode;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public Long getKpiAtributeId() {
		return kpiAtributeId;
	}
	public void setKpiAtributeId(Long kpiAtributeId) {
		this.kpiAtributeId = kpiAtributeId;
	}
	public Integer getRatio() {
		return ratio;
	}
	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}
	public String getIsCalculate() {
		return isCalculate;
	}
	public void setIsCalculate(String isCalculate) {
		this.isCalculate = isCalculate;
	}
	public String getIsValidateNumber() {
		return isValidateNumber;
	}
	public void setIsValidateNumber(String isValidateNumber) {
		this.isValidateNumber = isValidateNumber;
	}
	
	 
}
