package com.popkyss.sweetShop.dao;

import org.junit.Test;

import com.popkyss.sweetShop.AbstractHibernateTest;

public class IZakaznikDaoTest extends AbstractHibernateTest {

private IZakaznikDao zakaznikDao = SweetShopDaoFactory.getZakaznikDao();

	
	@Test
	public void existujeUsernameTest() {
		zakaznikDao.existujeUsername(null);
		zakaznikDao.existujeUsername("DZCOH11");
	}
	

	@Test
	public void existujeEmailTest() {
		zakaznikDao.existujeEmail(null);
		zakaznikDao.existujeEmail("honza@seznam.cz");
	}
	
	
	@Test
	public void existujeTelefonTest() {
		zakaznikDao.existujeTelefon(null);
		zakaznikDao.existujeTelefon("111888999");
	}
	
	@Test
	public void najdiDleEmailuTest() {
		zakaznikDao.najdiDleEmailu(null);
		zakaznikDao.najdiDleEmailu("honza@seznam.cz");
	}
}
