package com.popkyss.sweetShop.dao;


import com.popkyss.sweetShop.entity.Logger;
import com.popkyss.sweetShop.model.Uzivatel;
import com.popkyss.sweetShop.setting.ICrudDao;

public interface ILoggerDao extends ICrudDao<Logger, Integer> {

	/**
	 * Metoda, ktera overi uzivatele, zda eistuje
	 * @param username, username uzivatele
	 * @param password, heslo uzivatele
	 * @return true, pokud existuje
	 */
	public boolean existUser(String uname, String pass);
	
	
	/**
	 * Metoda, ktera najde uzivatele
	 * @param username, username uzivatele
	 * @param password, heslo uzivatele
	 * @return uzivatele
	 */
	public Uzivatel najdiUserLogin(String uname, String pass);
	
	/**
	 * Metoda, ktera najde uzivatelel dle username
	 * @param username, username uzivatele
	 * @return uzivatel
	 */
	public Logger najdiUzivatele(String username);
}
