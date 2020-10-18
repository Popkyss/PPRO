package com.popkyss.sweetShop.setting;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class JPAUnits {
	private static JPAUnits ju;
	private Map<String, EMF> persistenceUnits = new HashMap<String, EMF>();

	private JPAUnits(String persistenceUnit) {
		this.persistenceUnits.put(persistenceUnit, createEMF(persistenceUnit));
	}

	public static EntityManager getEM(String persistenceUnit) {
		if (ju != null) {
			if (ju.persistenceUnits.containsKey(persistenceUnit)) {
				return ((EMF) ju.persistenceUnits.get(persistenceUnit)).getEmf().createEntityManager();
			}
			EMF emf = createEMF(persistenceUnit);
			ju.persistenceUnits.put(persistenceUnit, emf);
			return emf.getEmf().createEntityManager();
		}

		ju = new JPAUnits(persistenceUnit);
		return getEM(persistenceUnit);
	}

	private static EMF createEMF(String persistenceUnit) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
		return new EMF(emf);
	}

	private static class EMF {
		private EntityManagerFactory emf;

		public EMF(EntityManagerFactory emf) {
			this.emf = emf;
		}

		public EntityManagerFactory getEmf() {
			return this.emf;
		}
	}
}
