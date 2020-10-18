package com.popkyss.sweetShop.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.popkyss.sweetShop.dao.IZamestnanecDao;
import com.popkyss.sweetShop.entity.Zamestnanec;
import com.popkyss.sweetShop.setting.HibernateUniversalDao;

public class HibernateZamestnanecDaoImpl extends HibernateUniversalDao<Zamestnanec, Integer> implements IZamestnanecDao {

	public HibernateZamestnanecDaoImpl(Class<Zamestnanec> entityClass) {
	}

	
	@SuppressWarnings("unchecked")
	public List<Zamestnanec> najdiVsechny(){
		Criteria criteria = createCriteria();
		
		return criteria.list();
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
	
	public Zamestnanec najdiDleEmailu(String email) {
		Criteria crit = createCriteria();
		crit.add(Restrictions.eq("email", email));
		return (Zamestnanec) crit.uniqueResult();
	}
	
}
