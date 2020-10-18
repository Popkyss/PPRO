package com.popkyss.sweetShop.dao;

import java.util.List;

import com.popkyss.sweetShop.entity.Zamestnanec;
import com.popkyss.sweetShop.setting.ICrudDao;

public interface IZamestnanecDao extends ICrudDao<Zamestnanec, Integer>{


	public List<Zamestnanec> najdiVsechny();
	
	
	/**
	 * Metoda, ktera overi existenci username
	 * @param username, prihlasovaci jmeno
	 * @return true, pokud existuje
	 */
	public boolean existujeUsername(String username);
	
	/**
	 * Metoda, ktera overi existenci emailu
	 * @param email, email
	 * @return true, pokud existuje
	 */
	public boolean existujeEmail(String email);
	
	/**
	 * Metoda, ktera overi existenci telefonniho cisla
	 * @param telefon, telefonni cislo
	 * @return true, pokud existuje
	 */
	public boolean existujeTelefon(String telefon);
	
	/**
	 * Metoda, ktera nadje uzivatele dle emailu
	 * @param email, email uzivatele
	 * @return zamestnanec prepravka
	 */
	public Zamestnanec najdiDleEmailu(String email);
}