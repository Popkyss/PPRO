package com.popkyss.sweetShop.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.popkyss.sweetShop.dao.IAdresaDao;
import com.popkyss.sweetShop.entity.Adresa;
import com.popkyss.sweetShop.setting.HibernateUniversalDao;

public class HibernateAdresaDaoImpl extends HibernateUniversalDao<Adresa, Integer> implements IAdresaDao {

	public HibernateAdresaDaoImpl(Class<Adresa> entityClass) {
	}
	
	public Integer existujeAdresa(String mesto, String ulice, String cp, int psc) {
		Criteria crit = createCriteria();
		crit.add(Restrictions.eq("mesto", mesto));
		crit.add(Restrictions.eq("ulice", ulice));
		crit.add(Restrictions.eq("cp", cp));
		crit.add(Restrictions.eq("psc", psc));
		
		crit.setProjection(Projections.property("idAdresa"));
		crit.setMaxResults(1);
		
		return (Integer)crit.uniqueResult();	
	}

}
