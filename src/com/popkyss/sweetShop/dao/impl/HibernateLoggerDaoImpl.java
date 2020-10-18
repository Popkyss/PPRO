package com.popkyss.sweetShop.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.popkyss.sweetShop.dao.ILoggerDao;
import com.popkyss.sweetShop.entity.Logger;
import com.popkyss.sweetShop.model.Uzivatel;
import com.popkyss.sweetShop.setting.HibernateUniversalDao;



public class HibernateLoggerDaoImpl extends HibernateUniversalDao<Logger, Integer> implements ILoggerDao {

	
	
	public HibernateLoggerDaoImpl(Class<Logger> entityClass) {
	}


	public boolean existUser(String username, String heslo) {
		Criteria crit = createCriteria();
		crit.add(Restrictions.eq("uname", username));
		crit.add(Restrictions.eq("heslo", heslo));
		
		crit.setProjection(Projections.rowCount());
		return (Long) crit.uniqueResult() > 0;
	}
	
	
	public Uzivatel najdiUserLogin(String username, String heslo) {
		Criteria crit = createCriteria();
		crit.add(Restrictions.eq("uname", username));
		crit.add(Restrictions.eq("heslo", heslo));
		
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.property("uname"), "username");
		pl.add(Projections.property("heslo"), "heslo");
		pl.add(Projections.property("idZamestnanec"), "idZamestnanec");
		pl.add(Projections.property("idZakaznik"), "idZakaznik");
		crit.setProjection(pl);
		crit.setResultTransformer(Transformers.aliasToBean(Uzivatel.class));
		
		return (Uzivatel) crit.uniqueResult();
	}
	
	public Logger najdiUzivatele(String username) {
		Criteria crit = createCriteria();
		crit.add(Restrictions.eq("uname", username));
		
		crit.setMaxResults(1);
		return (Logger) crit.uniqueResult();
	}
}
