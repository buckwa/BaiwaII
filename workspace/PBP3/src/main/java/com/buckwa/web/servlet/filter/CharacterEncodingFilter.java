package com.buckwa.web.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterEncodingFilter implements Filter {
	FilterConfig fc;
	//String encoding = "TIS-620";
	String encoding = "UTF-8";

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		this.fc = arg0;
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		arg0.setCharacterEncoding(encoding);
		arg1.setCharacterEncoding(encoding);
		arg2.doFilter(arg0, arg1);
	}

	public void destroy() {
		// TODO Auto-generated method stub
		this.fc = null;
	}

}
