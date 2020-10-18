package com.popkyss.sweetShop.setting;

import java.text.Collator;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class DTOComparatorII implements SerializableComparator {
	private static final long serialVersionUID = 1L;
	private static final Locale DEFAULT_LOCALE = new Locale("cs", "CZ");
	public static final Collator DEFAUL_COLLATOR = Collator.getInstance(DEFAULT_LOCALE);

	private List<DTOComparatorUnit> units;

	private String[][] attributes;

	private Locale locale;

	private transient Collator col = DEFAUL_COLLATOR;

	public DTOComparatorII(List<DTOComparatorUnit> units) {
		this(units, DEFAULT_LOCALE);
	}

	public DTOComparatorII(DTOComparatorUnit... units) {
		this(Arrays.asList(units));
	}

	public DTOComparatorII(List<DTOComparatorUnit> getters, Locale locale) {
		this.units = getters;
		this.locale = locale;
		this.col = Collator.getInstance(locale);
		this.attributes = new String[this.units.size()][];
		for (int i = 0; i < this.attributes.length; i++) {
			this.attributes[i] = getUnitAttributes(this.units.get(i));
		}
	}

	private String[] getUnitAttributes(DTOComparatorUnit unit) {
		String attribute;
		if (unit == null || (attribute = unit.getAttribute()) == null) {
			throw new NullPointerException("atribut nesmi byt null");
		}
		attribute = attribute.trim();
		if (attribute.equals("")) {
			throw new NullPointerException("atribut musi obsahovat nejake znaky");
		}
		String[] attributes = attribute.split("\\.");

		return attributes;
	}

	public int compare(Object o1, Object o2) {
		Object temp1 = o1;
		Object temp2 = o2;
		Iterator<DTOComparatorUnit> iter = this.units.iterator();
		int i = 0;
		String method = "";
		int j = 0;
		while (temp1 != null && temp2 != null && iter.hasNext() && i == 0) {
			DTOComparatorUnit unit = iter.next();
			for (int k = 0; k < (this.attributes[j]).length && temp1 != null && temp2 != null; k++) {
				method = ReflectionUtils.getGetterMethod(temp1, this.attributes[j][k]);
				try {
					temp1 = temp1.getClass().getMethod(method, new Class[0]).invoke(temp1, new Object[0]);
					temp2 = temp2.getClass().getMethod(method, new Class[0]).invoke(temp2, new Object[0]);
				} catch (Exception e) {
					throw new RuntimeException("Cannot compare " + temp1 + " with " + o2 + " on " + method, e);
				}
			}

			j++;
			i = compare(temp1, temp2, unit);
			temp1 = o1;
			temp2 = o2;
		}
		return i;
	}

	@SuppressWarnings("unchecked")
	protected int compare(Object o1, Object o2, DTOComparatorUnit unit) {
		if (unit.getComparator() != null) {
			return unit.getComparator().compare(o1, o2);
		}

		boolean ascending = unit.isAscending();
		if (o1 instanceof String && o2 instanceof String) {
			if (ascending) {
				return getCol().compare(o1, o2);
			}
			return getCol().compare(o2, o1);
		}

		if (ascending) {
			return (o1 == null && o2 == null) ? 0
					: ((o1 == null) ? -1 : ((o2 == null) ? 1 : ((Comparable<Object>) o1).compareTo(o2)));
		}
		return (o1 == null && o2 == null) ? 0
				: ((o1 == null) ? 1 : ((o2 == null) ? -1 : ((Comparable<Object>) o2).compareTo(o1)));
	}

	private Collator getCol() {
		if (this.col == null) {
			this.col = Collator.getInstance(this.locale);
		}
		return this.col;
	}
}