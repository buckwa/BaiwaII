package com.buckwa.dao.intf.pam;

import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.EstimateGroup;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:36:23 PM
 */
public interface EstimateGroupDao {
	public EstimateGroup create(EstimateGroup obj) throws Exception;
	public EstimateGroup update(EstimateGroup obj) throws Exception;
	public void delete(Long id) throws Exception;
	public EstimateGroup getById(Long id);
	public PagingBean getAllByOffset(PagingBean pagingBean);
	public boolean checkNameAlready(Long id,String name);
}

