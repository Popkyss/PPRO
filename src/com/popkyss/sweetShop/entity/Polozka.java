package com.popkyss.sweetShop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Polozka
 */
@Entity
@Table(name = "POLOZKA")
public class Polozka implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int idPolozka;
	
	private Kosik idKosik;

	private Produkt idProdukt;

	private int pocet;
	
	private Integer cena;
	
	
	public Polozka() {
	}


	@Id
	@Column(name = "POLOZKA_ID", unique = true, nullable = false, precision = 8, scale = 0)
	public int getIdPolozka() {
		return idPolozka;
	}


	public void setIdPolozka(int idPolozka) {
		this.idPolozka = idPolozka;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUKT_ID", nullable = false)
	public Produkt getIdProdukt() {
		return idProdukt;
	}


	public void setIdProdukt(Produkt idProdukt) {
		this.idProdukt = idProdukt;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "KOSIK_ID", nullable = false)
	public Kosik getIdKosik() {
		return idKosik;
	}

	public void setIdKosik(Kosik idKosik) {
		this.idKosik = idKosik;
	}

	@Column(name = "POCET", nullable = false, precision = 4, scale = 0)
	public int getPocet() {
		return pocet;
	}


	public void setPocet(int pocet) {
		this.pocet = pocet;
	}

	@Column(name = "CENA", nullable = true, precision = 10, scale = 2)
	public Integer getCena() {
		return cena;
	}


	public void setCena(Integer cena) {
		this.cena = cena;
	}
}
