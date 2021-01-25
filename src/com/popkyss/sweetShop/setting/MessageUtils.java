package com.popkyss.sweetShop.setting;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.FieldPosition;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

public class MessageUtils {
	public static final String NEW_LINE = System.getProperty("line.separator");

	private static Logger log = Logger.getLogger(MessageUtils.class);

	public static final String DEFAULT_RESOURCE = "global";

	public static Locale getRequestLocale() {
		return (Locale) RequestContext.getHttpSession().getAttribute("org.apache.struts.action.LOCALE");
	}

	@Deprecated
	public static String getRealPath(String source) {
		return RequestContext.getHttpSession().getServletContext().getRealPath(source);
	}

	public static URL getFileResourceURL(String path) throws MalformedURLException {
		return RequestContext.getHttpSession().getServletContext().getResource(path);
	}

	public static InputStream getFileResourceAsStream(String path) throws FileNotFoundException {
		InputStream stream = RequestContext.getHttpSession().getServletContext().getResourceAsStream(path);
		if (stream == null) {
			throw new FileNotFoundException("Nenalezen zrdoj na ceste " + path);
		}
		return stream;
	}

	public static boolean isFileResourceExists(String path) {
		InputStream stream = null;
		try {
			stream = RequestContext.getHttpSession().getServletContext().getResourceAsStream(path);
			return (stream != null);
		} catch (IllegalArgumentException e) {
			return false;
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					log.debug("Chyba pri zavirani proudu", e);
				}
			}
		}
	}

	public static ResourceBundle getResourceBundle(String resource) {
		return getResourceBundle(resource, getRequestLocale());
	}

	public static ResourceBundle getResourceBundle(String resource, Locale locale) {
		return ResourceBundle.getBundle(resource, locale);
	}

	public static String bundle(String resource, String key) {
		return bundle(resource, key, getRequestLocale());
	}

	public static String bundle(String key) {
		return bundle("global", key, getRequestLocale());
	}

	public static String bundle(String resource, String key, Locale locale) {
		String text = "";
		try {
			ResourceBundle bundle = getResourceBundle(resource, locale);
			text = bundle.getString(key);
		} catch (Exception e) {
			text = "???" + key + "???";
			log.warn("nepodarilo se nacist klic: " + key + " z resourceBundle: " + resource, e);
		}
		return text;
	}

	public static boolean bundleExists(String resource, String key) {
		return bundleExists(resource, key, getRequestLocale());
	}

	public static boolean bundleExists(String resource, String key, Locale locale) {
		try {
			ResourceBundle bundle = getResourceBundle(resource, locale);
			return bundle.containsKey(key);
		} catch (Exception e) {
			return false;
		}
	}

	public static String getZprava(String messageKey, Object... params) {
		return getZprava("global", messageKey, getRequestLocale(), params);
	}

	public static String getZprava(String resource, String messageKey, Locale locale, Object... params) {
		String template = bundle(resource, messageKey, locale);
		return naformatujZpravu(template, locale, params);
	}

	public static String naformatujZpravu(String template, Object... params) {
		return naformatujZpravu(template, getRequestLocale(), params);
	}

	public static String naformatujZpravu(String template, Locale locale, Object... params) {
		MessageFormat format = new MessageFormat(template, locale);
		return format.format(params, new StringBuffer(), (FieldPosition) null).toString();
	}
}