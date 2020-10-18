package com.popkyss.sweetShop.setting;

public final class CoreDaoFactory extends AbstractFactory {
	private static final CoreDaoFactory INSTANCE = new CoreDaoFactory();

	public static ICoreDao getCoreDao() {
		return (ICoreDao) INSTANCE.createAndStoreInstance(ICoreDao.class);
	}
}