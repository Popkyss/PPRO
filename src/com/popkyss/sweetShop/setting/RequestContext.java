package com.popkyss.sweetShop.setting;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RequestContext {
	private static ThreadLocal<HttpServletRequest> requestData = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> responseData = new ThreadLocal<HttpServletResponse>();

	public static void setHttpServletRequest(HttpServletRequest req) {
		requestData.set(req);
	}

	public static void setHttpServletResponse(HttpServletResponse res) {
		responseData.set(res);
	}

	public static HttpServletRequest getHttpServletRequest() {
		return requestData.get();
	}

	public static HttpServletResponse getHttpServletResponse() {
		return responseData.get();
	}

	public static HttpSession getHttpSession() throws IllegalStateException {
		HttpServletRequest req = getHttpServletRequest();
		if (req == null) {
			throw new IllegalStateException("HttpServletRequest is null");
		}
		return req.getSession();
	}
}