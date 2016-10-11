package com.buckwa.domain.pam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/*
@Author : Taechapon Himarat (Su)
@Create : Nov 11, 2012 4:13:43 PM
 */
public class ReportKpiSummary implements Serializable {
	
	private static final long serialVersionUID = -7706033693191524450L;
	
	private Long kpiId;
	private String no;
	private String name;
	private String nameId;// id of name object
	private String nameWithNo;
	private String markDesc;
	private BigDecimal targetScore;
	private BigDecimal uploadScore;
	private BigDecimal firstLevelScore;
	private BigDecimal secondLevelScore;
	private BigDecimal weight;
	private MarkLevel markLevel;
	private BigDecimal markLevelScore;
	private BigDecimal evaluateScore;
	private BigDecimal weightScore;
	private List<ReportKpiSummary> childList;
	
	
	public Long getKpiId() {
		return kpiId;
	}
	
	public void setKpiId(Long kpiId) {
		this.kpiId = kpiId;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public String getNameWithNo() {
		return nameWithNo;
	}

	public void setNameWithNo(String nameWithNo) {
		this.nameWithNo = nameWithNo;
	}

	public String getMarkDesc() {
		return markDesc;
	}

	public void setMarkDesc(String markDesc) {
		this.markDesc = markDesc;
	}

	public BigDecimal getUploadScore() {
		return uploadScore;
	}

	public void setUploadScore(BigDecimal uploadScore) {
		this.uploadScore = uploadScore;
	}
	
	public BigDecimal getFirstLevelScore() {
		return firstLevelScore;
	}

	public void setFirstLevelScore(BigDecimal firstLevelScore) {
		this.firstLevelScore = firstLevelScore;
	}

	public BigDecimal getSecondLevelScore() {
		return secondLevelScore;
	}

	public void setSecondLevelScore(BigDecimal secondLevelScore) {
		this.secondLevelScore = secondLevelScore;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getTargetScore() {
		return targetScore;
	}

	public void setTargetScore(BigDecimal targetScore) {
		this.targetScore = targetScore;
	}

	public MarkLevel getMarkLevel() {
		return markLevel;
	}

	public void setMarkLevel(MarkLevel markLevel) {
		this.markLevel = markLevel;
	}

	public BigDecimal getMarkLevelScore() {
		return markLevelScore;
	}

	public void setMarkLevelScore(BigDecimal markLevelScore) {
		this.markLevelScore = markLevelScore;
	}

	public BigDecimal getEvaluateScore() {
		return evaluateScore;
	}

	public void setEvaluateScore(BigDecimal evaluateScore) {
		this.evaluateScore = evaluateScore;
	}

	public BigDecimal getWeightScore() {
		return weightScore;
	}

	public void setWeightScore(BigDecimal weightScore) {
		this.weightScore = weightScore;
	}

	public List<ReportKpiSummary> getChildList() {
		return childList;
	}

	public void setChildList(List<ReportKpiSummary> childList) {
		this.childList = childList;
	}
	
	
	// Special for show in jsp
	public String getNameWithNoHtml() {
		String name = "";
		if (nameWithNo != null) {
			name = nameWithNo.replaceAll(" ", "\u00A0");
		}
		return name;
	}
}
