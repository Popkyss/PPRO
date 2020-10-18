package com.popkyss.sweetShop.setting;

public class ReverseSerializableComparator implements SerializableComparator {
	private static final long serialVersionUID = 1L;
	private SerializableComparator comp;

	public ReverseSerializableComparator(SerializableComparator comp) {
		if (comp == null) {
			throw new NullPointerException("Predany komparator nesmi byt null");
		}
		this.comp = comp;
	}

	public int compare(Object o1, Object o2) {
		return this.comp.compare(o2, o1);
	}
}