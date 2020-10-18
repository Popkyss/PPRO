package com.popkyss.sweetShop.service;

import com.popkyss.sweetShop.service.impl.OpravneniImpl;
import com.popkyss.sweetShop.service.impl.ZboziImpl;

public class SweetShopServiceFactory {

	
	private static volatile IOpravneni opravneni;
	private static volatile IZbozi zbozi;
	
	
	public static IOpravneni getOpravneni() {
		if (opravneni == null) {
			opravneni = new OpravneniImpl();
		}
		return opravneni;
	}
	
	public static IZbozi getZbozi() {
		if (zbozi == null) {
			zbozi = new ZboziImpl();
		}
		return zbozi;
	}
}
