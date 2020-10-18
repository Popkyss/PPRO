package com.popkyss.sweetShop.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.popkyss.sweetShop.dao.IPolozkaDao;
import com.popkyss.sweetShop.entity.Polozka;
import com.popkyss.sweetShop.setting.HibernateUniversalDao;

public class HibernatePolozkaDaoImpl extends HibernateUniversalDao<Polozka, Integer> implements IPolozkaDao {

	public HibernatePolozkaDaoImpl(Class<Polozka> entityClass) {
	}

	
	public Long spocitejPocetPolozek(Integer idZakaznik) {
		Criteria crit = createCriteria();
		crit.createAlias("idKosik", "kos");
		crit.createAlias("kos.idZakaznik", "zak");
		
		if(idZakaznik != null) {
			crit.add(Restrictions.eq("zak.idZakaznik", idZakaznik));			
		}
		crit.setProjection(Projections.rowCount());
		return (Long)crit.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Polozka> najdiPolozkyKosiku(Integer idKosik){
		Criteria crit = createCriteria();
		if(idKosik != null) {
			crit.add(Restrictions.eq("idKosik.idKosik", idKosik));			
		}
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return crit.list();
	}
}
