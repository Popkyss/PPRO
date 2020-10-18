package com.popkyss.sweetShop.dao.impl;

import com.popkyss.sweetShop.dao.IKosikDao;
import com.popkyss.sweetShop.entity.Kosik;
import com.popkyss.sweetShop.setting.HibernateUniversalDao;

public class HibernateKosikDaoImpl extends HibernateUniversalDao<Kosik, Integer> implements IKosikDao {

	public HibernateKosikDaoImpl(Class<Kosik> entityClass) {
	}

}
