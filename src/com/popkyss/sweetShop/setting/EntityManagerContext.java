package com.popkyss.sweetShop.setting;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;


public class EntityManagerContext {
	public static final String TRANSACTION_STATUS = "transaction_status";
	private static final Logger log = Logger.getLogger(EntityManagerContext.class);

	private static ThreadLocal<EntityManager> entityManager = new ThreadLocal<EntityManager>();

	private static ThreadLocal<TransactionSwitchSet> transactionStatus = new ThreadLocal<TransactionSwitchSet>();

	public static EntityManager em() {
		return entityManager.get();
	}

	public static void setEm(EntityManager em) {
		entityManager.set(em);
	}

	public static TransactionSwitchSet getTransactionStatus() {
		return transactionStatus.get();
	}

	public static void setTransactionStatus(TransactionSwitchSet etss) {
		transactionStatus.set(etss);
	}

	public static void commit() {
		if (em() != null && em().isOpen()) {
			if (em().getTransaction().isActive()) {
				em().getTransaction().commit();
			}
			em().close();
		}
	}

	public static void rollback() {
		if (em() != null && em().isOpen()) {
			if (em().getTransaction().isActive()) {
				em().getTransaction().rollback();
			}
			em().close();
		}
	}

	public static void close() {
		if (em() != null && em().isOpen()) {
			if (log.isDebugEnabled()) {
				log.debug("Uzaviram entity manager context");
			}
			em().close();
		}
	}
}
