package com.buckwa.web.ws;

import java.io.Serializable;

public class CustomerCountResponse implements Serializable{
	
	private int customerCount;

	public int getCustomerCount() {
		return customerCount;
	}

	public void setCustomerCount(int customerCount) {
		this.customerCount = customerCount;
	}

	
	
}
