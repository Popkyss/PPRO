package com.popkyss.sweetShop.model;

import java.io.Serializable;

public class PrepravkaProdukt implements Serializable {
	
	private static final long serialVersionUID = 1L;
	


	private Integer idProdukt;
	private String nazev; 
	private int cena;


	public PrepravkaProdukt() {
	}


	public PrepravkaProdukt(Integer idProdukt, String nazev, int cena) {
		super();
		this.idProdukt = idProdukt;
		this.nazev = nazev;
		this.cena = cena;
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


	public int getCena() {
		return cena;
	}


	public void setCena(int cena) {
		this.cena = cena;
	}

}
