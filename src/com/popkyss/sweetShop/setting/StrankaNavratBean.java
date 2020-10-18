package com.popkyss.sweetShop.setting;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("strankaNavrat")
public class StrankaNavratBean implements Serializable {
	private static final long serialVersionUID = -4587469655467629281L;
	@XStreamAsAttribute
	private String stranka;

	public StrankaNavratBean() {
	}

	public StrankaNavratBean(String stranka) {
		this.stranka = stranka;
	}

	public String getStranka() {
		return this.stranka;
	}

	public void setStranka(String stranka) {
		this.stranka = stranka.substring(0, 6);
	}

	public String getUrl() {
		return String.valueOf(getStranka()) + ".action";
	}

	public String getAction() {
		String actionClass = "com.popkyss." + WebAppSettings.getAppFolderName() + ".stripes.action."
				+ WebAppSettings.getBeanPrefix() + this.stranka.substring(3, 6);
		return actionClass;
	}

	public void setUrl(String url) {
	}
	
	public String getNazevStranka() {
		return PropertyReader.bundle(String.valueOf(PropertyReader.getLNG_PREFIX()) + getStranka().substring(3, 6),
				"pageName");
	}

	public String toString() {
		return this.stranka;
	}
}