package com.buckwa.domain.pbp;

import java.math.BigDecimal;
import java.util.List;

import com.buckwa.domain.BaseDomain;

public class PBPWorkType extends BaseDomain{
	
	 
	private Long workTypeId;
    private BigDecimal minPercent;
    private BigDecimal minHour;
    private BigDecimal maxPercent;
    private BigDecimal maxHour;
    private String shortDesc;
    private BigDecimal limitBase;
    private BigDecimal totalAllWorkType;
    private String facultyCode;
    
    private List<AcademicKPI> academicKPIList;
    
	private BigDecimal totalInWorkType = new BigDecimal(0.0);
	private BigDecimal totalInWorkTypeCompareBase = new BigDecimal(0.0);
    private BigDecimal totalInPercentWorkType = new BigDecimal(0.0);
    private BigDecimal totalInPercentCompareBaseWorkType = new BigDecimal(0.0);
    private BigDecimal totalInPercentCompareBaseWorkTypeAVG = new BigDecimal(0.0);
    private String compareBaseStatus;
    
    
	private BigDecimal totalInWorkType_E = new BigDecimal(0.0);
	private BigDecimal totalInWorkTypeCompareBase_E = new BigDecimal(0.0);
    private BigDecimal totalInPercentWorkType_E = new BigDecimal(0.0);
    private BigDecimal totalInPercentCompareBaseWorkType_E = new BigDecimal(0.0);
    
    private boolean minHourCal;
   
    private boolean maxHourCal;
    
    public String getFacultyCode() {
		return facultyCode;
	}
	public void setFacultyCode(String facultyCode) {
		this.facultyCode = facultyCode;
	}
	public BigDecimal getTotalInPercentCompareBaseWorkTypeAVG() {
		return totalInPercentCompareBaseWorkTypeAVG;
	}
	public void setTotalInPercentCompareBaseWorkTypeAVG(
			BigDecimal totalInPercentCompareBaseWorkTypeAVG) {
		this.totalInPercentCompareBaseWorkTypeAVG = totalInPercentCompareBaseWorkTypeAVG;
	}
	public BigDecimal getTotalInWorkTypeCompareBase() {
		return totalInWorkTypeCompareBase;
	}
	public void setTotalInWorkTypeCompareBase(BigDecimal totalInWorkTypeCompareBase) {
		this.totalInWorkTypeCompareBase = totalInWorkTypeCompareBase;
	}
	public BigDecimal getTotalAllWorkType() {
		return totalAllWorkType;
	}
	public void setTotalAllWorkType(BigDecimal totalAllWorkType) {
		this.totalAllWorkType = totalAllWorkType;
	}
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

    
    
    public BigDecimal getTotalInPercentCompareBaseWorkType_E() {
		return totalInPercentCompareBaseWorkType_E;
	}
	public void setTotalInPercentCompareBaseWorkType_E(
			BigDecimal totalInPercentCompareBaseWorkType_E) {
		this.totalInPercentCompareBaseWorkType_E = totalInPercentCompareBaseWorkType_E;
	}
	public BigDecimal getTotalInWorkType_E() {
		return totalInWorkType_E;
	}
	public void setTotalInWorkType_E(BigDecimal totalInWorkType_E) {
		this.totalInWorkType_E = totalInWorkType_E;
	}
	public BigDecimal getTotalInWorkTypeCompareBase_E() {
		return totalInWorkTypeCompareBase_E;
	}
	public void setTotalInWorkTypeCompareBase_E(
			BigDecimal totalInWorkTypeCompareBase_E) {
		this.totalInWorkTypeCompareBase_E = totalInWorkTypeCompareBase_E;
	}
	public BigDecimal getTotalInPercentWorkType_E() {
		return totalInPercentWorkType_E;
	}
	public void setTotalInPercentWorkType_E(BigDecimal totalInPercentWorkType_E) {
		this.totalInPercentWorkType_E = totalInPercentWorkType_E;
	}
	public BigDecimal getTotalInPercentWorkType() {
		return totalInPercentWorkType;
	}
	public void setTotalInPercentWorkType(BigDecimal totalInPercentWorkType) {
		this.totalInPercentWorkType = totalInPercentWorkType;
	}
	public BigDecimal getTotalInWorkType() {
		return totalInWorkType;
	}
	public void setTotalInWorkType(BigDecimal totalInWorkType) {
		this.totalInWorkType = totalInWorkType;
	}
	private List<PBPWorkTypeSub> pBPWorkTypeSubList;
    
    private List<AcademicKPIUserMapping> academicKPIUserMappingList;
    
    
    public List<AcademicKPIUserMapping> getAcademicKPIUserMappingList() {
		return academicKPIUserMappingList;
	}
	public void setAcademicKPIUserMappingList(
			List<AcademicKPIUserMapping> academicKPIUserMappingList) {
		this.academicKPIUserMappingList = academicKPIUserMappingList;
	}
	private PBPWorkTypeSub pBPWorkTypeSub;
    private String academicYear;
	public Long getWorkTypeId() {
		return workTypeId;
	}
	public void setWorkTypeId(Long workTypeId) {
		this.workTypeId = workTypeId;
	}
 
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public List<PBPWorkTypeSub> getpBPWorkTypeSubList() {
		return pBPWorkTypeSubList;
	}
	public void setpBPWorkTypeSubList(List<PBPWorkTypeSub> pBPWorkTypeSubList) {
		this.pBPWorkTypeSubList = pBPWorkTypeSubList;
	}
	public PBPWorkTypeSub getpBPWorkTypeSub() {
		return pBPWorkTypeSub;
	}
	public void setpBPWorkTypeSub(PBPWorkTypeSub pBPWorkTypeSub) {
		this.pBPWorkTypeSub = pBPWorkTypeSub;
	}
	public List<AcademicKPI> getAcademicKPIList() {
		return academicKPIList;
	}
	public void setAcademicKPIList(List<AcademicKPI> academicKPIList) {
		this.academicKPIList = academicKPIList;
	}
 
	public BigDecimal getMinPercent() {
		return minPercent;
	}
	public void setMinPercent(BigDecimal minPercent) {
		this.minPercent = minPercent;
	}
	public BigDecimal getMinHour() {
		return minHour;
	}
	public void setMinHour(BigDecimal minHour) {
		this.minHour = minHour;
	}
	public BigDecimal getMaxPercent() {
		return maxPercent;
	}
	public void setMaxPercent(BigDecimal maxPercent) {
		this.maxPercent = maxPercent;
	}
	public BigDecimal getMaxHour() {
		return maxHour;
	}
	public void setMaxHour(BigDecimal maxHour) {
		this.maxHour = maxHour;
	}
	public BigDecimal getLimitBase() {
		return limitBase;
	}
	public void setLimitBase(BigDecimal limitBase) {
		this.limitBase = limitBase;
	}
	public BigDecimal getTotalInPercentCompareBaseWorkType() {
		return totalInPercentCompareBaseWorkType;
	}
	public void setTotalInPercentCompareBaseWorkType(
			BigDecimal totalInPercentCompareBaseWorkType) {
		this.totalInPercentCompareBaseWorkType = totalInPercentCompareBaseWorkType;
	}
	public String getCompareBaseStatus() {
		return compareBaseStatus;
	}
	public void setCompareBaseStatus(String compareBaseStatus) {
		this.compareBaseStatus = compareBaseStatus;
	}
	public boolean isMinHourCal() {
		return minHourCal;
	}
	public void setMinHourCal(boolean minHourCal) {
		this.minHourCal = minHourCal;
	}
	public boolean isMaxHourCal() {
		return maxHourCal;
	}
	public void setMaxHourCal(boolean maxHourCal) {
		this.maxHourCal = maxHourCal;
	}
 
 
    
    

}
