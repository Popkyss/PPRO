package com.popkyss.sweetShop.stripes.bean;

import java.util.ArrayList;
import java.util.List;

import com.popkyss.sweetShop.model.PrepravkaPolozky;
import com.popkyss.sweetShop.setting.AbstractBean;


public class Sws010Bean extends AbstractBean {
	
	private static final long serialVersionUID = 1L;

	
	private List<PrepravkaPolozky> polozky = new ArrayList<PrepravkaPolozky>();


	public List<PrepravkaPolozky> getPolozky() {
		return polozky;
	}


	public void setPolozky(List<PrepravkaPolozky> polozky) {
		this.polozky = polozky;
	}


		

}
