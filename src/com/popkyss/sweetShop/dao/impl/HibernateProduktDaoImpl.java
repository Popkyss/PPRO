package com.popkyss.sweetShop.dao.impl;

import com.popkyss.sweetShop.dao.IProduktDao;
import com.popkyss.sweetShop.entity.Produkt;
import com.popkyss.sweetShop.setting.HibernateUniversalDao;

public class HibernateProduktDaoImpl extends HibernateUniversalDao<Produkt, Integer> implements IProduktDao {

	public HibernateProduktDaoImpl(Class<Produkt> entityClass) {
	}

	
}
