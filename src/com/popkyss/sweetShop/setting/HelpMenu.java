package com.popkyss.sweetShop.setting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HelpMenu implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private List<HelpMenu> subMenus;

	public HelpMenu(String id) {
		this.id = id;
	}

	public List<HelpMenu> getSubMenus() {
		return this.subMenus;
	}

	public HelpMenu addSubmenu(HelpMenu subMenu) {
		if (this.subMenus == null) {
			this.subMenus = new ArrayList<HelpMenu>();
		}
		this.subMenus.add(subMenu);
		return this;
	}

	public boolean isSubMenuExist() {
		if (this.subMenus == null) {
			return false;
		}
		return (this.subMenus.size() > 0);
	}

	public String getId() {
		return this.id;
	}

	public boolean isHelpPageExist() {
		return BaseServiceFactory.getHelp().isHelpMenuCreated(String.valueOf(WebAppSettings.getAppPrefix()) + this.id,
				"hlp", LocaleAction.getLocale());
	}
}