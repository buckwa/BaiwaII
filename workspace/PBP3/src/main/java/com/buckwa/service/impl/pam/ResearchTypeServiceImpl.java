package com.buckwa.service.impl.pam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.pam.ResearchTypeDao;
import com.buckwa.domain.pam.ResearchType;
import com.buckwa.service.intf.pam.ResearchTypeService;

/*
@Author : Wichien Prommese(Pe)
@Create : Aug 5, 2012 1:46:25 PM
 */

@Service("researchTypeService")
public class ResearchTypeServiceImpl  implements ResearchTypeService{
	
	@Autowired
	private ResearchTypeDao researchTypeDao;
	
	@Override
	public List<ResearchType> getAll() {
		List<ResearchType> list = null;
		try{		
			list = researchTypeDao.getAll();
		}catch(Exception ex){
			ex.printStackTrace();		
		}
		return list;
	}
}

