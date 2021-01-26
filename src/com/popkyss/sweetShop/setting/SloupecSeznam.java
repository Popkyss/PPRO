package com.popkyss.sweetShop.setting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class SloupecSeznam implements Serializable {
	private static final long serialVersionUID = 7151155210255229911L;
	private static Logger log = Logger.getLogger(SloupecSeznam.class);

	private Map<String, Sloupec> sloupecSeznam = null;

	public SloupecSeznam() {
		this.sloupecSeznam = new LinkedHashMap<String, Sloupec>();
	}

	public void put(String sloupecName, Sloupec sloupec) {
		this.sloupecSeznam.put(sloupecName, sloupec);
	}

	public void addSloupec(String sloupecName, String beanNumber, SerializableComparator comparator) {
		Sloupec sloupec = new Sloupec();
		sloupec.setBeanNumber(beanNumber);
		sloupec.setSloupec(sloupecName);
		sloupec.setComparator(comparator);
		put(sloupecName, sloupec);
	}

	public void addSloupecTrideni(String sloupecName, boolean ascending, SerializableComparator comparator) {
		kontrolaExistenceSloupce(sloupecName);

		Sloupec sloupec = get(sloupecName);
		if (sloupec.isTrideni()) {
			deleteSloupecTrideni(sloupecName);
		}

		sloupec.setPoradiTrideni(getNejvyssiPoradiTrideni() + 1);
		sloupec.setTrideni(true);
		sloupec.setAscending(ascending);
		sloupec.setComparator(comparator);
		log.trace("add sloupec: " + sloupecName + " poradi: " + sloupec.getPoradiTrideni());
		put(sloupecName, sloupec);
	}

	public void reverseOrderSloupecTrideni(String sloupecName) {
		kontrolaExistenceSloupce(sloupecName);

		Sloupec sl = get(sloupecName);
		if (!sl.isTrideni()) {
			addSloupecTrideni(sloupecName, !sl.isAscending(),
					(sl.getComparator() == null) ? null : PokyCollections.reverseOrder(sl.getComparator()));
		} else {

			sl.setAscending(!sl.isAscending());
			sl.setComparator((sl.getComparator() == null) ? null : PokyCollections.reverseOrder(sl.getComparator()));
			put(sloupecName, sl);
		}
	}

	public void deleteSloupecTrideni(String sloupecName) {
		kontrolaExistenceSloupce(sloupecName);
		int poradiTrideni = get(sloupecName).getPoradiTrideni();

		if (poradiTrideni > 0) {

			Sloupec sloupec = get(sloupecName);
			sloupec.setPoradiTrideni(0);
			sloupec.setTrideni(false);
			put(sloupecName, sloupec);

			Iterator<Sloupec> it = getIteratorSeznamSloupcu();
			while (it.hasNext()) {
				Sloupec sl = it.next();
				if (sl.isTrideni() && sl.getPoradiTrideni() > poradiTrideni) {
					sl.setPoradiTrideni(sl.getPoradiTrideni() - 1);
					put(sl.getSloupec(), sl);
				}
			}
		}
	}

	public void deleteAllSloupceTrideni() {
		Iterator<Sloupec> it = getIteratorSeznamSloupcu();
		while (it.hasNext()) {
			Sloupec sl = it.next();
			if (sl.isTrideni()) {
				sl.setPoradiTrideni(0);
				sl.setTrideni(false);
				put(sl.getSloupec(), sl);
			}
		}
	}

	public Sloupec get(String sloupecName) {
		return this.sloupecSeznam.get(sloupecName);
	}

	public Map<String, Sloupec> getSloupecSeznam() {
		return this.sloupecSeznam;
	}

	public void setSloupecSeznam(Map<String, Sloupec> sloupecSeznam) {
		this.sloupecSeznam = sloupecSeznam;
	}

	public List<DTOComparatorUnit> getSloupceSlozenehoTrideni() {
		List<DTOComparatorUnit> slST = new ArrayList<DTOComparatorUnit>();
		List<Sloupec> col = new ArrayList<Sloupec>(this.sloupecSeznam.values());
		Collections.sort(col, (Comparator<? super Sloupec>) new DTOComparator("poradiTrideni", true));
		Iterator<Sloupec> it = col.iterator();
		while (it.hasNext()) {
			Sloupec sloupec = it.next();
			if (sloupec.isTrideni()) {
				log.trace("getSloupceSlozenehoTrideni - sloupec: " + sloupec.getSloupec());
				if (sloupec.getComparator() == null) {
					slST.add(new DTOComparatorUnit(sloupec.getSloupec(), sloupec.isAscending()));
					continue;
				}
				slST.add(new DTOComparatorUnit(sloupec.getSloupec(), sloupec.getComparator()));
			}
		}

		log.trace("getSloupceSlozenehoTrideni - velikost: " + slST.size());
		return slST;
	}

	public void kontrolaExistenceSloupce(String sloupecName) {
		if (!isExistSloupec(sloupecName)) {
			throw new IllegalArgumentException("Tridici sloupec: " + sloupecName + " neexistuje v seznamu sloupcu");
		}
	}

	public boolean isExistSloupec(String sloupecName) {
		return this.sloupecSeznam.containsKey(sloupecName);
	}

	private int getNejvyssiPoradiTrideni() {
		return getSloupceSlozenehoTrideni().size();
	}

	private Iterator<Sloupec> getIteratorSeznamSloupcu() {
		Collection<Sloupec> col = this.sloupecSeznam.values();
		Iterator<Sloupec> it = col.iterator();
		return it;
	}
}