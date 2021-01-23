package com.popkyss.sweetShop.setting;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;



public abstract class ASeznamBean<RadekSeznamu extends IRadekSeznamu> extends AbstractBean
		implements IStrankovani<RadekSeznamu>, ISeznamSloupcuExport {
	private static final long serialVersionUID = -1601346090579949489L;
	private static final Logger log = Logger.getLogger(ASeznamBean.class);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StrankovanySeznam<RadekSeznamu> strankovanySeznam = new StrankovanySeznam();
	protected SloupecSeznam sloupecSeznam = new SloupecSeznam();

	@Deprecated
	protected Date datumVyberu = null;

	protected void addSloupec(String name) {
		if (validujSloupec(name)) {
			this.sloupecSeznam.addSloupec(name, getBeanNumber(), null);
		}
	}

	@SuppressWarnings("rawtypes")
	private boolean validujSloupec(String name) {
		if (StringUtils.isEmpty(name)) {
			log.warn("Sloupec pro strankovany seznam nesmi mit prazdny nazev");
			return false;
		}

		try {
			Class<?> dtoClass = (Class) ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[getRadekSeznamuPoradiGenerikum()];

			String[] casti = name.split("\\.");
			Object o = ReflectionUtils.getClassInstance(dtoClass.getName());
			Class<?> oClass = dtoClass;
			int i = 0;
			do {
				String method = ReflectionUtils.getGetterMethod(o, casti[i]);
				Object o2 = ReflectionUtils.invokeMethod(oClass, method, o, new Object[0]);

				o = o2;
				oClass = (o == null) ? null : o.getClass();
				++i;
			} while (i < casti.length && o != null);

			return true;
		} catch (Exception e) {
			log.warn("Nelze pridat sloupec " + name + " pro strankovany seznam", e);
			return false;
		}
	}

	protected void addSloupec(String name, SerializableComparator comparator) {
		if (comparator == null) {
			throw new NullPointerException("predany komparator nesmi byt null");
		}
		if (validujSloupec(name)) {
			this.sloupecSeznam.addSloupec(name, getBeanNumber(), comparator);
		}
	}

	public StrankovanySeznam<RadekSeznamu> getStrankovanySeznam() {
		return this.strankovanySeznam;
	}

	public SloupecSeznam getSloupceSeznam() {
		return this.sloupecSeznam;
	}

	public Map<String, Sloupec> getSloupecSeznam() {
		return this.sloupecSeznam.getSloupecSeznam();
	}

	public List<Sloupec> getSloupceList() {
		List<Sloupec> sloupce = new ArrayList<Sloupec>();
		for (Map.Entry<String, Sloupec> e : getSloupecSeznam().entrySet()) {
			sloupce.add(e.getValue());
		}
		return sloupce;
	}

	@SuppressWarnings("rawtypes")
	public List<FormatSloupce> getSloupcePopisovace() {
		List<FormatSloupce> popisovace = new ArrayList<FormatSloupce>();

		try {
			Class<?> dtoClass = (Class) ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[getRadekSeznamuPoradiGenerikum()];
			for (Sloupec sl : getSloupceList()) {
				FormatSloupce form = ExportUtils.getFormatSloupce(dtoClass, sl);
				popisovace.add(form);
			}
		} catch (Exception e) {
			log.error("Chyba pri vytvareni seznamu sloupcu pro strankovany seznam", e);
			throw new RuntimeException(e);
		}

		return popisovace;
	}

	protected int getRadekSeznamuPoradiGenerikum() {
		return 0;
	}

	public void setSloupceSeznam(SloupecSeznam sloupecSeznam) {
		this.sloupecSeznam = sloupecSeznam;
	}

	public void setSloupecSeznam(Map<String, Sloupec> sloupecSeznam) {
		this.sloupecSeznam.setSloupecSeznam(new LinkedHashMap<String, Sloupec>(sloupecSeznam));
	}

	public void setStrankovanySeznam(StrankovanySeznam<RadekSeznamu> strankovanySeznam) {
		this.strankovanySeznam = strankovanySeznam;
	}

	@SuppressWarnings("deprecation")
	public void nastaveniStrankyAction(String stranka) throws NumberFormatException {
		if (log.isTraceEnabled()) {
			log.trace("Odstrankovani: " + stranka);
		}
		this.strankovanySeznam.setStranka((new Integer(stranka)).intValue());
	}

	public void sort(String sloupecName) {
		if (log.isTraceEnabled()) {
			log.trace("Vybran sloupec: " + sloupecName);
		}
		this.sloupecSeznam.kontrolaExistenceSloupce(sloupecName);
		Sloupec sloupec = this.sloupecSeznam.get(sloupecName);

		if (sloupec.isTrideni()) {
			this.sloupecSeznam.reverseOrderSloupecTrideni(sloupecName);
			sort();

		} else {

			deleteAllSloupceTrideni();
			addSloupecTrideni(sloupecName);
			sort();
		}
	}

	public void sort() {
		List<RadekSeznamu> list = this.strankovanySeznam.getRadkySeznamu();
		Collections.sort(list,
				(Comparator<? super RadekSeznamu>) new DTOComparatorII(this.sloupecSeznam.getSloupceSlozenehoTrideni(),
						LocaleAction.getLocale()));

		for (int i = 0; i < list.size() && list.get(i) instanceof ARadekSeznamu; i++) {
			ARadekSeznamu radek = (ARadekSeznamu) list.get(i);
			radek.setPoradi(i + 1);
		}

		this.strankovanySeznam.setRadkySeznamu(list);
	}

	public void addSortCriteria(String sloupecName) {
		if (log.isTraceEnabled()) {
			log.trace("pridavam sloupec " + sloupecName + " do trideni");
		}
		addSloupecTrideni(sloupecName);
	}

	protected void addSloupecTrideni(String sloupecName) {
		Sloupec sl = this.sloupecSeznam.get(sloupecName);
		if (sl == null) {
			log.warn("Snazis se tridit podle sloupce " + sloupecName + " , ktery neni definovany v seznamu sloupcu");
			return;
		}
		this.sloupecSeznam.addSloupecTrideni(sloupecName, true, sl.getComparator());
	}

	protected void addSloupecTrideni(String sloupecName, boolean ascending) {
		Sloupec sl = this.sloupecSeznam.get(sloupecName);
		if (sl == null) {
			log.warn("Snazis se tridit podle sloupce " + sloupecName + " , ktery neni definovany v seznamu sloupcu");

			return;
		}
		SerializableComparator cmp = sl.getComparator();
		if (cmp != null && ascending != sl.isAscending()) {
			cmp = DatexCollections.reverseOrder(cmp);
		}
		this.sloupecSeznam.addSloupecTrideni(sloupecName, ascending, cmp);
		sort();
	}

	protected void addSloupecTrideni(String sloupecName, SerializableComparator comparator) {
		if (comparator == null) {
			throw new NullPointerException("predany komparator nesmi byt null");
		}
		this.sloupecSeznam.addSloupecTrideni(sloupecName, false, comparator);
		sort();
	}

	public void removeSortCriteria(String sloupecName) {
		log.trace("odebiram sloupec " + sloupecName + " z trideni");
		deleteSloupecTrideni(sloupecName);
	}

	protected void deleteSloupecTrideni(String sloupecName) {
		this.sloupecSeznam.kontrolaExistenceSloupce(sloupecName);
		this.sloupecSeznam.deleteSloupecTrideni(sloupecName);
		sort();
	}

	public void deleteAllSloupceTrideni() {
		this.sloupecSeznam.deleteAllSloupceTrideni();
		sort();
	}

	protected void setRadkySeznamu(List<RadekSeznamu> radkySeznamu) {
		this.strankovanySeznam.setRadkySeznamu(radkySeznamu);
		this.strankovanySeznam.setStranka(1);

		setDatumVyberu(new Date());
		sort();
	}

	@Deprecated
	public Date getDatumVyberu() {
		return this.datumVyberu;
	}

	@Deprecated
	public void setDatumVyberu(Date datumVyberu) {
		this.datumVyberu = datumVyberu;
	}
}