package com.buckwa.web.ws;

import java.io.Serializable;

public class CustomerCountRequest implements Serializable{
	
	private String customerName;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	

}
