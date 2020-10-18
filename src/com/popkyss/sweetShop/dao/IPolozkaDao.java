package com.popkyss.sweetShop.dao;

import java.util.List;

import com.popkyss.sweetShop.entity.Polozka;
import com.popkyss.sweetShop.setting.ICrudDao;

public interface IPolozkaDao extends ICrudDao<Polozka, Integer> {

	
	/**
	 * Metoda, ktera spocita pocet polozek v kosiku pro zakaznika
	 * @param idZakaznik, identifikator zakaznika
	 * @return pocet polozek
	 */
	public Long spocitejPocetPolozek(Integer idZakaznik);
	
	/**
	 * Metoda, ktera najde polozky kosiku
	 * @param idKosik, identifikator kosiku
	 * @return list polozek
	 */
	public List<Polozka> najdiPolozkyKosiku(Integer idKosik);
}
