package com.buckwa.domain.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BuckWaRequest implements Serializable{

	private Map mapObj;
	
	public BuckWaRequest(){
		mapObj = new HashMap();
	}
	
	public void put(String key, Object in){
		mapObj.put(key, in);
	}
	
	public Object get(String key){
		return mapObj.get(key);
	}
	

}
