package com.popkyss.sweetShop.dao;

import com.popkyss.sweetShop.dao.impl.HibernateAdresaDaoImpl;
import com.popkyss.sweetShop.dao.impl.HibernateDataDaoImpl;
import com.popkyss.sweetShop.dao.impl.HibernateDodavatelDaoImpl;
import com.popkyss.sweetShop.dao.impl.HibernateDodavkaDaoImpl;
import com.popkyss.sweetShop.dao.impl.HibernateKosikDaoImpl;
import com.popkyss.sweetShop.dao.impl.HibernateLoggerDaoImpl;
import com.popkyss.sweetShop.dao.impl.HibernateObjednavkaDaoImpl;
import com.popkyss.sweetShop.dao.impl.HibernateOblibeneDaoImpl;
import com.popkyss.sweetShop.dao.impl.HibernatePolozkaDaoImpl;
import com.popkyss.sweetShop.dao.impl.HibernateProduktDaoImpl;
import com.popkyss.sweetShop.dao.impl.HibernateRoleDaoImpl;
import com.popkyss.sweetShop.dao.impl.HibernateSkupinaDaoImpl;
import com.popkyss.sweetShop.dao.impl.HibernateSlevaDaoImpl;
import com.popkyss.sweetShop.dao.impl.HibernateZakaznikDaoImpl;
import com.popkyss.sweetShop.dao.impl.HibernateZamestnanecDaoImpl;
import com.popkyss.sweetShop.dao.impl.HibernateZnackaDaoImpl;
import com.popkyss.sweetShop.entity.Adresa;
import com.popkyss.sweetShop.entity.Data;
import com.popkyss.sweetShop.entity.Dodavatel;
import com.popkyss.sweetShop.entity.Dodavka;
import com.popkyss.sweetShop.entity.Kosik;
import com.popkyss.sweetShop.entity.Logger;
import com.popkyss.sweetShop.entity.Objednavka;
import com.popkyss.sweetShop.entity.Oblibene;
import com.popkyss.sweetShop.entity.Polozka;
import com.popkyss.sweetShop.entity.Produkt;
import com.popkyss.sweetShop.entity.Role;
import com.popkyss.sweetShop.entity.Skupina;
import com.popkyss.sweetShop.entity.Sleva;
import com.popkyss.sweetShop.entity.Zakaznik;
import com.popkyss.sweetShop.entity.Zamestnanec;
import com.popkyss.sweetShop.entity.Znacka;


public class SweetShopDaoFactory {
	
	private static volatile IAdresaDao adresaDao;
	private static volatile IDataDao dataDao;
	private static volatile IDodavatelDao dodavatelDao;
	private static volatile IDodavkaDao dodavkaDao;
	private static volatile IKosikDao kosikDao;
	private static volatile ILoggerDao loggerDao;
	private static volatile IObjednavkaDao objednavkaDao;
	private static volatile IOblibeneDao oblibeneDao;
	private static volatile IPolozkaDao polozkaDao;
	private static volatile IProduktDao produktDao;
	private static volatile IRoleDao roleDao;
	private static volatile ISkupinaDao skupinaDao;
	private static volatile ISlevaDao slevaDao;
	private static volatile IZakaznikDao zakaznikDao;
	private static volatile IZamestnanecDao zamestnanecDao;
	private static volatile IZnackaDao znackaDao;

	
	
	 public static IAdresaDao getAdresaDao() {
	        if (adresaDao == null) {
	            adresaDao = new HibernateAdresaDaoImpl(Adresa.class);
	        }
	        return adresaDao;
	    }
	 
	 public static IDataDao getDataDao() {
	        if (dataDao == null) {
	        	dataDao = new HibernateDataDaoImpl(Data.class);
	        }
	        return dataDao;
	    }
	 
	 public static IDodavatelDao getDodavatelDao() {
	        if (dodavatelDao == null) {
	        	dodavatelDao = new HibernateDodavatelDaoImpl(Dodavatel.class);
	        }
	        return dodavatelDao;
	    }
	 
	 public static IDodavkaDao getDodavkaDao() {
	        if (dodavkaDao == null) {
	        	dodavkaDao = new HibernateDodavkaDaoImpl(Dodavka.class);
	        }
	        return dodavkaDao;
	    }
	 
	 public static IKosikDao getKosikDao() {
	        if (kosikDao == null) {
	        	kosikDao = new HibernateKosikDaoImpl(Kosik.class);
	        }
	        return kosikDao;
	    }
	 
	 public static ILoggerDao getLoggerDao() {
	        if (loggerDao == null) {
	        	loggerDao = new HibernateLoggerDaoImpl(Logger.class);
	        }
	        return loggerDao;
	    }
	 
	 public static IObjednavkaDao getObjednavkaDao() {
	        if (objednavkaDao == null) {
	        	objednavkaDao = new HibernateObjednavkaDaoImpl(Objednavka.class);
	        }
	        return objednavkaDao;
	    }
	 
	 public static IOblibeneDao getOblibeneDao() {
	        if (oblibeneDao == null) {
	        	oblibeneDao = new HibernateOblibeneDaoImpl(Oblibene.class);
	        }
	        return oblibeneDao;
	    }
	 
	 public static IPolozkaDao getPolozkaDao() {
	        if (polozkaDao == null) {
	        	polozkaDao = new HibernatePolozkaDaoImpl(Polozka.class);
	        }
	        return polozkaDao;
	    }
	 
	 public static IProduktDao getProduktDao() {
	        if (produktDao == null) {
	        	produktDao = new HibernateProduktDaoImpl(Produkt.class);
	        }
	        return produktDao;
	    }
	 
	 public static IRoleDao getRoleDao() {
	        if (roleDao == null) {
	        	roleDao = new HibernateRoleDaoImpl(Role.class);
	        }
	        return roleDao;
	    }
	 
	 public static ISkupinaDao getSkupinaDao() {
	        if (skupinaDao == null) {
	        	skupinaDao = new HibernateSkupinaDaoImpl(Skupina.class);
	        }
	        return skupinaDao;
	    }
	 
	 public static ISlevaDao getSlevaDao() {
	        if (slevaDao == null) {
	        	slevaDao = new HibernateSlevaDaoImpl(Sleva.class);
	        }
	        return slevaDao;
	    }
	 
	 public static IZakaznikDao getZakaznikDao() {
	        if (zakaznikDao == null) {
	        	zakaznikDao = new HibernateZakaznikDaoImpl(Zakaznik.class);
	        }
	        return zakaznikDao;
	    }
	 
	 public static IZamestnanecDao getZamestnanecDao() {
	        if (zamestnanecDao == null) {
	        	zamestnanecDao = new HibernateZamestnanecDaoImpl(Zamestnanec.class);
	        }
	        return zamestnanecDao;
	    }
	 
	 public static IZnackaDao getZnackaDao() {
	        if (znackaDao == null) {
	        	znackaDao = new HibernateZnackaDaoImpl(Znacka.class);
	        }
	        return znackaDao;
	    }
	 
}
