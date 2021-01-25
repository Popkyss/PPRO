package com.popkyss.sweetShop.dao;

import org.junit.Test;

import com.popkyss.sweetShop.AbstractHibernateTest;

public class IPolozkaDaoTest extends AbstractHibernateTest {

private IPolozkaDao polozkakDao = SweetShopDaoFactory.getPolozkaDao();

	
	@Test
	public void spocitejPocetPolozekTest() {
		polozkakDao.spocitejPocetPolozek(null);
		polozkakDao.spocitejPocetPolozek(192932);
	}
	

	@Test
	public void najdiPolozkyKosikuTest() {
		polozkakDao.najdiPolozkyKosiku(null);
		polozkakDao.najdiPolozkyKosiku(123);
	}
	
}
