package com.popkyss.sweetShop.setting;

import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

public final class WebAppSettings {

	private static final Logger log = Logger.getLogger(WebAppSettings.class);

	private static final String PROPERTIES_PATH = "resources/web/settings.properties";

	private static SettingsDto sd;

	private static SettingsDto loadSettings() {
		SettingsDto sdTemp = new SettingsDto();
		URL url = WebAppSettings.class.getClassLoader().getResource(PROPERTIES_PATH);
		Properties prop = CommonsIO.makeProperties(url);

		if (url != null) {
			sdTemp.setAppFolderName(prop.getProperty("appFolderName", "popkyssWeb"));
			sdTemp.setAppPrefix(prop.getProperty("appPrefix", "pok"));
			sdTemp.setBeanPrefix(prop.getProperty("beanPrefix", "Pok"));
			sdTemp.setBeanSuffix(prop.getProperty("beanSuffix", "Bean"));
			sdTemp.setUrlSuffix(prop.getProperty("urlSuffix", ".action"));
			sdTemp.setErrorPageNumber(prop.getProperty("errorPageNumber", "999"));
			sdTemp.setWelcomePageNumber(prop.getProperty("welcomePageNumber", "000"));
			sdTemp.setSupportedLanguages(prop.getProperty("supportedLanguages", "cs").split(","));

		} else {
			log.warn("Nepodarilo se nacist nastaveni aplikace, soubor settings.properties nebyl nazelen v resources!");
		}

		log.info("Aplikace je nastavena na tyto hodnoty: WebAppSettings " + sdTemp.toString());
		return sdTemp;
	}

	public String toString() {
		return "WebAppSettings " + sd.toString();
	}

	public static synchronized SettingsDto getSd() {
		if (sd == null) {
			sd = loadSettings();
		}
		return sd;
	}

	public static synchronized void setSd(SettingsDto sd) {
		WebAppSettings.sd = sd;
	}

	public static String getAppPrefix() {
		return getSd().getAppPrefix();
	}

	public static String getBeanPrefix() {
		return getSd().getBeanPrefix();
	}

	public static String getBeanSuffix() {
		return getSd().getBeanSuffix();
	}

	public static String getUrlSuffix() {
		return getSd().getUrlSuffix();
	}

	public static String getErrorPageNumber() {
		return getSd().getErrorPageNumber();
	}

	public static String getWelcomePageNumber() {
		return getSd().getWelcomePageNumber();
	}

	public static String getAppFolderName() {
		return getSd().getAppFolderName();
	}

	public static String[] getSupportedLanguages() {
		return getSd().getSupportedLanguages();
	}
}