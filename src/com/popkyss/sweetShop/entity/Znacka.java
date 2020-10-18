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
 * Znacka
 */
@Entity
@Table(name = "ZNACKA")
public class Znacka implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int idZnacka;

	private String nazev;

	private int aktivni;

	private Set<Produkt> produkts = new HashSet<Produkt>(0);
	
	
	public Znacka() {
	}


	@Id
	@Column(name = "ZNACKA_ID", unique = true, nullable = false, precision = 8, scale = 0)
	public int getIdZnacka() {
		return idZnacka;
	}
	
	
	public void setIdZnacka(int idZnacka) {
		this.idZnacka = idZnacka;
	}
	
	@Column(name = "NAZEV", nullable = false, length = 20)
	public String getNazev() {
		return nazev;
	}


	public void setNazev(String nazev) {
		this.nazev = nazev;
	}

	@Column(name = "AKTIVNI", nullable = false, precision = 1, scale = 0)
	public int getAktivni() {
		return aktivni;
	}

	
	public void setAktivni(int aktivni) {
		this.aktivni = aktivni;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idZnacka")
	public Set<Produkt> getProdukts() {
		return produkts;
	}


	public void setProdukts(Set<Produkt> produkts) {
		this.produkts = produkts;
	}	
	
}
