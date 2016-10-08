package baiwa.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import baiwa.config.SecurityConfig;

public class CustomLogoutHandler extends SecurityContextLogoutHandler {
	  Logger logger = LoggerFactory.getLogger(SecurityConfig.class);	
	//@Autowired
	//private AuditLogService auditLogService;
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		logger.info(" ############ LOGOUT ###########");
//		UserBean userBean = UserLoginUtils.getCurrentUser();
//		if (userBean != null) {
//			String actionDetails = "User ID " + userBean.getUsername() + " Logout on " + NsllUtils.getDateTimethai(new Date());
//			AuditLogInfo log = new AuditLogInfo(userBean.getDisplayName(), request.getRemoteAddr(), SCREEN_NAME.LOG_OUT, actionDetails);
//			log.setActionType(ACTION_AUDIT_LOG_DROPDOWN.LOGOUT);
//			auditLogService.insertLog(log);
//		}
//		
//		super.logout(request, response, authentication);

	}
	
}
