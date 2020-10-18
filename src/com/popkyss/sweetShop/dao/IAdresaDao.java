package com.popkyss.sweetShop.dao;

import com.popkyss.sweetShop.entity.Adresa;
import com.popkyss.sweetShop.setting.ICrudDao;

public interface IAdresaDao extends ICrudDao<Adresa, Integer> {	
	
	
	/**
	 * Metoda, ktera overi existenci adresy
	 * @param mesto, nazev mesta
	 * @param ulice, nazev ulice
	 * @param cp, cislo popisne
	 * @param psc, postovni smerovaci cislo
	 * @return identifikator adresy, pokud existuje
	 */
	public Integer existujeAdresa(String mesto, String ulice, String cp, int psc);

	
}
