package com.buckwa.dao.impl.uitl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service("rejectSubjectUtil") 
public class RejectSubjectUtil {
	
	private Map<String,String> rejectMap = new HashMap<>();
	
	private Map<String,String> chumRegIdMappingMap = new HashMap<>();

	public Map<String, String> getRejectMap() {
		return rejectMap;
	}

	public void setRejectMap(Map<String, String> rejectMap) {
		this.rejectMap = rejectMap;
	}

	public Map<String, String> getChumRegIdMappingMap() {
		return chumRegIdMappingMap;
	}

	public void setChumRegIdMappingMap(Map<String, String> chumRegIdMappingMap) {
		this.chumRegIdMappingMap = chumRegIdMappingMap;
	}
	
	
}
