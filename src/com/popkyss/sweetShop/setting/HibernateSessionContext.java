package com.popkyss.sweetShop.setting;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionContext {
	private static volatile SessionFactory sessionFactory;

	public static SessionFactory getSessionfactory() throws HibernateSessionContextException {
		if (sessionFactory == null) {
			synchronized (HibernateSessionContext.class) {
				if (sessionFactory == null) {

					try {
						sessionFactory = (new Configuration()).configure().buildSessionFactory();
					} catch (Exception e) {
						throw new HibernateSessionContextException("Chyba pri vytvareni session factory", e);
					}
				}
			}
		}
		return sessionFactory;
	}

	private static final Logger log = Logger.getLogger(HibernateSessionContext.class);

	private static ThreadLocal<Session> sessionThread = new ThreadLocal<Session>();

	private static ThreadLocal<TransactionSwitchSet> transactionStatus = new ThreadLocal<TransactionSwitchSet>();

	private static ThreadLocal<Boolean> setHS = new ThreadLocal<Boolean>();

	public static Session hs() throws HibernateSessionContextException {
		if (setHS.get() != null && ((Boolean) setHS.get()).booleanValue()) {
			return sessionThread.get();
		}

		try {
			return (Session) getSessionfactory().getCurrentSession();
		} catch (HibernateException e) {
			throw new HibernateSessionContextException("Nepodarilo se ziskat hibernate session", e);
		}
	}

	public static void setHs(Session session) {
		setHS.set(Boolean.valueOf(true));
		sessionThread.set(session);
	}

	public static TransactionSwitchSet getTransactionStatus() {
		return transactionStatus.get();
	}

	public static void setTransactionStatus(TransactionSwitchSet etss) {
		transactionStatus.set(etss);
	}

	public static boolean commit() throws HibernateSessionContextException {
		if (hs() != null && hs().getTransaction().isActive()) {
			String transactionName = "";
			try {
				if (log.isTraceEnabled()) {
					transactionName = hs().getTransaction().toString();
				}
				hs().getTransaction().commit();
				if (log.isTraceEnabled()) {
					log.trace("Hibernate: Proveden comit transakce: " + transactionName);
				}
				return true;
			} catch (Exception e) {
				rollback();
				log.error("Hibernate: Proveden rollback transakce");
				if (e instanceof HibernateSessionContextException) {
					throw (HibernateSessionContextException) e;
				}
				throw new HibernateSessionContextException("Nezdaril se commit", e);
			}
		}

		return false;
	}

	public static boolean rollback() throws HibernateSessionContextException {
		if (hs() != null && hs().getTransaction().isActive()) {
			try {
				String transactionName = "";
				if (log.isTraceEnabled()) {
					transactionName = hs().getTransaction().toString();
				}

				hs().getTransaction().rollback();
				if (log.isTraceEnabled()) {
					log.trace("Hibernate: Proveden rollback transakce: " + transactionName);
				}
				return true;
			} catch (HibernateException e) {
				log.error("Hibernate: Rollback nezdaren");
				throw new HibernateSessionContextException("Nezdaril se rollback", e);
			}
		}

		return false;
	}

	public static boolean begin() throws HibernateSessionContextException {
		if (hs() != null) {
			try {
				hs().beginTransaction();
				if (log.isTraceEnabled()) {
					log.trace("Hibernate: Start transakce: " + hs().getTransaction().toString());
				}
				return true;
			} catch (HibernateException e) {
				log.error("Nepodarilo se nastartovat novou transakci", (Throwable) e);
				throw new HibernateSessionContextException("Nepodarilo se nastartovat transakci", e);
			}
		}
		return false;
	}

	public static void tryFlush() throws HibernateSessionContextException {
		if (hs() != null && hs().getTransaction().isActive()) {
			try {
				hs().flush();
			} catch (HibernateException e) {
				throw new HibernateSessionContextException("Nezdarilo se vydani dotazu", e);
			}
		}
	}

	public static void tryClear() throws HibernateSessionContextException {
		if (hs() != null && hs().getTransaction().isActive())
			hs().clear();
	}
}
