package com.buckwa.domain.admin;

import java.io.Serializable;
import java.util.List;

import com.buckwa.util.BuckWaConstants;

public class Menu implements Serializable{
 
	private Long menuId;
	private String code;
	private String desc;
	private String url;
	private String name;
	private boolean isActive;
	private Long parentId;
	
	private String status;
	private String statusTxt;
	private List<Menu> subMenuList;
	
	private String topOrder;
	private String downOrder;
	private Long topMenuId;
	private Long downMenuId;
	
	
	private String orderNo;

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

 

	public Long getTopMenuId() {
		return topMenuId;
	}

	public void setTopMenuId(Long topMenuId) {
		this.topMenuId = topMenuId;
	}

	public Long getDownMenuId() {
		return downMenuId;
	}

	public void setDownMenuId(Long downMenuId) {
		this.downMenuId = downMenuId;
	}

	public String getTopOrder() {
		return topOrder;
	}

	public void setTopOrder(String topOrder) {
		this.topOrder = topOrder;
	}

	public String getDownOrder() {
		return downOrder;
	}

	public void setDownOrder(String downOrder) {
		this.downOrder = downOrder;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getStatusTxt() {
		if("0".equals(this.status)){
		  return BuckWaConstants.ENABLE_TXT;	
		}else{
			 return BuckWaConstants.DISABLE_TXT;	
		}
		 
	}

	public void setStatusTxt(String statusTxt) {
		this.statusTxt = statusTxt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

 
 

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<Menu> getSubMenuList() {
		return subMenuList;
	}

	public void setSubMenuList(List<Menu> subMenuList) {
		this.subMenuList = subMenuList;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	 
}
