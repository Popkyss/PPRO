package com.popkyss.sweetShop.setting;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class LocaleFilter implements Filter {
	private static Logger log = Logger.getLogger(LocaleFilter.class);

	public static final String REQUEST_LOCALE = "org.apache.struts.action.LOCALE";
	public static final String REQUEST_LANGUAGE = "request_locale";
	public static final String DEFAULT_LANGUAGE = "cs";
	private static final String LANGUAGE_PARAM_NAME = "language";

	private String language;

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		log.trace("Before -> request: " + req.getLocale() + ", response: " + res.getLocale());
		nastavZakladniLocale((HttpServletRequest) req, (HttpServletResponse) res);
		chain.doFilter(req, res);
		log.trace("After  -> request: " + req.getLocale() + ", response: " + res.getLocale());
	}

	protected void nastavZakladniLocale(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		Locale sessionLocale = (Locale) session.getAttribute(REQUEST_LOCALE);
		String sessionLanguage = (String) session.getAttribute(REQUEST_LANGUAGE);
		if (sessionLocale == null || sessionLanguage == null) {
			sessionLocale = new Locale(this.language);
			session.setAttribute(REQUEST_LOCALE, sessionLocale);
			session.setAttribute(REQUEST_LANGUAGE, this.language);
		}
		response.setLocale(sessionLocale);
	}

	public void destroy() {
	}

	public void init(FilterConfig conf) throws ServletException {
		String pp = conf.getInitParameter(LANGUAGE_PARAM_NAME);
		if (StringUtils.isEmpty(pp)) {
			this.language = "cs";
		} else {
			this.language = pp;
		}
	}
}