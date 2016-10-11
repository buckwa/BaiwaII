package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.pam.EstimateUser;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:36:23 PM
 */
public interface EstimateUserDao {
	public EstimateUser create(EstimateUser obj) throws Exception;
	public void delete(Long estimateGroupId) throws Exception;
	public List<EstimateUser> estimateUserList(Long estimateGroupId,boolean view) ;
}

