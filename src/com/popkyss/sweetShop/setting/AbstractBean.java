package com.popkyss.sweetShop.setting;

import java.io.Serializable;

public abstract class AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String getBEAN_PREFIX() {
		return WebAppSettings.getAppPrefix();
	}

	public static String getBEAN_SUFIX() {
		return WebAppSettings.getBeanSuffix();
	}

	private boolean novyVyber = true;

	public String getBeanNumber() {
		return getClass().getSimpleName().substring(3, 6);
	}

	public boolean isNovyVyber() {
		return this.novyVyber;
	}

	public void setNovyVyber(boolean novyVyber) {
		this.novyVyber = novyVyber;
	}
}