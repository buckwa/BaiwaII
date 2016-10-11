package com.buckwa.service.impl.admin;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.buckwa.dao.intf.admin.AccessLogDao;
import com.buckwa.dao.intf.admin.LovHeaderDao;
import com.buckwa.dao.intf.pam.ImportProfileDao;
import com.buckwa.dao.intf.pam.PersonProfileDao;
import com.buckwa.domain.admin.User;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.service.intf.admin.AccessLogService;
import com.buckwa.service.intf.util.MailService;
import com.buckwa.util.BuckWaConstants;



@Service("accessLogService")
public class AccessLogServiceImpl implements AccessLogService {
	private static Logger logger = LoggerFactory.getLogger(AccessLogServiceImpl.class);

	@Autowired
	private MailService mailService;
	
	@Autowired
	private LovHeaderDao lovHeaderDao;
	
	@Autowired
	private ImportProfileDao importProfileDao;
	
	@Autowired
	private PlatformTransactionManager txManager;
 
	@Autowired
	private AccessLogDao accessLogDao;
	
	@Autowired
	private PersonProfileDao personProfileDao;
	
 
	@Override
	public BuckWaResponse getByOffset(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			logger.info(" getByOffset :");
			PagingBean pagingBean = (PagingBean) request.get("pagingBean");
			PagingBean returnBean = accessLogDao.getByOffset(pagingBean);
		 
			response.addResponse("pagingBean", returnBean);

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}

	 
	
}
