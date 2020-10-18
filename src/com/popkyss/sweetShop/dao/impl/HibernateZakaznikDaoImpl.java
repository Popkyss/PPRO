package com.popkyss.sweetShop.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.popkyss.sweetShop.dao.IZakaznikDao;
import com.popkyss.sweetShop.entity.Zakaznik;
import com.popkyss.sweetShop.setting.HibernateUniversalDao;

public class HibernateZakaznikDaoImpl extends HibernateUniversalDao<Zakaznik, Integer> implements IZakaznikDao {

	public HibernateZakaznikDaoImpl(Class<Zakaznik> entityClass) {
	}

	
	public boolean existujeUsername(String username) {
		Criteria crit = createCriteria();
		if(username != null) {
			crit.add(Restrictions.eq("username", username));
		}
		crit.setProjection(Projections.rowCount());
		return (Long) crit.uniqueResult() > 0;
	}
	
	public boolean existujeEmail(String email) {
		Criteria crit = createCriteria();
		if(email != null) {
			crit.add(Restrictions.eq("email", email));
		}
		crit.setProjection(Projections.rowCount());
		return (Long) crit.uniqueResult() > 0;
	}
	
	public boolean existujeTelefon(String telefon) {
		Criteria crit = createCriteria();
		if(telefon != null) {
			crit.add(Restrictions.eq("telefon", telefon));
		}
		crit.setProjection(Projections.rowCount());
		return (Long) crit.uniqueResult() > 0;
	}
	
	public Zakaznik najdiDleEmailu(String email) {
		Criteria crit = createCriteria();
		crit.add(Restrictions.eq("email", email));
		return (Zakaznik) crit.uniqueResult();
	}
}
