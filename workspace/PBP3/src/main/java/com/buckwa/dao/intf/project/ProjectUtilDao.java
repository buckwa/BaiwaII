package com.buckwa.dao.intf.project;

import java.util.List;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.project.Util;

public interface ProjectUtilDao {
	

 
	public void update(Util util);
	public void create(Util util);
	public PagingBean getByOffset(PagingBean pagingBean);
	public List<Util> getAllUtil_N();
	public List<Util> getAllUtil_D();
	public Util getUtilById(String utilId);
	public Util getUtilandDetailDesignById(String utilId);
	
	public void deleteUtilById(String utilId);
 
	public boolean isUtilAlreadyUsege(String utilId);
	 
 
	
}
