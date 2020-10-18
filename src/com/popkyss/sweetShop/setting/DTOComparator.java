package com.popkyss.sweetShop.setting;

import java.text.Collator;
import java.util.Locale;

public class DTOComparator implements SerializableComparator {
	private static final long serialVersionUID = 1L;
	public static final Collator DEFAULT_COLLATOR = Collator.getInstance(new Locale("cs", "CZ"));

	private String[] attributes;

	private Locale locale;
	private transient Collator col = DEFAULT_COLLATOR;

	private boolean ascending = true;

	private SerializableComparator comparator;

	public DTOComparator(String attribute, boolean ascending) {
		if (attribute == null) {
			throw new NullPointerException("atribut nesmi byt null");
		}
		attribute = attribute.trim();
		if (attribute.equals("")) {
			throw new NullPointerException("atribut musi obsahovat nejake znaky");
		}
		this.attributes = attribute.split("\\.");
		this.ascending = ascending;
	}

	public DTOComparator(String attribute, boolean ascending, Locale locale) {
		this(attribute, ascending);
		this.locale = locale;
		this.col = Collator.getInstance(locale);
	}

	public DTOComparator(String attribute) {
		this(attribute, true);
	}

	public DTOComparator(String attribute, SerializableComparator comp) {
		this(attribute);
		if (comp == null) {
			throw new NullPointerException("Predany komparator nesmi byt null");
		}
		this.comparator = comp;
	}

	@SuppressWarnings("unchecked")
	public int compare(Object o1, Object o2) {
		if (o1 == null && o2 == null) {
			return 0;
		}

		for (int i = 0; i < this.attributes.length; i++) {
			String method = ReflectionUtils.getGetterMethod((o1 == null) ? o2 : o1, this.attributes[i]);
			try {
				if (o1 != null && o2 != null) {
					o1 = o1.getClass().getMethod(method, new Class[0]).invoke(o1, new Object[0]);
					o2 = o2.getClass().getMethod(method, new Class[0]).invoke(o2, new Object[0]);
				}
			} catch (Exception e) {
				throw new RuntimeException("Cannot compare " + o1 + " with " + o2 + " on " + method, e);
			}
		}

		if (this.comparator != null) {
			return this.comparator.compare(o1, o2);
		}
		if (o1 instanceof String && o2 instanceof String) {

			if (this.ascending) {
				return getCol().compare(o1, o2);
			}
			return getCol().compare(o2, o1);
		}

		if (this.ascending) {
			return (o1 == null) ? -1 : ((o2 == null) ? 1 : ((Comparable<Object>) o1).compareTo(o2));
		}
		return (o1 == null) ? 1 : ((o2 == null) ? -1 : ((Comparable<Object>) o2).compareTo(o1));
	}

	private Collator getCol() {
		if (this.col == null) {
			this.col = (this.locale == null) ? DEFAULT_COLLATOR : Collator.getInstance(this.locale);
		}
		return this.col;
	}

	public SerializableComparator getComparator() {
		return this.comparator;
	}
}