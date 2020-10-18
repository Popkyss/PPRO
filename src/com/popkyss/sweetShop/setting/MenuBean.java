package com.popkyss.sweetShop.setting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("menuBean")
public class MenuBean implements Serializable {
	private static final long serialVersionUID = -7036525022739469236L;
	@XStreamAsAttribute
	private String menu;
	@XStreamAsAttribute
	private String stranka;
	@XStreamAsAttribute
	private boolean selected;
	@XStreamAsAttribute
	private boolean isStandard = true;
	@XStreamAsAttribute
	private boolean isZamestnanec = false;
	@XStreamAsAttribute
	private boolean isZakaznik = false;
	@XStreamImplicit(itemFieldName = "subMenu")
	private Collection<MenuBean> subMenuBean = new ArrayList<MenuBean>();

	public MenuBean() {
	}

	public MenuBean(String menu, String stranka, boolean selected, boolean isStandard, boolean isZamestnanec, boolean isZakaznik) {
		this.menu = menu;
		this.stranka = stranka;
		this.selected = selected;
		this.isStandard = isStandard;
		this.isZamestnanec = isZamestnanec;
		this.isZakaznik = isZakaznik;
	}

	public String getMenu() {
		return this.menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getStranka() {
		return this.stranka;
	}

	public void setStranka(String stranka) {
		this.stranka = stranka;
	}

	public boolean isSelected() {
		return this.selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Collection<MenuBean> getSubMenuBean() {
		return this.subMenuBean;
	}

	public void setSubMenuBean(Collection<MenuBean> subMenuBean) {
		this.subMenuBean = subMenuBean;
	}

	public String getNazevMenu() {
		return PropertyReader.bundle("menu." + getMenu());
	}

	public String getCisloMenu() {
		return getMenu();
	}

	public boolean isStandard() {
		return this.isStandard;
	}
	
	public boolean isZamestnanec() {
		return this.isZamestnanec;
	}
	
	public boolean isZakaznik() {
		return this.isZakaznik;
	}
}
