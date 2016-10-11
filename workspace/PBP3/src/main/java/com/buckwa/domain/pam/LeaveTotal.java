package com.buckwa.domain.pam;

import java.io.Serializable;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 18, 2012 3:37:13 PM
 */
public class LeaveTotal implements Serializable{
	private int year;
	private int month;
	private int day;
	private int dayTotal;
	
	
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getDayTotal() {
		return dayTotal;
	}
	public void setDayTotal(int dayTotal) {
		this.dayTotal = dayTotal;
	}
	
	
}

