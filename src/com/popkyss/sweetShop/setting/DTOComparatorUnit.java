package com.popkyss.sweetShop.setting;

import java.io.Serializable;

public class DTOComparatorUnit implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean ascending = true;
	private String attribute;
	private SerializableComparator comparator;

	public DTOComparatorUnit(String attribute) {
		this.attribute = attribute;
	}

	public DTOComparatorUnit(String attribute, boolean ascending) {
		this.attribute = attribute;
		this.ascending = ascending;
	}

	public DTOComparatorUnit(String attribute, SerializableComparator comp) {
		if (comp == null) {
			throw new NullPointerException("predany komparator nesmi byt null");
		}
		this.attribute = attribute;
		this.comparator = comp;
	}

	public boolean isAscending() {
		return this.ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	public String getAttribute() {
		return this.attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public SerializableComparator getComparator() {
		return this.comparator;
	}

	public void setComparator(SerializableComparator comparator) {
		this.comparator = comparator;
	}
}
