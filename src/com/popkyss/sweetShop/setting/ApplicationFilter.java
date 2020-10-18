package com.popkyss.sweetShop.setting;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApplicationFilter implements Filter {
	public static final String ACTION = "Action";

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String actionNew = request.getServletPath().substring(1, 7);

		request.setAttribute("Action", actionNew);
		request.setAttribute("page", String.valueOf(actionNew) + ".action");

		RequestContext.setHttpServletRequest(request);
		RequestContext.setHttpServletResponse(response);

		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void destroy() {
	}
}