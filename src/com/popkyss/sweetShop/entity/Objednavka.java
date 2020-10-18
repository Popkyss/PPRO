package com.popkyss.sweetShop.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Obejdnavka
 */
@Entity
@Table(name = "OBJEDNAVKA")
public class Objednavka implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int idObjednavka;
	
	private Adresa idAdresa;
	
	private Zakaznik idZakaznik;
	
	private Data idData;

	private int stav;
	
	private int cislo;
	
	private Date zmeneno;
	
	private Date vytvoreno;
		
	
	
	public Objednavka() {
	}


	@Id
	@Column(name = "OBJEDNAVKA_ID", unique = true, nullable = false, precision = 8, scale = 0)
	public int getIdObjednavka() {
		return idObjednavka;
	}


	public void setIdObjednavka(int idObjednavka) {
		this.idObjednavka = idObjednavka;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADRESA_ID", nullable = false)
	public Adresa getIdAdresa() {
		return idAdresa;
	}


	public void setIdAdresa(Adresa idAdresa) {
		this.idAdresa = idAdresa;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ZAKAZNIK_ID", nullable = false)
	public Zakaznik getIdZakaznik() {
		return idZakaznik;
	}


	public void setIdZakaznik(Zakaznik idZakaznik) {
		this.idZakaznik = idZakaznik;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DATA_ID", nullable = false)
	public Data getIdData() {
		return idData;
	}


	public void setIdData(Data idData) {
		this.idData = idData;
	}

	@Column(name = "STAV", nullable = false, precision = 1, scale = 0)
	public int getStav() {
		return stav;
	}


	public void setStav(int stav) {
		this.stav = stav;
	}

	@Column(name = "CISLO", nullable = false, precision = 10, scale = 0)
	public int getCislo() {
		return cislo;
	}


	public void setCislo(int cislo) {
		this.cislo = cislo;
	}

	@Column(name = "VYTVORENO", length = 7)
	public Date getVytvoreno() {
		return vytvoreno;
	}


	public void setVytvoreno(Date vytvoreno) {
		this.vytvoreno = vytvoreno;
	}

	@Column(name = "ZMENENO", length = 7)
	public Date getZmeneno() {
		return zmeneno;
	}


	public void setZmeneno(Date zmeneno) {
		this.zmeneno = zmeneno;
	}
}
