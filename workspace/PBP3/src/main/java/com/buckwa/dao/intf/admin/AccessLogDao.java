package com.buckwa.dao.intf.admin;

import java.util.List;

import com.buckwa.domain.admin.User;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.util.BuckWaException;

public interface AccessLogDao {
	
 
	public PagingBean getByOffset(PagingBean pagingBean);		 
	 
	
}
