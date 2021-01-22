package com.popkyss.sweetShop.setting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("hlavicka")
public class Hlavicka implements Serializable {
	private static final long serialVersionUID = 54775320703752688L;
	private static final Logger log = Logger.getLogger(Hlavicka.class);

	private String stranka = "";
	private List<MenuBean> menuBeanSeznam = new ArrayList<MenuBean>();
	private List<MenuBean> fullMenuBeanSeznam = new ArrayList<MenuBean>();
	private List<StrankaNavratBean> navratSeznam = new ArrayList<StrankaNavratBean>();

	private boolean isExistStrankaMenu(String stranka) {
		boolean isExist = false;
		for (int i = 0; !isExist && i < this.menuBeanSeznam.size(); i++) {
			if (((MenuBean) this.menuBeanSeznam.get(i)).getStranka().equals(stranka)) {
				isExist = true;
			}
		}
		return isExist;
	}

	private String getMenuStranky(String stranka) {
		String menu = "";
		boolean isExist = false;
		for (int i = 0; !isExist && i < this.menuBeanSeznam.size(); i++) {
			if (((MenuBean) this.menuBeanSeznam.get(i)).getStranka().equals(stranka)) {
				isExist = true;
				menu = ((MenuBean) this.menuBeanSeznam.get(i)).getMenu();
			}
		}
		return menu;
	}

	public synchronized void setStranka(String stranka) {
		if (stranka == null) {
			log.warn("Parametr metody byl nastaven na null");
			log.warn("NavratSeznam: " + this.navratSeznam.toString());
		}
		if (this.navratSeznam == null) {
			log.warn("Navratovy seznam je null");
		} else if (this.navratSeznam.contains(null)) {
			log.warn("Navratovy seznam obsahuje polozku null");
			log.warn("NavratSeznam: " + this.navratSeznam.toString());
		} else {
			for (StrankaNavratBean snb : this.navratSeznam) {
				if (snb.getStranka() == null) {
					log.warn("Obnaceni stranky v navratovem menu je null");
					log.warn("Navrat seznam: " + this.navratSeznam.toString());
				}
			}
		}

		this.stranka = stranka;
		if (this.navratSeznam == null) {
			this.navratSeznam = new ArrayList<StrankaNavratBean>();
		}
		if (this.navratSeznam.size() > 0) {
			if (isExistStrankaMenu(stranka)) {
				String menu = getMenuStranky(stranka);
				for (int i = 0; i < this.menuBeanSeznam.size(); i++) {
					MenuBean menuBeanO = this.menuBeanSeznam.get(i);
					if (menuBeanO.getMenu().equals(menu)) {
						menuBeanO.setSelected(true);
					} else {
						menuBeanO.setSelected(false);
					}
				}
				this.navratSeznam = new ArrayList<StrankaNavratBean>();
			} else {

				boolean isExist = false;
				int poradi = 0;
				for (int i = 0; !isExist && i < this.navratSeznam.size(); i++) {
					if (((StrankaNavratBean) this.navratSeznam.get(i)).getStranka().equals(stranka)) {
						isExist = true;
						poradi = i;
					}
				}
				if (isExist) {
					while (this.navratSeznam.size() > poradi) {
						this.navratSeznam.remove(poradi);
					}
				}
			}
		}
		this.navratSeznam.add(new StrankaNavratBean(stranka));
	}
	
	
	public synchronized void omezHlavickuSeznam(Boolean zamestnanec) {
		List<MenuBean> menuBeanSeznam = new ArrayList<MenuBean>();
		if(fullMenuBeanSeznam == null) {
			this.fullMenuBeanSeznam = this.menuBeanSeznam;
		} else {
			this.menuBeanSeznam = fullMenuBeanSeznam;
		}
		
		 if(zamestnanec == null) {
			 for (int i = 0; i < this.menuBeanSeznam.size(); i++) {
				 if (((MenuBean) this.menuBeanSeznam.get(i)).isStandard()) {
					menuBeanSeznam.add(this.menuBeanSeznam.get(i)); 
				 }
			 }
			 this.menuBeanSeznam = menuBeanSeznam;
			 
		 } else { 
			 if(zamestnanec.booleanValue()) {
				 for (int i = 0; i < this.menuBeanSeznam.size(); i++) {
					 if (((MenuBean) this.menuBeanSeznam.get(i)).isZamestnanec()) {
						 menuBeanSeznam.add(this.menuBeanSeznam.get(i)); 
					 }
				 }
				 this.menuBeanSeznam = menuBeanSeznam;
			 } 
			 if(!zamestnanec.booleanValue()) {
				 for (int i = 0; i < this.menuBeanSeznam.size(); i++) {
					 if (((MenuBean) this.menuBeanSeznam.get(i)).isZakaznik()) {
						menuBeanSeznam.add(this.menuBeanSeznam.get(i)); 
					 }
				 }
				 this.menuBeanSeznam = menuBeanSeznam;
			 }
		 }
	}
	

	public synchronized String getStranka() {
		return this.stranka;
	}

	public synchronized String getSelectedStranka() {
		return ((StrankaNavratBean) this.navratSeznam.get(this.navratSeznam.size() - 1)).getStranka();
	}

	public synchronized List<MenuBean> getMenuBeanSeznam() {
		return this.menuBeanSeznam;
	}

	public synchronized List<StrankaNavratBean> getNavratSeznam() {
		return this.navratSeznam;
	}


	public String toString() {
		String navrat = "";
		navrat = String.valueOf(navrat) + "-----------------------------------------------\n";

		navrat = String.valueOf(navrat) + "Stranka: ";
		navrat = String.valueOf(navrat) + getStranka();
		navrat = String.valueOf(navrat) + "\n";

		navrat = String.valueOf(navrat) + "MenuSeznam:";
		navrat = String.valueOf(navrat) + "\n";
		List<MenuBean> ms = getMenuBeanSeznam();
		int i = 0;
		for (MenuBean bean : ms) {
			navrat = String.valueOf(navrat) + "Menu(" + String.valueOf(i) + "):";
			navrat = String.valueOf(navrat) + bean.getMenu();
			navrat = String.valueOf(navrat) + " ";
			navrat = String.valueOf(navrat) + "StrankaMenu(" + String.valueOf(i) + "):";
			navrat = String.valueOf(navrat) + bean.getStranka();
			navrat = String.valueOf(navrat) + " ";
			navrat = String.valueOf(navrat) + "Selected(" + String.valueOf(i) + "):";
			navrat = String.valueOf(navrat) + String.valueOf(bean.isSelected());
			navrat = String.valueOf(navrat) + " ";
			navrat = String.valueOf(navrat) + "\n";
			i++;
		}
		navrat = String.valueOf(navrat) + " ";
		navrat = String.valueOf(navrat) + "\n";

		navrat = String.valueOf(navrat) + "NavratSeznam:";
		navrat = String.valueOf(navrat) + "\n";
		List<StrankaNavratBean> ns = getNavratSeznam();
		i = 0;
		for (StrankaNavratBean bean : ns) {
			navrat = String.valueOf(navrat) + "Stranka(" + String.valueOf(i) + "):";
			navrat = String.valueOf(navrat) + bean.getStranka();
			navrat = String.valueOf(navrat) + " ";
			navrat = String.valueOf(navrat) + bean.getNazevStranka();
			navrat = String.valueOf(navrat) + " ";
			navrat = String.valueOf(navrat) + "\n";
			i++;
		}
		navrat = String.valueOf(navrat) + "\n";
		navrat = String.valueOf(navrat) + "SelectedStranka: ";
		navrat = String.valueOf(navrat) + getSelectedStranka();
		navrat = String.valueOf(navrat) + " ";
		navrat = String.valueOf(navrat) + "\n";
		return navrat;
	}

	public List<MenuBean> getFullMenuBeanSeznam() {
		return fullMenuBeanSeznam;
	}

	public void setFullMenuBeanSeznam(List<MenuBean> fullMenuBeanSeznam) {
		this.fullMenuBeanSeznam = fullMenuBeanSeznam;
	}
	
	
}