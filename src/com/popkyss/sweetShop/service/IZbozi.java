package com.popkyss.sweetShop.service;

import java.util.List;

import com.popkyss.sweetShop.model.PrepravkaKosik;
import com.popkyss.sweetShop.model.PrepravkaPolozky;
import com.popkyss.sweetShop.model.PrepravkaProdukt;
import com.popkyss.sweetShop.setting.BasicServiceException;

public interface IZbozi {

	//Polozka neexistuje
	public static final String POL01 = "pol.01";
	
	//Produkt neexistuje
		public static final String PROD01 = "prod.01";
		
		//Kosik neexistuje
		public static final String KOS01 = "kos.01";
	
	/**
	 * Metoda, ktera najde kosik zakaznika
	 * @param idZakaznika, identifikator zakaznika
	 * @return naplnena prepravka kosiku
	 */
	public PrepravkaKosik najdiKosikZakaznika(Integer idZakaznika);
	
	/**
	 * Metoda, ktera najde polozky kosiku
	 * @param idKosik, identifikator kosiku
	 * @return naplnena prepravka polozek
	 */
	public List<PrepravkaPolozky> najdiPolozkyKosiku(Integer idKosik);
	
	/**
	 * Metoda, ktera odebere 1 ks polozky z kosiku
	 * @param idPolozka, identifikator polozky
	 * @return true, pokud pri odebirani nezbyde zadny kus
	 * @throws BasicServiceException
	 */
	public boolean odebratPolozku(Integer idPolozka) throws BasicServiceException;
	
	/**
	 * Metoda, ktera nadjde vsechny produkty
	 * @return list produktu
	 */
	public List<PrepravkaProdukt> najdiProdukty();
	
	/**
	 * Metoda, ktera najde produkt dle id
	 * @param idProdukt, identifikator produktu
	 * @return prepravka produktu
	 * @throws BasicServiceException
	 */
	public PrepravkaProdukt najdiProdukt(Integer idProdukt) throws BasicServiceException;
	
	/**
	 * Metoda, ktera prida produkt do kosiku
	 * @param idKosik, identifikator kosiku
	 * @param produkt, prepravka s produktem
	 * @return true, pokud se zaklada nova polozka do kosiku 
	 * @throws BasicServiceException
	 */
	public boolean pridatDoKosiku(Integer idKosik, PrepravkaProdukt produkt) throws BasicServiceException;
}
