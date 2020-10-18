package com.popkyss.sweetShop.setting;

import org.apache.log4j.Logger;

public abstract class AHlavickaTabulky {
	protected Logger log = Logger.getLogger(getClass());

	protected Integer pocetDatovychSloupcu = null;

	@Deprecated
	public AHlavickaTabulky() {
	}

	public AHlavickaTabulky(int pocetDatovychSloupcu) {
		this.pocetDatovychSloupcu = Integer.valueOf(pocetDatovychSloupcu);
	}

	public abstract void addBunka(BunkaTabulky paramBunkaTabulky);

	public int getPocetSloupcu() {
		return this.pocetDatovychSloupcu.intValue();
	}
}