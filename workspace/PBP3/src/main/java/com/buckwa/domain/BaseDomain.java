package com.buckwa.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.buckwa.util.BuckWaDateUtils;

public class BaseDomain implements Serializable {
	
	private static final long serialVersionUID = 8812087822695403338L;

	protected static final SimpleDateFormat sdf_th = new SimpleDateFormat("dd/MM/yyyy", new Locale("th", "TH"));
 
	protected static final SimpleDateFormat ddmmyyy_time_thai = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss",new Locale("th", "TH"));
	
	private String status;
	protected String rownum;
	protected java.sql.Timestamp createDate;
	protected java.sql.Timestamp updateDate;
	protected String createBy;
	protected String updateBy;
	
	protected String name;
	protected String code;
	protected String description;
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateDateStr() {		 
		if(createDate != null) {
			return BuckWaDateUtils.get_ddMMyyyy_thai_from_date(createDate);
		}else{
			return "";
		}
	}
	
	public String getCreateDateTimeStr() {		 
		if(createDate != null) {
			return BuckWaDateUtils.get_ddmmyyyy_time_thai_from_date(createDate);
		}else{
			return "";
		}
	}
	
	public String getUpdateDateStr() {		 
		if(updateDate != null) {
			return BuckWaDateUtils.get_ddMMyyyy_thai_from_date(updateDate);
		}else{
			return "";
		}
	}
	
	public String getUpdateDateTimeStr() {		 
		if(updateDate != null) {
			return BuckWaDateUtils.get_ddmmyyyy_time_thai_from_date(updateDate);
		}else{
			return "";
		}
	}
	
 
	public java.sql.Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.sql.Timestamp createDate) {
		this.createDate = createDate;
	}

	public java.sql.Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.sql.Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getRownum() {
		return rownum;
	}
	public void setRownum(String rownum) {
		this.rownum = rownum;
	}
 
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
