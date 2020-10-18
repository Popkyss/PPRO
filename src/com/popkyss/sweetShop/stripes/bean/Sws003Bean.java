package com.popkyss.sweetShop.stripes.bean;

import com.popkyss.sweetShop.model.PrepravkaUzivatel;
import com.popkyss.sweetShop.setting.AbstractBean;


public class Sws003Bean extends AbstractBean {
	
	private static final long serialVersionUID = 1L;

	private boolean adresaAdded = false;
	
	private PrepravkaUzivatel novyZamestnanec = new PrepravkaUzivatel();


	public PrepravkaUzivatel getNovyZamestnanec() {
		return novyZamestnanec;
	}


	public void setNovyZamestnanec(PrepravkaUzivatel novyZamestnanec) {
		this.novyZamestnanec = novyZamestnanec;
	}


	public boolean isAdresaAdded() {
		return adresaAdded;
	}


	public void setAdresaAdded(boolean adresaAdded) {
		this.adresaAdded = adresaAdded;
	}

}
