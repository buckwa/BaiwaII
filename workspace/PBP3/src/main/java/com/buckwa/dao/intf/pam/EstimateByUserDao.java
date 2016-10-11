package com.buckwa.dao.intf.pam;

import java.util.List;

import com.buckwa.domain.pam.EstimateByUser;
import com.buckwa.domain.pam.Person;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 2:36:23 PM
 */
public interface EstimateByUserDao {
	public EstimateByUser create(EstimateByUser obj) throws Exception;
	public void delete(Long estimateGroupId) throws Exception;
	public List<EstimateByUser> estimateByUserList(Long estimateGroupId,int type,boolean view) ;
	
	public List<Person> getEstimateUserList(Long byUserId);
}

