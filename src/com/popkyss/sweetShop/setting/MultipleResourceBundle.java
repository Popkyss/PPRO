package com.popkyss.sweetShop.setting;

import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class MultipleResourceBundle extends ResourceBundle {
	private static Logger log = Logger.getLogger(MultipleResourceBundle.class);

	private Locale locale;
	private List<String> bundleNames;

	public MultipleResourceBundle(Locale locale, List<String> bundleNames) {
		this.locale = locale;
		this.bundleNames = bundleNames;
	}

	public Enumeration<String> getKeys() {
		return null;
	}

	protected Object handleGetObject(String key) {
		if (this.locale == null) {
			if (log.isDebugEnabled()) {
				log.debug("Pri cteni klice " + key + " nebylo predano locale");
			}
			return null;
		}

		Object result = null;
		if (this.bundleNames != null) {
			for (String bundleName : this.bundleNames) {
				result = getFromBundle(this.locale, bundleName, key);
				if (result != null) {
					break;
				}
			}
		}
		if (result == null) {

			String bundleName = "StripesResources";
			result = getFromBundle(this.locale, bundleName, key);
		}
		return result;
	}

	private static String getFromBundle(Locale loc, String bundleName, String key) throws NullPointerException {
		if (StringUtils.isEmpty(bundleName)) {
			if (log.isDebugEnabled()) {
				log.debug("Pri cteni klice " + key + " nebyl zadan nazev bundleName");
			}
			return null;
		}

		ResourceBundle bundle = null;
		try {
			bundle = ResourceBundle.getBundle(bundleName, loc);
		} catch (MissingResourceException exc) {
			if (log.isTraceEnabled()) {
				log.trace("Nepodarilo se najit resource bundle s oznacenim " + bundleName + " pro locale " + loc
						+ ". Zkontrolujte nastaveni dostupnych bundleNames ve web.xml.");
			}
		}
		String result = null;
		if (bundle != null) {
			try {
				result = bundle.getString(key);
			} catch (MissingResourceException missingResourceException) {
			}
		}

		return result;
	}
}