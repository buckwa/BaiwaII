package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Semester;

public interface SemesterDao {
	public PagingBean getByOffset(PagingBean pagingBean);
	public Long create(Semester domain);
	public void update(Semester domain); 
	public List<Semester> getAll();
	public Semester getById(String id);
	public void deleteById(String id);
	public boolean isAlreadyUsege(String id);
	public boolean isExist(String name, String yearId);	 
	public boolean isExistForUpdate(String name,Long id);
	
	public List<Semester> getByYearId(Long id);
	
	
}
