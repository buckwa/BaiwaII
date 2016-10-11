package com.buckwa.web.captcha.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.TextEscapeUtils;

import com.buckwa.domain.common.BuckWaRequest;
import com.buckwa.service.intf.admin.AdminUserService;



public class CommonAuthenticationFilter extends
		AbstractAuthenticationProcessingFilter {
	
	private static Logger logger = LoggerFactory.getLogger(CommonAuthenticationFilter.class);
	
	private static final String DEFAULT_FILTER_PROCESSES_URL = "/j_spring_security_check";
	private static final String POST = "POST";
	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "j_username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "j_password";
	public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY="j_captcha";
	public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";
	private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
	private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
	private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;
	


	protected CommonAuthenticationFilter() {
		super(DEFAULT_FILTER_PROCESSES_URL);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {
		logger.info("CommonAuthenticationFilter.attemptAuthentication method");
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		String captcha = obtainCaptcha(request);
		if (username == null) {
			username = "";
		}else{
			username=username+"@kmitl.ac.th";
		}
		if (password == null) {
			password = "";
		}
		if(captcha==null){
			captcha = "";
		}
		
		//logger.info("Validate Captcha...");
		//boolean validCaptcha = false;
		//validCaptcha = captchaService.validateResponseForID(request.getSession().getId(),captcha);
		
		//if(StringUtils.isBlank(username)||StringUtils.isBlank(password)||validCaptcha==false)
			if(StringUtils.isBlank(username)||StringUtils.isBlank(password) )
			throw new BadCredentialsException("Invalid username/password");
			
		username = username.trim();
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		
		HttpSession session = request.getSession(false);
		if (session != null || getAllowSessionCreation()) {
			request.getSession().setAttribute(
					SPRING_SECURITY_LAST_USERNAME_KEY,
					TextEscapeUtils.escapeEntities(username));
		}
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected void setDetails(HttpServletRequest request,
			UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource
				.buildDetails(request));
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,	FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		if (request.getMethod().equals(POST)) {
			super.doFilter(request, response, chain);
		} else {
			chain.doFilter(request, response);
		}
	}

	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter(usernameParameter);
	}

	protected String obtainPassword(HttpServletRequest request) {
		return request.getParameter(passwordParameter);
	}
	
	protected String obtainCaptcha(HttpServletRequest request) {
		return request.getParameter(captchaParameter);
	}
}