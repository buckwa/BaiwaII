package com.buckwa.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.web.servlet.view.RedirectView;

import com.buckwa.util.BuckWaUtils;

public class AccessDeniedHandler extends AccessDeniedHandlerImpl {
	private static final String LOG_TEMPLATE = "AccessDeniedHandler:  User attempted to access a resource for which they do not have permission.  User %s attempted to access %s";

	@Override
	public void handle(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, AccessDeniedException ex)
			throws IOException, ServletException {
		logger.info(" ### In AccessDeniedHandler.handle ######"); 
		
		String url = "";
		if(BuckWaUtils.isRole("ROLE_ADMIN")){
			url =  "/securityAdminAccessDenied.htm";
		} else 	if(BuckWaUtils.isRole("ROLE_DEAN")){
			url =  "/securityDeanAccessDenied.htm"; 
		}else if(BuckWaUtils.isRole("ROLE_USER")){
			url =  "/securityUserAccessDenied.htm";
		} else{
			url =  "/securityUserAccessDenied.htm";
		}
		
		logger.info(" ### AccessDeniedHandler Forword to:"+url); 
		
		setErrorPage(url);
		super.handle(httpRequest, httpResponse, ex);
	}

}