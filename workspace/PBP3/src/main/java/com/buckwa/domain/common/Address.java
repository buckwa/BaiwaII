package com.buckwa.domain.common;

import com.buckwa.domain.BaseDomain;

public class Address extends BaseDomain{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -600412980502898335L;
	private Long addressId;
	private String addresLine1;
	private String addresLine2;
	private String zipCode;
	private String telNo;
	private String faxNo;
	private String remark;
	private String latitude;
	private String longitude ;
	
	private String  no;
	private String  soi;
	private String  road;
	private String  aumpur;
	private String  province;
	private String  tumbon;
	
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public String getAddresLine1() {
		return addresLine1;
	}
	public void setAddresLine1(String addresLine1) {
		this.addresLine1 = addresLine1;
	}
	public String getAddresLine2() {
		return addresLine2;
	}
	public void setAddresLine2(String addresLine2) {
		this.addresLine2 = addresLine2;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getSoi() {
		return soi;
	}
	public void setSoi(String soi) {
		this.soi = soi;
	}
	public String getRoad() {
		return road;
	}
	public void setRoad(String road) {
		this.road = road;
	}
	public String getAumpur() {
		return aumpur;
	}
	public void setAumpur(String aumpur) {
		this.aumpur = aumpur;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getTumbon() {
		return tumbon;
	}
	public void setTumbon(String tumbon) {
		this.tumbon = tumbon;
	}


	
	
}
