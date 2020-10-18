package com.popkyss.sweetShop.setting;

import java.io.Serializable;


public class SettingsDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String appFolderName = "popkyssWeb";
	private String appPrefix = "pok";
	private String beanPrefix = "Pok";
	private String beanSuffix = "bean";
	private String urlSuffix = ".action";
	private String errorPageNumber = "999";
	private String welcomePageNumber = "000";
	private String[] supportedLanguages = new String[] { "cs" };
	
	
	public String getAppPrefix() {
		return this.appPrefix;
	}

	public void setAppPrefix(String appPrefix) {
		if (StringUtils.isEmpty(appPrefix)) {
			throw new RuntimeException("Nenastaven aplikacni prefix, aplikace nemuze byt spustena!");
		}
		this.appPrefix = appPrefix;
	}

	public String getBeanPrefix() {
		return this.beanPrefix;
	}

	public void setBeanPrefix(String beanPrefix) {
		if (!StringUtils.isEmpty(beanPrefix)) {
			this.beanPrefix = beanPrefix;
		}
	}

	public String getBeanSuffix() {
		return this.beanSuffix;
	}

	public void setBeanSuffix(String beanSuffix) {
		if (!StringUtils.isEmpty(beanSuffix)) {
			this.beanSuffix = beanSuffix;
		}
	}

	public String getUrlSuffix() {
		return this.urlSuffix;
	}

	public void setUrlSuffix(String urlSuffix) {
		if (!StringUtils.isEmpty(urlSuffix)) {
			this.urlSuffix = urlSuffix;
		}
	}

	public String getErrorPageNumber() {
		return this.errorPageNumber;
	}

	public void setErrorPageNumber(String errorPageNumber) {
		if (StringUtils.isInteger(errorPageNumber)) {
			this.errorPageNumber = errorPageNumber;
		}
	}

	public String getWelcomePageNumber() {
		return this.welcomePageNumber;
	}

	public void setWelcomePageNumber(String welcomePageNumber) {
		if (StringUtils.isInteger(welcomePageNumber)) {
			this.welcomePageNumber = welcomePageNumber;
		}
	}

	public String getAppFolderName() {
		return this.appFolderName;
	}

	public void setAppFolderName(String appFolderName) {
		this.appFolderName = appFolderName;
	}

	public String[] getSupportedLanguages() {
		return this.supportedLanguages;
	}

	public void setSupportedLanguages(String[] supportedLanguages) {
		this.supportedLanguages = supportedLanguages;
	}

	public String toString() {
		return "\n\tappFolderName=" + this.appFolderName + "\n\tappPrefix=" + this.appPrefix + "\n\tbeanPrefix="
				+ this.beanPrefix + "\n\tbeanSuffix=" + this.beanSuffix + "\n\turlSuffix=" + this.urlSuffix
				+ "\n\terrorPageNumber=" + this.errorPageNumber + "\n\twelcomePageNumber=" + this.welcomePageNumber;
	}
}