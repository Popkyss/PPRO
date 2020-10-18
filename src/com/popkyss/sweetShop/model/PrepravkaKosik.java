package com.popkyss.sweetShop.model;

import java.io.Serializable;

public class PrepravkaKosik implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

	private Integer idKosik;
	private Integer idZakaznik;
	private int pocet;


	public PrepravkaKosik() {
	}


	public PrepravkaKosik(Integer idKosik, Integer idZakaznik, int pocet) {
		super();
		this.idKosik = idKosik;
		this.idZakaznik = idZakaznik;
		this.pocet = pocet;
	}




	public Integer getIdKosik() {
		return idKosik;
	}


	public void setIdKosik(Integer idKosik) {
		this.idKosik = idKosik;
	}


	public Integer getIdZakaznik() {
		return idZakaznik;
	}


	public void setIdZakaznik(Integer idZakaznik) {
		this.idZakaznik = idZakaznik;
	}


	public int getPocet() {
		return pocet;
	}


	public void setPocet(int pocet) {
		this.pocet = pocet;
	}

}
