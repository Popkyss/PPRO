package com.popkyss.sweetShop.dao;

import org.junit.Test;

import com.popkyss.sweetShop.AbstractHibernateTest;

public class IAdresaDaoTest extends AbstractHibernateTest {

private IAdresaDao adresaDao = SweetShopDaoFactory.getAdresaDao();

	
	@Test
	public void existujeAdresaTest() {
		adresaDao.existujeAdresa("Pardubice", "Belehradska", "23", 52510);
		adresaDao.existujeAdresa("Pardubice", "Belehradska", null, 52510);
		adresaDao.existujeAdresa(null, "Belehradska", "23", 52510);
		adresaDao.existujeAdresa("Honza", null, "23", 52510);
		adresaDao.existujeAdresa("Honza", null, null, 52510);
		adresaDao.existujeAdresa(null, "Honza", null, 52510);
		adresaDao.existujeAdresa(null, null, "23", 52510);
		adresaDao.existujeAdresa(null, null, null, 52510);
	}
	
}
