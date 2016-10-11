package com.buckwa.domain.pam.nodetree;

import java.math.BigDecimal;

/**
 * 
 @Author : Kroekpong Sakulchai
 * @Create : 27 ก.ย. 2555 16:39:00
 * 
 **/
public class EvaluateKpi extends Kpi {

	private String evaluateKpiId;
	private String personMapEvaluateId;
	private String personId;
	private BigDecimal defaultScore;
	private BigDecimal estimateScore;
	private BigDecimal firstLevelScore;
	private BigDecimal secondLevelScore;
	private String status;
	
	// for KpiSummaryReport
	private BigDecimal markLevelScore;
	private BigDecimal evaluateScore;
	private BigDecimal weightScore;

	public String getPersonMapEvaluateId() {
		return personMapEvaluateId;
	}

	public void setPersonMapEvaluateId(String personMapEvaluateId) {
		this.personMapEvaluateId = personMapEvaluateId;
	}

	public BigDecimal getDefaultScore() {
		return defaultScore;
	}

	public void setDefaultScore(BigDecimal defaultScore) {
		this.defaultScore = defaultScore;
	}

	public BigDecimal getEstimateScore() {
		return estimateScore;
	}

	public void setEstimateScore(BigDecimal estimateScore) {
		this.estimateScore = estimateScore;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getEvaluateKpiId() {
		return evaluateKpiId;
	}

	public void setEvaluateKpiId(String evaluateKpiId) {
		this.evaluateKpiId = evaluateKpiId;
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

	@Override
	public String toString() {
		return "EvaluateKpi [evaluateKpiId=" + evaluateKpiId
				+ ", personMapEvaluateId=" + personMapEvaluateId
				+ ", personId=" + personId + ", defaultScore=" + defaultScore
				+ ", estimateScore=" + estimateScore + ", firstLevelScore="
				+ firstLevelScore + ", secondLevelScore=" + secondLevelScore
				+ ", status=" + status + ", getMarkLevel()=" + getMarkLevel()
				+ ", getWeight()=" + getWeight() + ", getWeightTotal()="
				+ getWeightTotal() + ", getUnitDesc()=" + getUnitDesc()
				+ ", getKpiId()=" + getKpiId() + ", getCode()=" + getCode()
				+ ", getName()=" + getName() + ", getParentId()="
				+ getParentId() + ", getNumber()=" + getNumber()
				+ ", getIsLeave()=" + getIsLeave() + ", getMark()=" + getMark()
				+ ", getChildOrder()=" + getChildOrder()
				+ ", getKpiTemplateId()=" + getKpiTemplateId()
				+ ", getMarkType()=" + getMarkType() + ", getUnitId()="
				+ getUnitId() + ", getMarkTypeDesc()=" + getMarkTypeDesc()
				+ ", getYearId()=" + getYearId() + ", getCategoryId()="
				+ getCategoryId()
				+ ", getTarget()=" + getTarget() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}



}
