package com.buckwa.service.impl.admin;

import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buckwa.dao.intf.admin.AdminUserDao;
import com.buckwa.dao.intf.admin.LovHeaderDao;
import com.buckwa.dao.intf.pam.ImportProfileDao;
import com.buckwa.dao.intf.pam.PersonProfileDao;
import com.buckwa.domain.admin.User;
import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.domain.common.BuckWaResponse;
import com.buckwa.domain.common.LovDetail;
import com.buckwa.domain.common.PagingBean;
import com.buckwa.domain.pam.Person;
import com.buckwa.service.intf.admin.AdminUserService;
import com.buckwa.service.intf.util.MailService;
import com.buckwa.util.BeanUtils;
import com.buckwa.util.BuckWaConstants;
import com.buckwa.util.BuckWaDateUtils;
import com.buckwa.util.PAMConstants;



@Service("userService")
public class AdminUserServiceImpl implements AdminUserService {
	private static Logger logger = LoggerFactory.getLogger(AdminUserServiceImpl.class);

	@Autowired
	private MailService mailService;
	
	@Autowired
	private LovHeaderDao lovHeaderDao;
	
	@Autowired
	private ImportProfileDao importProfileDao;
	
	@Autowired
	private PlatformTransactionManager txManager;
	// private final TransactionTemplate transactionTemplate;

	// use constructor-injection to supply the PlatformTransactionManager

	// @ConstructorProperties({"transactionManager"})
	// public UserServiceImpl(PlatformTransactionManager txManager) {
	// Assert.notNull(txManager,
	// "The 'transactionManager' argument must not be null.");
	// this.transactionTemplate = new TransactionTemplate(txManager);
	// }

	@Autowired
	private AdminUserDao userDao;
	
	@Autowired
	private PersonProfileDao personProfileDao;
	

	@Override
	public BuckWaResponse createUser(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			User user = (User) request.get("user");
			boolean iseExist = userDao.isUserExist(user.getUsername());
			if (iseExist) {
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");
			} else {
				userDao.create(user);
				importProfileDao.create(user.getPerson());
				response.setSuccessCode("S001");
			}
		} catch (DuplicateKeyException dx) {
			dx.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E002");
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse updateUser(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			User user = (User) request.get("user");
			// boolean iseExist = userDao.isUserExist( user.getUsername());
			boolean iseExist = false;
			if (iseExist) {
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");
			} else {
				userDao.update(user);
				personProfileDao.update(user.getPerson());
				response.setSuccessCode("S002");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}
	

	
	

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse updateSignatureImagePath(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			User user = (User) request.get("user");

			userDao.updateSignatureImagePath(user);
			response.setSuccessCode("S002");

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}

	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse loginLoging(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			String userName = request.get("userName")+"";
			String loginStatus = request.get("loginStatus")+"";
			String clientIP = request.get("clientIP")+"";
				userDao.loginLoging(userName,loginStatus,clientIP); 

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse updateRegisterUser(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			User user = (User) request.get("user");
			boolean iseExist = false;
			if (iseExist) {
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E002");
			} else {
				userDao.updateRegister(user);
				response.setSuccessCode("S002");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}

	@Override
	public BuckWaResponse getUserByOffset(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			logger.info(" getUserByOffset :");
			PagingBean pagingBean = (PagingBean) request.get("pagingBean");
			PagingBean returnBean = userDao.getAllUserByOffset(pagingBean);
			List<User> userList = returnBean.getCurrentPageItem();
			for (User usrtmp : userList) {
				usrtmp.setEditable("Y");
			}
			response.addResponse("pagingBean", returnBean);

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}

	@Override
	public BuckWaResponse getUserByFacultyCodeOffset(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			logger.info(" getUserByOffset :");
			PagingBean pagingBean = (PagingBean) request.get("pagingBean");
			PagingBean returnBean = userDao.getUserByFacultyCodeOffset(pagingBean);
			List<User> userList = returnBean.getCurrentPageItem();
			for (User usrtmp : userList) {
				usrtmp.setEditable("Y");
			}
			response.addResponse("pagingBean", returnBean);

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}

	
	@Override
	public BuckWaResponse getUserDepartmentByOffset(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			logger.info(" getUserByOffset :");
			PagingBean pagingBean = (PagingBean) request.get("pagingBean");
			PagingBean returnBean = userDao.getUserDepartmentByOffset(pagingBean);
			List<User> userList = returnBean.getCurrentPageItem();
			for (User usrtmp : userList) {
				usrtmp.setEditable("Y");
			}
			response.addResponse("pagingBean", returnBean);

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}
	
	
	@Override
	public BuckWaResponse getUserById(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {

			String roleId = (String) request.get("roleId");
			logger.info(" getUserById :" + roleId);
			User role = userDao.getUserByUserId(roleId);
			if (role != null) {
				response.addResponse("role", role);
			} else {

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}

		// Success return S001
		response.setSuccessCode("S001");
		return response;
	}

	@Override
	public BuckWaResponse getUserByUsername(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			String username = (String) request.get("username");
			String academicYear = (String) request.get("academicYear");
			User user = userDao.getUserByUsername(username,academicYear);
			if (user != null) {
				response.addResponse("user", user);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}

		response.setSuccessCode("S001");
		return response;
	}
	
	
	@Override
	public BuckWaResponse getUserByUsernameForEdit(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			String username = (String) request.get("username");
			String academicYear = (String) request.get("academicYear");
			User user = userDao.getUserByUsername(username,academicYear);
			if (user != null) {
				response.addResponse("user", user);
				
				Person person = personProfileDao.getByUserId((long) user.getUser_id(),academicYear);
				
				// Set Date format
				person.setBirthdateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(person.getBirthdate()));
				person.setWorkingDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(person.getWorkingDate()));
				person.setAssignDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(person.getAssignDate()));
				person.setRetireDateStr(BuckWaDateUtils.get_ddMMyyyy_from_date(person.getRetireDate()));
				
				List<LovDetail> lovSexList           = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_SEX);
				List<LovDetail> lovEmployeeTypeList  = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_EMPLOYEE_TYPE);
				List<LovDetail> lovPositionList      = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_POSITION);
				List<LovDetail> lovWorkLineList      = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_WORK_LINE);
				List<LovDetail> lovFacultyList       = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_FACULTY);
				List<LovDetail> lovInsigniaList      = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_INSIGNIA);
				List<LovDetail> lovMarriedStatusList = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_MARRIED_STATUS);
				List<LovDetail> lovEducationList     = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_EDUCATION);
				List<LovDetail> lovWorkingStatusList = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_WORKING_STATUS);
				
				person.setLovSexList(lovSexList);
				person.setLovEmployeeTypeList(lovEmployeeTypeList);
				person.setLovPositionList(lovPositionList);
				person.setLovWorkLineList(lovWorkLineList);
				person.setLovFacultyList(lovFacultyList);
				person.setLovInsigniaList(lovInsigniaList);
				person.setLovMarriedStatusList(lovMarriedStatusList);
				person.setLovEducationList(lovEducationList);
				person.setLovWorkingStatusList(lovWorkingStatusList);
				
				
				user.setPerson(person);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}

		response.setSuccessCode("S001");
		return response;
	}
	
	
	@Override
	public BuckWaResponse getUserAndAssingDateByUsername(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			String username = (String) request.get("username");
			User user = userDao.getUserAndAssignDateByUsername(username);
			if (user != null) {
				response.addResponse("user", user);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}

		response.setSuccessCode("S001");
		return response;
	}

	// @Override
	// public BuckWaResponse registerUser(BuckWaRequest request) {
	// final BuckWaResponse response = new BuckWaResponse();
	// try{
	// // User Registration in transaction
	// // 1. Save User, and Role
	// // 2. Send Mail Confirmation
	//			
	//	
	// // final User user = (User)request.get("user");
	// // @SuppressWarnings({ "unchecked", "rawtypes" })
	// // boolean createResult =new TransactionTemplate(txManager).execute(new
	// TransactionCallback() {
	// // public Boolean doInTransaction(TransactionStatus status) {
	// // logger.info(" status.isNewTransaction():"+status.isNewTransaction());
	// // boolean returnResult = false;
	// // try {
	// //
	// logger.info("  Start in Transaction Create user name '"+user.getUsername()+"'");
	// // user.setSecureCode(BuckWaUtils.getSecureCode(user.getUsername()));
	// // userDao.create(user);
	// //
	// // returnResult = true;
	// // } catch (DuplicateKeyException ex) {
	// // status.setRollbackOnly();
	// // ex.printStackTrace();
	// // response.setStatus(BuckWaConstants.FAIL);
	// // response.setErrorCode("E002");
	// // returnResult = false;
	// // }catch(Exception ex){
	// // status.setRollbackOnly();
	// // ex.printStackTrace();
	// // response.setStatus(BuckWaConstants.FAIL);
	// // response.setErrorCode("E001");
	// // returnResult = false;
	// // }
	// // return returnResult;
	// // }
	// // });
	//			
	// // Send Mail ( Not in Transaction )
	// if(createResult){
	// logger.info(" Create user name '"+user.getUsername()+"' Success, Try to send E-mail confirmation ");
	// try{
	// BuckWaMail buckWaMail = new BuckWaMail();
	// buckWaMail.setSendTo(user.getEmail());
	// buckWaMail.setSendToName(user.getUsername());
	// buckWaMail.setUserSecureCode(user.getSecureCode());
	// mailService.sendMail(buckWaMail);
	// logger.info(" Send Mail Success");
	// }catch(Exception ex){
	// logger.info(" Send Mail Fail but not rollback user registration");
	// ex.printStackTrace();
	// }
	// }else{
	// logger.info(" Create user name  '"+user.getUsername()+"'  Fail !!! , No send E-mail confirmation");
	// }
	// 	
	// }catch(Exception ex){
	// ex.printStackTrace();
	// response.setStatus(BuckWaConstants.FAIL);
	// response.setErrorCode("E001");
	// }
	// response.setSuccessCode("S001");
	// return response;
	// }

	@Override
	public BuckWaResponse changePassword(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {

			User user = (User) request.get("user");
			logger.info(" changePassword :" + BeanUtils.getBeanString(user));
			userDao.changePassword(user);

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}

		response.setSuccessCode("S003");
		return response;
	}

	// @Override
	// public BuckWaResponse enableUser(BuckWaRequest request) {
	// final BuckWaResponse response = new BuckWaResponse();
	// try{
	// final User user = (User)request.get("user");
	// @SuppressWarnings({ "unchecked", "rawtypes" })
	// boolean createResult =new TransactionTemplate(txManager).execute(new
	// TransactionCallback() {
	// public Boolean doInTransaction(TransactionStatus status) {
	// logger.info(" status.isNewTransaction():"+status.isNewTransaction());
	// boolean returnResult = false;
	// try {
	// logger.info("  Start in Transaction Create user name '"+user.getUsername()+"'");
	// userDao.enableUser(user);
	// returnResult = true;
	// response.setSuccessCode("S003");
	// }catch(BuckWaException ex){
	// status.setRollbackOnly();
	// ex.printStackTrace();
	// response.setStatus(BuckWaConstants.FAIL);
	// response.setErrorCode(ex.getCode());
	// returnResult = false;
	// }
	// return returnResult;
	// }
	// });
	//		
	// }catch(Exception ex){
	// ex.printStackTrace();
	// response.setStatus(BuckWaConstants.FAIL);
	// response.setErrorCode("E001");
	// }
	//	
	// return response;
	// }

	public static void setLogger(Logger logger) {
		AdminUserServiceImpl.logger = logger;
	}

	public static Logger getLogger() {
		return logger;
	}

	@Override
	public BuckWaResponse enableUser(BuckWaRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuckWaResponse registerUser(BuckWaRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuckWaResponse delete(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			String username = (String) request.get("username");
			// Check Is Role are using
			// boolean isAreadyUse = userDao.isRoleAlreadyUsege(roleId);
			boolean isAreadyUse = false;
			if (isAreadyUse) {
				response.setStatus(BuckWaConstants.FAIL);
				response.setErrorCode("E010");
			} else {
				userDao.delete(username);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}

	@Override
	public BuckWaResponse changeStatus(BuckWaRequest request) {
		logger.info(" ");
		BuckWaResponse response = new BuckWaResponse();
		try {
			String username = (String) request.get("username");
			String currentStatus = (String) request.get("currentStatus");
			boolean toStatus = false;
			if ("false".equals(currentStatus)) {
				toStatus = BuckWaConstants.ACTIVE_BOOLEAN;
			} else {
				toStatus = BuckWaConstants.INACTIVE_BOOLEAN;
			}
			userDao.changeStatus(username, toStatus);

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		response.setSuccessCode("S001");
		return response;
	}

	@Override
	public BuckWaResponse resetPass(BuckWaRequest request) {
		logger.info(" ");
		BuckWaResponse response = new BuckWaResponse();
		try {
			String username = (String) request.get("username");
			String newPass = (String) request.get("newPass");
			userDao.resetPass(username, newPass);
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		response.setSuccessCode("S001");
		return response;

	}
	
	@Override
	public BuckWaResponse getUserAll(BuckWaRequest request){
		logger.info("LeaveQuotaServiceImpl.getUserAll");
		BuckWaResponse response = new BuckWaResponse();
		try{					
			List<User> userList = userDao.getAll();
			response.addResponse("userList",userList);
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");			
		}
		return response;
	}

	@Override
	public BuckWaResponse initCreateUser() {
		
		List<LovDetail> lovSexList           = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_SEX);
		List<LovDetail> lovEmployeeTypeList  = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_EMPLOYEE_TYPE);
		List<LovDetail> lovPositionList      = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_POSITION);
		List<LovDetail> lovWorkLineList      = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_WORK_LINE);
		List<LovDetail> lovFacultyList       = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_FACULTY);
		List<LovDetail> lovInsigniaList      = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_INSIGNIA);
		List<LovDetail> lovMarriedStatusList = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_MARRIED_STATUS);
		List<LovDetail> lovEducationList     = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_EDUCATION);
		List<LovDetail> lovWorkingStatusList = lovHeaderDao.getDetailListByCode(PAMConstants.LOV_CODE_WORKING_STATUS);
		
		Person person = new Person();
		person.setLovSexList(lovSexList);
		person.setLovEmployeeTypeList(lovEmployeeTypeList);
		person.setLovPositionList(lovPositionList);
		person.setLovWorkLineList(lovWorkLineList);
		person.setLovFacultyList(lovFacultyList);
		person.setLovInsigniaList(lovInsigniaList);
		person.setLovMarriedStatusList(lovMarriedStatusList);
		person.setLovEducationList(lovEducationList);
		person.setLovWorkingStatusList(lovWorkingStatusList);
		
		BuckWaResponse response = new BuckWaResponse();
		response.addResponse("person", person);
		
		return response;
	}
	
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BuckWaResponse updateLeaveAccumulate(BuckWaRequest request) {
		BuckWaResponse response = new BuckWaResponse();
		try {
			User user = (User) request.get("user");		 
			boolean iseExist = false;
 
			userDao.update(user);
			user.getPerson().setLeaveAccumulate(user.getLeaveAccumulate());
			personProfileDao.updateLeaveAccumulate(user.getPerson());
			response.setSuccessCode("S002");
		 

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(BuckWaConstants.FAIL);
			response.setErrorCode("E001");
		}
		return response;
	}

	
}
