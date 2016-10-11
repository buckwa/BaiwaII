package com.buckwa.service.impl.pam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.pam.LeaveTypeDao;
import com.buckwa.domain.pam.LeaveType;
import com.buckwa.service.intf.pam.LeaveTypeService;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 1:46:25 PM
 */

@Service("leaveTypeService")
public class LeaveTypeServiceImpl  implements LeaveTypeService{
	
	@Autowired
	private LeaveTypeDao leaveTypeDao;
	
	@Override
	public List<LeaveType> getAll() {
		List<LeaveType> list = null;
		try{		
			list = leaveTypeDao.getAll();
		}catch(Exception ex){
			ex.printStackTrace();		
		}
		return list;
	}
}

