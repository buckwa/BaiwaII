package com.buckwa.domain.pbp;

import com.buckwa.domain.BaseDomain;

public class AcademicKPIAttribute  extends BaseDomain{
 
	private Long academicKPIAtributeId;
	private Long academicKPIId;
	private String academicKPICode;
	private String value;
	private String isCalculate;
	private String isAttatchFile; 
	private String academicYear;
	private String mandatory;
	//new field by tum 19/07/2014
	private Integer ratio;
	private String isValidateNumber;
	
	
 
	public String getIsValidateNumber() {
		return isValidateNumber;
	}
	public void setIsValidateNumber(String isValidateNumber) {
		this.isValidateNumber = isValidateNumber;
	}
	public String getMandatory() {
		return mandatory;
	}
	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}
	public Long getAcademicKPIAtributeId() {
		return academicKPIAtributeId;
	}
	public void setAcademicKPIAtributeId(Long academicKPIAtributeId) {
		this.academicKPIAtributeId = academicKPIAtributeId;
	}
	public Long getAcademicKPIId() {
		return academicKPIId;
	}
	public void setAcademicKPIId(Long academicKPIId) {
		this.academicKPIId = academicKPIId;
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
	public String getIsCalculate() {
		return isCalculate;
	}
	public void setIsCalculate(String isCalculate) {
		this.isCalculate = isCalculate;
	}
	public String getIsAttatchFile() {
		return isAttatchFile;
	}
	public void setIsAttatchFile(String isAttatchFile) {
		this.isAttatchFile = isAttatchFile;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public Integer getRatio() {
		return ratio;
	}
	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}
 
	 

}
