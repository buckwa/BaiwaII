package com.buckwa.domain.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PagingBean implements Serializable{
	
	private int maxPageItems =16;
	private int maxIndexPages =10;
	private int totalItems ;
	private int limitItemFrom;
	private int limitItemTo;
	private List currentPageItem;
	private int offset =0;
	
	private Map mapObj;
	
	public PagingBean(){
		mapObj = new HashMap();
	}
	
	public void put(String key, Object in){
		mapObj.put(key, in);
	}
	
	public Object get(String key){
		return mapObj.get(key);
	}

	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
	public int getMaxPageItems() {
		return maxPageItems;
		//return offset+maxIndexPages;
	}
	public void setMaxPageItems(int maxPageItems) {
		this.maxPageItems = maxPageItems;
	}
	public int getMaxIndexPages() {
		 return maxIndexPages;
		//return offset+maxPageItems;
	}
	public void setMaxIndexPages(int maxIndexPages) {
		this.maxIndexPages = maxIndexPages;
	}
	public List getCurrentPageItem() {
		return currentPageItem;
	}
	public void setCurrentPageItem(List currentPageItem) {
		this.currentPageItem = currentPageItem;
	}

	public int getLimitItemFrom() {
		//return limitItemFrom;
		return this.offset;
	}

	public void setLimitItemFrom(int limitItemFrom) {
		this.limitItemFrom = limitItemFrom;
	}

	public int getLimitItemTo() {
		return this.offset+maxPageItems;
	}

	public void setLimitItemTo(int limitItemTo) {
		this.limitItemTo = limitItemTo;
	}
	
	

}
