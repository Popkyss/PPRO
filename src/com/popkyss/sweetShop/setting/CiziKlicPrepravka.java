package com.popkyss.sweetShop.setting;

import java.io.Serializable;

public class CiziKlicPrepravka<K, V> implements Serializable {
	private static final long serialVersionUID = 1L;
	private K id;
	private V value;
	private Object dalsiInfo;

	public CiziKlicPrepravka() {
	}

	public CiziKlicPrepravka(K id, V value) {
		this(id, value, null);
	}

	public CiziKlicPrepravka(K id, V value, Object dalsiInfo) {
		this.id = id;
		this.value = value;
		this.dalsiInfo = dalsiInfo;
	}

	public K getId() {
		return this.id;
	}

	public void setId(K id) {
		this.id = id;
	}

	public V getValue() {
		return this.value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public void setDalsiInfo(Object dalsiInfo) {
		this.dalsiInfo = dalsiInfo;
	}

	public Object getDalsiInfo() {
		return this.dalsiInfo;
	}
}