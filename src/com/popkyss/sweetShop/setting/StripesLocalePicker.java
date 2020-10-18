package com.popkyss.sweetShop.setting;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import net.sourceforge.stripes.localization.DefaultLocalePicker;

public class StripesLocalePicker extends DefaultLocalePicker {
	public Locale pickLocale(HttpServletRequest request) {
		Locale locale = (Locale) request.getSession().getAttribute("org.apache.struts.action.LOCALE");
		if (locale != null) {
			return locale;
		}
		return super.pickLocale(request);
	}
}
