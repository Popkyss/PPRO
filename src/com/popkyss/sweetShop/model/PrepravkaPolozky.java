package com.popkyss.sweetShop.model;

import java.io.Serializable;

public class PrepravkaPolozky implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

	private Integer idKosik;
	private Integer idProdukt;
	private Integer idPolozka;
	private String nazev; 
	private int pocet;
	private int cena;


	public PrepravkaPolozky() {
	}

	public PrepravkaPolozky(Integer idKosik, Integer idProdukt, String nazev, int pocet, int cena) {
		super();
		this.idKosik = idKosik;
		this.idProdukt = idProdukt;
		this.nazev = nazev;
		this.pocet = pocet;
		this.cena = cena;
	}



	public Integer getIdKosik() {
		return idKosik;
	}


	public void setIdKosik(Integer idKosik) {
		this.idKosik = idKosik;
	}


	public Integer getIdProdukt() {
		return idProdukt;
	}


	public void setIdProdukt(Integer idProdukt) {
		this.idProdukt = idProdukt;
	}


	public String getNazev() {
		return nazev;
	}


	public void setNazev(String nazev) {
		this.nazev = nazev;
	}


	public int getPocet() {
		return pocet;
	}


	public void setPocet(int pocet) {
		this.pocet = pocet;
	}


	public int getCena() {
		return cena;
	}


	public void setCena(int cena) {
		this.cena = cena;
	}

	public Integer getIdPolozka() {
		return idPolozka;
	}

	public void setIdPolozka(Integer idPolozka) {
		this.idPolozka = idPolozka;
	}
}
