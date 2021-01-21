package com.popkyss.sweetShop.setting;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class HlavickaBean implements Serializable {
	private static final long serialVersionUID = -7227041088996205949L;
	public static final String HLAVICKA_SESSION_SPACE = "hlavickaBean";
	private static final String HLAVICKA_PATH = "resources/web/hlavicka.xml";
	public Hlavicka hlavicka = null;

	private static Logger log = Logger.getLogger(HlavickaBean.class);
	
	public HlavickaBean() {
		InputStream input = null;
		try {
			input = getClass().getClassLoader().getResourceAsStream(HLAVICKA_PATH);

			XStream xstream = new XStream((HierarchicalStreamDriver) new DomDriver("utf-8"));
			xstream.processAnnotations(Hlavicka.class);
		
			
			this.hlavicka = (Hlavicka) xstream.fromXML(input);
//			this.hlavicka.omezHlavickuSeznam(null);
		} catch (Exception e) {
			log.error("Nepodarilo se inicializovat hlavicku aplikace", e);
			throw new RuntimeException(e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					throw new RuntimeException("Problem pri zavirani proudu dat s hlavickou", e);
				}
			}
		}
	}

	public List<MenuBean> getMenuSeznam() {
		return this.hlavicka.getMenuBeanSeznam();
	}

	public List<StrankaNavratBean> getNavratSeznam() {
		List<StrankaNavratBean> snbs = new ArrayList<StrankaNavratBean>(this.hlavicka.getNavratSeznam());

		if (snbs.size() > 0) {
			snbs.remove(snbs.size() - 1);
		}
		return snbs;
	}

	public String getSelectedStranka() {
		return this.hlavicka.getSelectedStranka();
	}

	public String getStranka() {
		return this.hlavicka.getStranka();
	}

	public void setStranka(String stranka) {
		this.hlavicka.setStranka(stranka);
	}

	public String toString() {
		return this.hlavicka.toString();
	}

	public String getStrankaNazev() {
		return PropertyReader.bundle(String.valueOf(PropertyReader.getLNG_PREFIX()) + getStranka().substring(3, 6),
				"pageName");
	}
}