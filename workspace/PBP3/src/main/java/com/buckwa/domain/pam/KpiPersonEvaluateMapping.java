package com.buckwa.domain.pam;

import java.math.BigDecimal;

import com.buckwa.domain.BaseDomain;

public class KpiPersonEvaluateMapping extends BaseDomain {

	private Long kpiId;
	private Long evaluateKpiId;
	private String personMapEvaluateId;
	private BigDecimal estimateScore;
	private BigDecimal defaultScore;
	private BigDecimal kpiMarkScore;
	private BigDecimal flag_cal;
	private String cal_value;
	private BigDecimal weight;

	public Long getKpiId() {
		return kpiId;
	}

	public void setKpiId(Long kpiId) {
		this.kpiId = kpiId;
	}

	public BigDecimal getKpiMarkScore() {
		return kpiMarkScore;
	}

	public void setKpiMarkScore(BigDecimal kpiMarkScore) {
		this.kpiMarkScore = kpiMarkScore;
	}

	public String getPersonMapEvaluateId() {
		return personMapEvaluateId;
	}

	public void setPersonMapEvaluateId(String personMapEvaluateId) {
		this.personMapEvaluateId = personMapEvaluateId;
	}

	public Long getEvaluateKpiId() {
		return evaluateKpiId;
	}

	public void setEvaluateKpiId(Long evaluateKpiId) {
		this.evaluateKpiId = evaluateKpiId;
	}

	public BigDecimal getEstimateScore() {
		return estimateScore;
	}

	public void setEstimateScore(BigDecimal estimateScore) {
		this.estimateScore = estimateScore;
	}

	public BigDecimal getDefaultScore() {
		return defaultScore;
	}

	public void setDefaultScore(BigDecimal defaultScore) {
		this.defaultScore = defaultScore;
	}

	public BigDecimal getFlag_cal() {
		return flag_cal;
	}

	public void setFlag_cal(BigDecimal flag_cal) {
		this.flag_cal = flag_cal;
	}

	public String getCal_value() {
		return cal_value;
	}

	public void setCal_value(String cal_value) {
		this.cal_value = cal_value;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "KpiPersonEvaluateMapping [kpiId=" + kpiId + ", evaluateKpiId="
				+ evaluateKpiId + ", personMapEvaluateId="
				+ personMapEvaluateId + ", estimateScore=" + estimateScore
				+ ", defaultScore=" + defaultScore + ", kpiMarkScore="
				+ kpiMarkScore + ", flag_cal=" + flag_cal + ", cal_value="
				+ cal_value + ", weight=" + weight + "]";
	}

}
