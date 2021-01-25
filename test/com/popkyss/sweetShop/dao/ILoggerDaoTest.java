package com.popkyss.sweetShop.dao;

import org.junit.Test;

import com.popkyss.sweetShop.AbstractHibernateTest;

public class ILoggerDaoTest extends AbstractHibernateTest {

private ILoggerDao loggerDao = SweetShopDaoFactory.getLoggerDao();

	
	@Test
	public void existUserTest() {
		loggerDao.existUser(null, null);
		loggerDao.existUser("Honza", null);
		loggerDao.existUser(null, "Honza");
		loggerDao.existUser("Honza", "Honza");
	}
	

	@Test
	public void najdiUserLoginTest() {
		loggerDao.najdiUserLogin(null, null);
		loggerDao.najdiUserLogin("Honza", null);
		loggerDao.najdiUserLogin(null, "Honza");
		loggerDao.najdiUserLogin("Honza", "Honza");
	}
	
	@Test
	public void najdiUzivateleTest() {
		loggerDao.najdiUzivatele(null);
		loggerDao.najdiUzivatele("Popkyss");
	}
	
}
