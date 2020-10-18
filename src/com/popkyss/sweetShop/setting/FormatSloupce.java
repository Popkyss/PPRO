package com.popkyss.sweetShop.setting;

import java.io.Serializable;

public class FormatSloupce implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String DEFAULT_MASKA_CISLO_DESETINNE = "#0.000";
	public static final String DEFAULT_MASKA_CISLO_CELE = "#0";
	public static final String DEFALUT_MASKA_DATUM = "dd.MM.yyyy HH:mm";
	private String nazevSloupce;
	private String nazevSloupceLng;

	public enum Zarovnani {
		VLEVO, NASTRED, VPRAVO;
	}

	public enum SmerTrideni {
		VZESTUPNE, SESTUPNE;

		public boolean isAscending() {
			return (this == VZESTUPNE);
		}
	}

	private int poradiTrideni = 0;
	private boolean trideni = false;
	private SmerTrideni smerTrideni = SmerTrideni.SESTUPNE;
	private Zarovnani zarovnaniHlavicka = Zarovnani.NASTRED;

	private String maskaCislo;

	private String maskaDatum;

	private Class<?> typHodnoty;

	private Zarovnani zarovnaniHodnota;

	private boolean zalamovatHodnota = false;
	private boolean zalamovatHlavicka = true;
	private String substituteHodnota;

	public FormatSloupce(Class<?> typHodnoty) {
		this.typHodnoty = typHodnoty;
	}

	public String getNazevSloupce() {
		return this.nazevSloupce;
	}

	public void setNazevSloupce(String nazevSloupce) {
		this.nazevSloupce = nazevSloupce;
	}

	public String getNazevSloupceLng() {
		return this.nazevSloupceLng;
	}

	public void setNazevSloupceLng(String nazevSloupceLng) {
		this.nazevSloupceLng = nazevSloupceLng;
	}

	public int getPoradiTrideni() {
		return this.poradiTrideni;
	}

	public void setPoradiTrideni(int poradiTrideni) {
		this.poradiTrideni = poradiTrideni;
	}

	public boolean isTrideni() {
		return this.trideni;
	}

	public void setTrideni(boolean trideni) {
		this.trideni = trideni;
	}

	public SmerTrideni getSmerTrideni() {
		return this.smerTrideni;
	}

	public void setSmerTrideni(SmerTrideni smerTrideni) {
		this.smerTrideni = smerTrideni;
	}

	public Zarovnani getZarovnaniHlavicka() {
		return this.zarovnaniHlavicka;
	}

	public void setZarovnaniHlavicka(Zarovnani zarovnaniHlavicka) {
		this.zarovnaniHlavicka = zarovnaniHlavicka;
	}

	public String getMaskaCislo() {
		return this.maskaCislo;
	}

	public void setMaskaCislo(String maskaCislo) {
		this.maskaCislo = maskaCislo;
	}

	public String getMaskaDatum() {
		return this.maskaDatum;
	}

	public void setMaskaDatum(String maskaDatum) {
		this.maskaDatum = maskaDatum;
	}

	public Class<?> getTypHodnoty() {
		return this.typHodnoty;
	}

	public void setTypHodnoty(Class<?> typHodnoty) {
		this.typHodnoty = typHodnoty;
	}

	public Zarovnani getZarovnaniHodnota() {
		return this.zarovnaniHodnota;
	}

	public void setZarovnaniHodnota(Zarovnani zarovnaniHodnota) {
		this.zarovnaniHodnota = zarovnaniHodnota;
	}

	public String getSubstituteHodnota() {
		return this.substituteHodnota;
	}

	public void setSubstituteHodnota(String substituteHodnota) {
		this.substituteHodnota = substituteHodnota;
	}

	public boolean isZalamovatHodnota() {
		return this.zalamovatHodnota;
	}

	public void setZalamovatHodnota(boolean zalamovat) {
		this.zalamovatHodnota = zalamovat;
	}

	public boolean isZalamovatHlavicka() {
		return this.zalamovatHlavicka;
	}

	public void setZalamovatHlavicka(boolean zalamovatHlavicka) {
		this.zalamovatHlavicka = zalamovatHlavicka;
	}
}