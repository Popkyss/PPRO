package com.popkyss.sweetShop.stripes.bean;

import java.util.ArrayList;
import java.util.List;

import com.popkyss.sweetShop.model.Uzivatel;
import com.popkyss.sweetShop.setting.AbstractBean;


public class Sws001Bean extends AbstractBean {
	
	private static final long serialVersionUID = 1L;

	
	private List<Uzivatel> uzivatele = new ArrayList<Uzivatel>();
	

	public List<Uzivatel> getUzivatele() {
		return uzivatele;
	}


	public void setUzivatele(List<Uzivatel> uzivatele) {
		this.uzivatele = uzivatele;
	}

}
