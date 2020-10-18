package com.popkyss.sweetShop.setting;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class HibernateUniversalDao<Typ extends Serializable, Id extends Serializable> extends AHibernateTransactionDao
		implements ICrudDao<Typ, Id> {
	private Class<? extends Typ> entityClass;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HibernateUniversalDao() {
		Class<?> cl = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.entityClass = (Class) cl;
	}

	@SuppressWarnings("unchecked")
	public Typ find(Id id) {
		if (id != null) {
			return (Typ) hs().get(this.entityClass, (Serializable) id);
		}
		return null;
	}

	public boolean isExist(Id id) {
		return !(find(id) == null);
	}

	@SuppressWarnings("unchecked")
	public List<Typ> findAll() {
		List<Typ> list = null;
		String sql = "SELECT entity FROM " + this.entityClass.getSimpleName() + " entity ";
		Query q = hs().createQuery(sql);
		list = q.list();
		return list;
	}

	public void remove(Id id) {
		Typ t = find(id);
		if (t != null) {
			hs().delete(t);
		}
	}

	public void delete(Typ t) {
		if (t != null) {
			hs().delete(t);
		}
	}

	@SuppressWarnings("unchecked")
	public Id persist(Typ e) {
		hs().persist(e);
		return (Id) hs().getIdentifier(e);
	}

	@SuppressWarnings("unchecked")
	public Typ merge(Typ e) {
		return (Typ) hs().merge(e);
	}

	public void refresh(Typ e) {
		hs().refresh(e);
	}

	public boolean isExist(String property, Object value) {
		Criteria crit = createCriteria();
		crit.add((Criterion) Restrictions.eq(property, value));
		crit.setProjection(Projections.rowCount());
		return (((Long) crit.uniqueResult()).longValue() > 0L);
	}

	public boolean isExist(String property, Object value, Id id) {
		Criteria crit = createCriteria();
		crit.add((Criterion) Restrictions.eq(property, value));
		crit.add(Restrictions.not(Restrictions.idEq(id)));
		crit.setProjection(Projections.rowCount());
		return (((Long) crit.uniqueResult()).longValue() > 0L);
	}

	protected Criteria createCriteria() {
		return hs().createCriteria(this.entityClass);
	}

	protected Criteria createCriteria(String alias) {
		return hs().createCriteria(this.entityClass, alias);
	}

	protected Junction createDeletableJunctionOnCollections(boolean deletable, String... kolekce) {
		Disjunction disjunction = null;
		if (deletable) {
			Conjunction conjunction = Restrictions.conjunction();
			byte b;
			int i;
			String[] arrayOfString;
			for (i = (arrayOfString = kolekce).length, b = 0; b < i;) {
				String s = arrayOfString[b];
				conjunction.add(Restrictions.isEmpty(s));
				b++;
			}

		} else {
			disjunction = Restrictions.disjunction();
			byte b;
			int i;
			String[] arrayOfString;
			for (i = (arrayOfString = kolekce).length, b = 0; b < i;) {
				String s = arrayOfString[b];
				disjunction.add(Restrictions.isNotEmpty(s));
				b++;
			}

		}
		return (Junction) disjunction;
	}
}
