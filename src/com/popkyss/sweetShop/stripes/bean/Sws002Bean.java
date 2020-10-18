package com.popkyss.sweetShop.stripes.bean;

import com.popkyss.sweetShop.model.PrepravkaUzivatel;
import com.popkyss.sweetShop.setting.AbstractBean;


public class Sws002Bean extends AbstractBean {
	
	private static final long serialVersionUID = 1L;

	private boolean adresaAdded = false;
	private PrepravkaUzivatel novyZakaznik = new PrepravkaUzivatel();


	public PrepravkaUzivatel getNovyZakaznik() {
		return novyZakaznik;
	}


	public void setNovyZakaznik(PrepravkaUzivatel novyZakaznik) {
		this.novyZakaznik = novyZakaznik;
	}


	public boolean isAdresaAdded() {
		return adresaAdded;
	}


	public void setAdresaAdded(boolean adresaAdded) {
		this.adresaAdded = adresaAdded;
	}

}
