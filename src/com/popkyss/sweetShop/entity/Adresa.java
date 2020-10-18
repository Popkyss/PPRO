package com.popkyss.sweetShop.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Adresa
 */
@Entity
@Table(name = "ADRESA")
public class Adresa implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private int idAdresa;

	private String ulice;

	private String cp;
	
	private String mesto;
	
	private int psc;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idAdresa")
	private Set<Objednavka> objednavkas = new HashSet<Objednavka>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idAdresa")
	private Set<Dodavatel> dodavatels = new HashSet<Dodavatel>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idAdresa")
	private Set<Zamestnanec> zamestnanecs = new HashSet<Zamestnanec>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idAdresa")
	private Set<Zakaznik> zakazniks = new HashSet<Zakaznik>(0);
	
	
	public Adresa() {
	}


	
	@Column(name = "ADRESA_ID", unique = true, nullable = false, precision = 8, scale = 0)
	public int getIdAdresa() {
		return idAdresa;
	}
	
	
	public void setIdAdresa(int idAdresa) {
		this.idAdresa = idAdresa;
	}

	@Column(name = "ULICE", nullable = false, length = 100)
	public String getUlice() {
		return this.ulice;
	}


	public void setUlice(String ulice) {
		this.ulice = ulice;
	}


	@Column(name = "CP", nullable = false, length = 10)
	public String getCp() {
		return this.cp;
	}


	public void setCp(String cp) {
		this.cp = cp;
	}


	@Column(name = "MESTO", nullable = false, length = 50)
	public String getMesto() {
		return mesto;
	}


	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	@Column(name = "PSC", nullable = false, precision = 5, scale = 0)
	public int getPsc() {
		return psc;
	}


	public void setPsc(int psc) {
		this.psc = psc;
	}
	
	
	public Set<Objednavka> getObjednavkas() {
		return this.objednavkas;
	}
	
	
	public void setObjednavkas(Set<Objednavka> objednavkas) {
		this.objednavkas = objednavkas;
	}

	
	public Set<Dodavatel> getDodavatels() {
		return dodavatels;
	}


	public void setDodavatels(Set<Dodavatel> dodavatels) {
		this.dodavatels = dodavatels;
	}

	
	public Set<Zamestnanec> getZamestnanecs() {
		return zamestnanecs;
	}


	public void setZamestnanecs(Set<Zamestnanec> zamestnanecs) {
		this.zamestnanecs = zamestnanecs;
	}

	
	public Set<Zakaznik> getZakazniks() {
		return zakazniks;
	}


	public void setZakazniks(Set<Zakaznik> zakazniks) {
		this.zakazniks = zakazniks;
	}

}
