package com.popkyss.sweetShop;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;

import com.popkyss.sweetShop.setting.HibernateSessionContext;

public class AbstractHibernateTest {
	
	protected Logger log = Logger.getLogger(getClass());
	protected Session hs;
	
	
	@Before
	public void beforeTest() {
		try {
			HibernateSessionContext.setHs(HibernateSessionContext.getSessionfactory().openSession());
			this.hs = HibernateSessionContext.hs();
			hs.getTransaction().begin();
			log.trace("Start testu - transaction begin ***************************************");
		} catch (Exception e) {
			log.error("Chyba v testu", e);
		}
		
	}
	
	
	@After
	public void afterTest(){
		try {
			HibernateSessionContext.rollback();
			log.trace("Konec testu - transaction rollback ************************************");
		} catch (Exception e) {
			log.error("Chyba v testu", e);
			
		}
	}
	
	/**
	 * Vrati aktualni session pro aktualne probihajici test
	 */
	protected Session hs() {
		return hs;
	}

}
