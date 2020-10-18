package com.popkyss.sweetShop.setting;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

public final class PropertyReader {
	public static final String LNG_PATH = "lng";
	public static final String LNG_GLOBAL = "global";
	public static final String KONSTANTY_RESOURCE = "konstanty";

	public static String getLNG_PREFIX() {
		return WebAppSettings.getAppPrefix();
	}

	public static String bundle(String resource, String key) {
		String text = "";
		try {
			ResourceBundle bundle = getResourceBundle(resource);
			text = bundle.getString(key);
		} catch (Exception e) {
			text = "???" + key + "???";
		}
		return text;
	}

	public static boolean bundleExists(String resource, String key) {
		try {
			ResourceBundle bundle = getResourceBundle(resource);
			return bundle.containsKey(key);
		} catch (Exception e) {
			return false;
		}
	}

	public static String bundleToHtml(String resource, String key) {
		return StringUtils.htmlFilter(bundle(resource, key));
	}

	public static String bundle(String key) {
		return bundle("global", key);
	}

	public static String bundleToHtml(String key) {
		return StringUtils.htmlFilter(bundle("global", key));
	}

	public static ResourceBundle getResourceBundle(String resource) {
		return ResourceBundle.getBundle("lng." + resource, MessageUtils.getRequestLocale());
	}

	public static Map<String, String> getResourceBundleMap(String resource) {
		Map<String, String> map = new HashMap<String, String>();
		ResourceBundle rb = getResourceBundle(resource);
		if (rb != null) {
			Enumeration<String> keys = getResourceBundle(resource).getKeys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				map.put(key, (String) rb.getObject(key));
			}
		}
		return map;
	}

	public static Properties getProperties(String resource) {
		ResourceBundle rb = getResourceBundle(resource);
		Enumeration<String> es = rb.getKeys();
		Properties popisky = new Properties();

		while (es.hasMoreElements()) {
			String key = es.nextElement();
			popisky.put(key, rb.getString(key));
		}
		return popisky;
	}

	public static String getKonstantaPopisek(Konstanty.IVycet konstanta) {
		return getKonstantaPopisek(konstanta, "konstanty");
	}

	public static String getKonstantaPopisek(Konstanty.IVycet konstanta, String resource) {
		return getKonstantaPopisek(konstanta, resource, null);
	}

	public static String getKonstantaPopisek(Konstanty.IVycet konstanta, String resource, String property) {
		String key, clName = StringUtils.getSimpleClassName(konstanta.getClass()).toLowerCase();

		if (StringUtils.isEmpty(property)) {
			key = String.valueOf(clName) + "." + konstanta.getOrdinal();
		} else {
			key = String.valueOf(clName) + "." + konstanta.getOrdinal() + "." + property;
		}

		if (bundleExists(resource, key)) {
			ResourceBundle rb = getResourceBundle(resource);
			return rb.getString(key);
		}
		return null;
	}
}