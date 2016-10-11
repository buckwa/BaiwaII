package com.buckwa.domain.demo;

import java.io.Serializable;

public class BudgetReport implements Serializable{
	
	private String unit;
	private String budget;
	private String spending;
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getBudget() {
		return budget;
	}
	public void setBudget(String budget) {
		this.budget = budget;
	}
	public String getSpending() {
		return spending;
	}
	public void setSpending(String spending) {
		this.spending = spending;
	}
	
 

}
