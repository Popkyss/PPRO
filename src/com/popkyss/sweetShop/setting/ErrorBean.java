package com.popkyss.sweetShop.setting;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class ErrorBean implements Serializable {
	private static final long serialVersionUID = -3848456744233893158L;
	private static final Logger log = Logger.getLogger(ErrorBean.class);

	public static final String SESSION_SPACE = "errorBean";

	private Throwable throwable = new Exception("Unknown error");

	private Date lastErrorDate;

	private String action = "Unknown action";

	private String params = "none";

	private String method = "unknown";

	public ErrorBean() {
	}

	private ErrorBean(Throwable throwable, String action, String params, String method) {
		this.throwable = throwable;
		this.action = action;
		this.params = params;
		this.method = method;
		this.lastErrorDate = new Date();
	}

	public static void createErrorBean(Throwable throwable, HttpServletRequest request) {
		log.error("V aplikaci byla zachycena chyba: \n", throwable);
		String action = request.getServletPath().substring(0, 7);

		String temp01 = request.getQueryString();
		String[] temp02 = (String[]) null;
		String method = "Unknown method";
		if (temp01 != null) {
			temp02 = temp01.split("=");
		}
		if (temp02 != null) {
			method = temp02[0];
		}
		ErrorBean eb = new ErrorBean(throwable, action, request.getQueryString(), method);
		request.getSession().setAttribute("errorBean", eb);
	}

	public Throwable getException() {
		return this.throwable;
	}

	public void setException(Throwable throwable) {
		this.throwable = throwable;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getParams() {
		return this.params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setLastErrorDate(Date lastErrorDate) {
		this.lastErrorDate = lastErrorDate;
	}

	public String getLastErrorDate() {
		if (this.lastErrorDate == null) {
			return (new Date()).toString();
		}
		return this.lastErrorDate.toString();
	}

	public String getMessage() {
		return StringUtils.stringToHtml(this.throwable.getMessage());
	}

	public String getCause() {
		try {
			return StringUtils.stringToHtml(this.throwable.getCause().toString());
		} catch (Exception e) {
			return "";
		}
	}
}