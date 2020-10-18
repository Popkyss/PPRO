package com.popkyss.sweetShop.setting;

import java.io.Serializable;

public class KriteriumSestavy implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String ODDELOVAC_CARKA = ", ";
	public static final String ODDELOVAC_NOVY_RADEK = "\n";
	public static final String DEFAULT_CISLO_MASKA = "";
	public static final String DEFAULT_DATUM_MASKA = "dd.MM.yyyy HH:mm";
	private String nazevKlic;
	private String oddelovac;
	private Object hodnota;
	private String maskaCislo = "";
	private String maskaDatum = "dd.MM.yyyy HH:mm";
	private KriteriumFiltru.ExpandList expandList = KriteriumFiltru.ExpandList.NONE;

	public KriteriumSestavy(String nazevKlic) {
		this(nazevKlic, null, null, null, null);
	}

	public KriteriumSestavy(String nazevKlic, String oddelovac, Object hodnota, String maskaCislo, String maskaDatum) {
		this.nazevKlic = nazevKlic;
		this.oddelovac = oddelovac;
		this.hodnota = hodnota;
		this.maskaCislo = maskaCislo;
		this.maskaDatum = maskaDatum;
	}

	public String getNazevKlic() {
		return this.nazevKlic;
	}

	public void setNazevKlic(String nazevKlic) {
		this.nazevKlic = nazevKlic;
	}

	public String getOddelovac() {
		return this.oddelovac;
	}

	public void setOddelovac(String oddelovac) {
		this.oddelovac = oddelovac;
	}

	public Object getHodnota() {
		return this.hodnota;
	}

	public void setHodnota(Object hodnota) {
		this.hodnota = hodnota;
	}

	public void setMaskaCislo(String maskaCislo) {
		this.maskaCislo = maskaCislo;
	}

	public String getMaskaCislo() {
		return (this.maskaCislo == null) ? "" : this.maskaCislo;
	}

	public void setMaskaDatum(String maskaDatum) {
		this.maskaDatum = maskaDatum;
	}

	public String getMaskaDatum() {
		return (this.maskaDatum == null) ? "dd.MM.yyyy HH:mm" : this.maskaDatum;
	}

	public void setExpandList(KriteriumFiltru.ExpandList expandList) {
		this.expandList = expandList;
	}

	public KriteriumFiltru.ExpandList getExpandList() {
		return (this.expandList == null) ? KriteriumFiltru.ExpandList.NONE : this.expandList;
	}
}