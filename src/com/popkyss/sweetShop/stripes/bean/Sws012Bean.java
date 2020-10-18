package com.popkyss.sweetShop.stripes.bean;


import com.popkyss.sweetShop.model.PrepravkaProdukt;
import com.popkyss.sweetShop.setting.AbstractBean;


public class Sws012Bean extends AbstractBean {
	
	private static final long serialVersionUID = 1L;

	private PrepravkaProdukt produkt;

	
	
	public PrepravkaProdukt getProdukt() {
		return produkt;
	}

	public void setProdukt(PrepravkaProdukt produkt) {
		this.produkt = produkt;
	}

}
