package com.popkyss.sweetShop.setting;

import java.util.List;

import org.apache.log4j.Logger;

public class SimpleDataTable {
	Logger log = Logger.getLogger(SimpleDataTable.class);

	private List<RadekTabulky> radkyTabulky = null;

	private AHlavickaDatoveTabulky hlavickaTabulky = null;

	private int pocetObarvenychRadku = 1;

	private SirkaSloupcuSwitcher switcher = null;

	private float[] sirkaSloupcuTabulky = null;

	private boolean onNewPage = false;

	private boolean onHalfPage = false;

	public SimpleDataTable(AHlavickaDatoveTabulky hlavickaTabulky, List<RadekTabulky> radkyTabulky) {
		this.radkyTabulky = radkyTabulky;
		this.hlavickaTabulky = hlavickaTabulky;
	}

	public List<RadekTabulky> getRadkyTabulky() {
		return this.radkyTabulky;
	}

	public AHlavickaTabulky getHlavickaTabulky() {
		return this.hlavickaTabulky;
	}

	public int getPocetObarvenychRadku() {
		return this.pocetObarvenychRadku;
	}

	public void setPocetObarvenychRadku(int pocetObarvenychRadku) {
		if (pocetObarvenychRadku > -1) {
			this.pocetObarvenychRadku = pocetObarvenychRadku;
		} else {
			this.log.error("pocet obarvenych radku nesmi byt zaporne cislo");
		}
	}

	public SirkaSloupcuSwitcher getSwitcher() {
		return this.switcher;
	}

	public void setSwitcher(SirkaSloupcuSwitcher switcher) {
		this.switcher = switcher;
	}

	public float[] getSirkaSloupcuTabulky() {
		return this.sirkaSloupcuTabulky;
	}

	public void setSirkaSloupcuTabulky(float[] sirkaSloupcuTabulky) {
		if (sirkaSloupcuTabulky.length != ((RadekTabulky) getRadkyTabulky().get(0)).size()) {
			this.log.error("Nepodarilo se nastavit sirku sloupcu tabulky, zkontrolujte pocet zadavanych sloupcu");
			throw new RuntimeException(
					"Nepodarilo se nastavit sirku sloupcu tabulky, zkontrolujte pocet zadavanych sloupcu");
		}
		this.sirkaSloupcuTabulky = sirkaSloupcuTabulky;
	}

	public int size() {
		return this.hlavickaTabulky.getPocetSloupcu();
	}

	public boolean isOnNewPage() {
		return this.onNewPage;
	}

	public void setOnNewPage(boolean onNewPage) {
		this.onNewPage = onNewPage;
		this.onHalfPage = false;
	}

	public boolean isOnHalfPage() {
		return this.onHalfPage;
	}

	public void setOnHalfPage(boolean onHalfPage) {
		this.onHalfPage = onHalfPage;
		this.onNewPage = false;
	}
}