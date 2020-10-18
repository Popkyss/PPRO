package com.popkyss.sweetShop.stripes.bean;

import java.util.ArrayList;
import java.util.List;

import com.popkyss.sweetShop.model.PrepravkaProdukt;
import com.popkyss.sweetShop.setting.AbstractBean;


public class Sws011Bean extends AbstractBean {
	
	private static final long serialVersionUID = 1L;

	
	private List<PrepravkaProdukt> produkty = new ArrayList<PrepravkaProdukt>();


	public List<PrepravkaProdukt> getProdukty() {
		return produkty;
	}


	public void setProdukty(List<PrepravkaProdukt> produkty) {
		this.produkty = produkty;
	}
	


		

}
