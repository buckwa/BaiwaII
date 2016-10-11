package com.buckwa.service.impl.userregistration;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.buckwa.dao.intf.userregistration.UserRegistrationDao;
import com.buckwa.domain.admin.User;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.mail.BuckWaMail;
import com.buckwa.service.intf.userregistration.UserRegistrationService;
import com.buckwa.service.intf.util.MailService;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaException;
import com.buckwa.util.BuckWaUtils;


@Service("userRegistrationService") 
public class UserRegistrationServiceImpl implements UserRegistrationService {
	private static Logger logger = LoggerFactory.getLogger(UserRegistrationServiceImpl.class);
	
	
	@Autowired
	private PlatformTransactionManager txManager;
	
	@Autowired
	private MailService mailService;
	
	
	@Autowired
	private UserRegistrationDao userRegistrationDao;

	@Override 
	public BuckWaResponse registerUser(BuckWaRequest request) {
		final BuckWaResponse response = new BuckWaResponse();
		try{	
	 
	
			final User user = (User)request.get("user");	 
//			@SuppressWarnings({ "unchecked", "rawtypes" })
//			boolean createResult =new TransactionTemplate(txManager).execute(new TransactionCallback() {
//				public Boolean doInTransaction(TransactionStatus status) {
//					logger.info(" status.isNewTransaction():"+status.isNewTransaction());
//					boolean returnResult  = false;
//					try {
//						logger.info("  Start in Transaction Create user name '"+user.getUsername()+"'");	
//						user.setSecureCode(BuckWaUtils.getSecureCode(user.getUsername()));
//						userRegistrationDao.create(user);					
//						returnResult = true;						
//					} catch (DuplicateKeyException ex) {
//						status.setRollbackOnly();
//						ex.printStackTrace();				
//						response.setStatus(BuckWaConstants.FAIL);
//						response.setErrorCode("E002");
//						returnResult = false;	
//					}catch(Exception ex){
//						status.setRollbackOnly();
//						ex.printStackTrace();
//						response.setStatus(BuckWaConstants.FAIL);
//						response.setErrorCode("E001");	
//						returnResult = false;	
//					}	
//					return returnResult;
//				}				
//			});
//			
//			// Send Mail ( Not in Transaction )
//					 
//			if(createResult){		
//				logger.info(" Create user name '"+user.getUsername()+"' Success, Try to send E-mail confirmation ");	
//				try{
//					BuckWaMail buckWaMail = new BuckWaMail();
//					buckWaMail.setSendTo(user.getEmail());
//					buckWaMail.setSendToName(user.getFirst_name());
//					buckWaMail.setUserSecureCode(user.getSecureCode());
//					mailService.sendMail(buckWaMail);
//					logger.info(" Send Mail Success");
//				}catch(Exception ex){
//					logger.info(" Send Mail Fail but not rollback user registration");
//					ex.printStackTrace();
//				}
//			}else{
//				logger.info(" Create user name  '"+user.getUsername()+"'  Fail !!! , No send E-mail confirmation");
//			} 	
 	 
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		} 
		response.setSuccessCode("S001");
		return response;
	}
	
	
	@Override 
	public BuckWaResponse enableUser(BuckWaRequest request) {
		final BuckWaResponse response = new BuckWaResponse();
		try{	
			final User user = (User)request.get("user");	 
//			@SuppressWarnings({ "unchecked", "rawtypes" })
//			boolean createResult =new TransactionTemplate(txManager).execute(new TransactionCallback() {
//				public Boolean doInTransaction(TransactionStatus status) {
//					logger.info(" status.isNewTransaction():"+status.isNewTransaction());
//					boolean returnResult  = false;
//					try {
//						logger.info("  Start in Transaction Create user name '"+user.getUsername()+"'");			 
//						userRegistrationDao.enableUser(user);					
//						returnResult = true;						
//						response.setSuccessCode("S003");
//					}catch(BuckWaException ex){
//						status.setRollbackOnly();
//						ex.printStackTrace();
//						response.setStatus(BuckWaConstants.FAIL);
//						response.setErrorCode(ex.getCode());	
//						returnResult = false;	
//					}	
//					return returnResult;
//				}				
//			});
		
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		} 
	
		return response;
	}
	
	
	@Override
	public BuckWaResponse getUserByUsername(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{
			String username = (String)request.get("username");				 
			User user = userRegistrationDao.getUserByUsername(username);							
			if(user!=null){
				response.addResponse("user",user);
			}			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}

		response.setSuccessCode("S001");
		return response;
	}	
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse updateRegisterUser(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		 			
			User user = (User)request.get("user");	 
			//boolean iseExist = userDao.isUserExist( user.getUsername());
			boolean iseExist = false;
			if(iseExist){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");		
			}else{
				userRegistrationDao.update(user);	
				response.setSuccessCode("S002");	
			}	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}


	@Override
	public BuckWaResponse isEmailAreadyRegistered(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try{		 			
			User user = (User)request.get("user");	 
			String firstNameRegisgered = userRegistrationDao.isEmailAreadyRegistered( user.getEmail());			 
			if(firstNameRegisgered==null){
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E015");		
			}else{
				//userRegistrationDao.update(user);	
				response.addResponse("firstNameRegisgered",firstNameRegisgered);
					
			}	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}
	
	

}
