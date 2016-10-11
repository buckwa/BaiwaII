package com.buckwa.service.impl.pbp;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buckwa.dao.intf.pbp.InstituteDao;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.service.intf.pbp.InstituteService;
import com.buckwa.util.BuckWaConstants;

@Service("instituteService") 
public class InstituteServiceImpl implements InstituteService {
	private static Logger logger = LoggerFactory.getLogger(InstituteServiceImpl.class);
	
	@Autowired
	private InstituteDao instituteDao;
 

	@Override	
	public BuckWaResponse recalculate(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		
		// String headUserName ,String academicYear,String status
		try{				 
			 
			String academicYear = (String)request.get("academicYear");
			 
		 instituteDao.recalculate(academicYear);
 	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
	 
		return response;
	}
	
}
