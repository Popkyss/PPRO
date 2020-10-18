package com.popkyss.sweetShop.setting;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/locale.action")
public class LocaleAction extends AbstractAction {
	@DontValidate
	@DefaultHandler
	public Resolution execute() {
		HttpServletRequest request = getContext().getRequest();
		String language = request.getParameter("request_locale");
		if (language == null) {
			language = "cs";
		}

		String action = request.getParameter("action");
		Locale locale = new Locale(language);

		HttpSession session = request.getSession(true);
		session.setAttribute("org.apache.struts.action.LOCALE", locale);
		session.setAttribute("request_locale", language);
		response().setLocale(locale);

		return (Resolution) new RedirectResolution("/" + action + "?" + request.getQueryString());
	}

	public static Locale getLocale() {
		Locale locale = (Locale) RequestContext.getHttpSession().getAttribute("org.apache.struts.action.LOCALE");
		if (locale != null) {
			return locale;
		}
		return new Locale("cs");
	}
}