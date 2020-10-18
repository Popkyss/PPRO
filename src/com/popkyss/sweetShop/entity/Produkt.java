package com.popkyss.sweetShop.entity;

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
 * Produkt
 */
@Entity
@Table(name = "PRODUKT")
public class Produkt implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int idProdukt;

	private Dodavka idDodavka;

	private Znacka idZnacka;
	
	private Sleva idSleva;
	
	private String nazev;
	
	private int cena;
	
	private Integer velikostBot;
	
	private Integer vaha;
	
	private Integer vyska;
	
	private Integer sirka;
	
	private Integer hloubka;
	
	private Integer barva;
	
	private Integer pohlavi;
	
	

	private Set<Polozka> polozkas = new HashSet<Polozka>(0);
	
	private Set<Skupina> skupinas = new HashSet<Skupina>(0);
	
	
	public Produkt() {
	}


	@Id
	@Column(name = "PRODUKT_ID", unique = true, nullable = false, precision = 8, scale = 0)
	public int getIdProdukt() {
		return idProdukt;
	}


	public void setIdProdukt(int idProdukt) {
		this.idProdukt = idProdukt;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DODAVKA_ID", nullable = false)
	public Dodavka getIdDodavka() {
		return idDodavka;
	}


	public void setIdDodavka(Dodavka idDodavka) {
		this.idDodavka = idDodavka;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ZNACKA_ID", nullable = false)
	public Znacka getIdZnacka() {
		return idZnacka;
	}


	public void setIdZnacka(Znacka idZnacka) {
		this.idZnacka = idZnacka;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SLEVA_ID")
	public Sleva getIdSleva() {
		return idSleva;
	}


	public void setIdSleva(Sleva idSleva) {
		this.idSleva = idSleva;
	}

	@Column(name = "NAZEV", nullable = false, length = 50)
	public String getNazev() {
		return nazev;
	}


	public void setNazev(String nazev) {
		this.nazev = nazev;
	}

	@Column(name = "CENA", nullable = false, precision = 10, scale = 2)
	public int getCena() {
		return cena;
	}


	public void setCena(int cena) {
		this.cena = cena;
	}

	@Column(name = "VELIKOST_BOT", precision = 2, scale = 0)
	public Integer getVelikostBot() {
		return velikostBot;
	}


	public void setVelikostBot(Integer velikostBot) {
		this.velikostBot = velikostBot;
	}

	@Column(name = "VAHA", precision = 6, scale = 3)
	public Integer getVaha() {
		return vaha;
	}


	public void setVaha(Integer vaha) {
		this.vaha = vaha;
	}

	@Column(name = "VYSKA", precision = 8, scale = 2)
	public Integer getVyska() {
		return vyska;
	}


	public void setVyska(Integer vyska) {
		this.vyska = vyska;
	}

	@Column(name = "SIRKA", precision = 8, scale = 2)
	public Integer getSirka() {
		return sirka;
	}


	public void setSirka(Integer sirka) {
		this.sirka = sirka;
	}

	@Column(name = "HLOUBKA", precision = 8, scale = 2)
	public Integer getHloubka() {
		return hloubka;
	}


	public void setHloubka(Integer hloubka) {
		this.hloubka = hloubka;
	}

	@Column(name = "BARVA", precision = 1, scale = 0)
	public Integer getBarva() {
		return barva;
	}


	public void setBarva(Integer barva) {
		this.barva = barva;
	}

	@Column(name = "POHLAVI", precision = 1, scale = 0)
	public Integer getPohlavi() {
		return pohlavi;
	}


	public void setPohlavi(Integer pohlavi) {
		this.pohlavi = pohlavi;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idProdukt")
	public Set<Polozka> getPolozkas() {
		return polozkas;
	}


	public void setPolozkas(Set<Polozka> polozkas) {
		this.polozkas = polozkas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idProdukt")
	public Set<Skupina> getSkupinas() {
		return skupinas;
	}


	public void setSkupinas(Set<Skupina> skupinas) {
		this.skupinas = skupinas;
	}
}
