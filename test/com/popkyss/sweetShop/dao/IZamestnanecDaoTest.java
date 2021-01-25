package com.popkyss.sweetShop.dao;

import org.junit.Test;

import com.popkyss.sweetShop.AbstractHibernateTest;

public class IZamestnanecDaoTest extends AbstractHibernateTest {

	private IZamestnanecDao zamestnanecDao = SweetShopDaoFactory.getZamestnanecDao();

	
	@Test
	public void existujeUsernameTest() {
		zamestnanecDao.existujeUsername(null);
		zamestnanecDao.existujeUsername("DZCOH11");
	}
	

	@Test
	public void existujeEmailTest() {
		zamestnanecDao.existujeEmail(null);
		zamestnanecDao.existujeEmail("honza@seznam.cz");
	}
	
	
	@Test
	public void existujeTelefonTest() {
		zamestnanecDao.existujeTelefon(null);
		zamestnanecDao.existujeTelefon("111888999");
	}
	
	@Test
	public void najdiDleEmailuTest() {
		zamestnanecDao.najdiDleEmailu(null);
		zamestnanecDao.najdiDleEmailu("honza@seznam.cz");
	}
}
