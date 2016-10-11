package com.buckwa.dao.intf.pam;

import java.util.Date;
import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Year;

public interface YearDao {
	
	public PagingBean getByOffset(PagingBean pagingBean);
	public Long create(Year domain);
	public void update(Year domain); 
	public List<Year> getAll();
	public Year getById(String id);
	public void deleteById(String id);
	public boolean isAlreadyUsege(String id);
	public boolean isExist(String name);	 
	public boolean isExistForUpdate(String name,Long id);
	public int getYearCurrent();
	
}
