package com.popkyss.sweetShop.setting;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public abstract class AHibernateTransactionDao {
	protected Session hs() {
		try {
			Session session = HibernateSessionContext.hs();
			if (session.getTransaction().isActive()) {
				return session;
			}
			session.beginTransaction();
			
			return session;
		} catch (HibernateSessionContextException | HibernateException e) {
			throw new RuntimeException(e);
		}
	}
}