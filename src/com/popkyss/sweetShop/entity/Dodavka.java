package com.popkyss.sweetShop.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Dodavka
 */
@Entity
@Table(name = "DODAVKA")
public class Dodavka implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int idDodavka;

	private Dodavatel idDodavatel;

	private Date prebrano;
	
	private int pocetKusu;
	
	private int cena;
	

	private Set<Produkt> produkts = new HashSet<Produkt>(0);
	
	
	public Dodavka() {
	}


	@Id
	@Column(name = "DODAVKA_ID", unique = true, nullable = false, precision = 8, scale = 0)
	public int getIdDodavka() {
		return idDodavka;
	}
	
	public void setIdDodavka(int idDodavka) {
		this.idDodavka = idDodavka;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DODAVATEL_ID", nullable = false)
	public Dodavatel getIdDodavatel() {
		return idDodavatel;
	}
	
	
	public void setIdDodavatel(Dodavatel idDodavatel) {
		this.idDodavatel = idDodavatel;
	}
	
	@Column(name = "PREBRANO", length = 7)
	public Date getPrebrano() {
		return prebrano;
	}


	public void setPrebrano(Date prebrano) {
		this.prebrano = prebrano;
	}

	@Column(name = "POCET_KUSU", precision = 4, scale = 0)
	public int getPocetKusu() {
		return pocetKusu;
	}


	public void setPocetKusu(int pocetKusu) {
		this.pocetKusu = pocetKusu;
	}

	@Column(name = "CENA", precision = 10, scale = 2)
	public int getCena() {
		return cena;
	}


	public void setCena(int cena) {
		this.cena = cena;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idDodavka")
	public Set<Produkt> getProdukts() {
		return produkts;
	}


	public void setProdukts(Set<Produkt> produkts) {
		this.produkts = produkts;
	}	
	
}
