package com.popkyss.sweetShop.model;

import java.io.Serializable;

public class PrepravkaUzivatel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

	private Integer idUzivatel;
	private String email;
	private String username;
	private String heslo;
	private String heslo2;
	private String jmeno;
	private String prijmeni;
	private String telefon;
	
	private Integer idAdresa;
	private String mesto;
	private String ulice;
	private Integer cp;
	private Integer cp2;
	private String psc;

	public PrepravkaUzivatel() {
	}


	public PrepravkaUzivatel(String email, String username, String heslo, String heslo2, String jmeno, String prijmeni,
			String telefon, Integer idAdresa, String mesto, String ulice, Integer cp, Integer cp2, String psc) {
		super();
		this.email = email;
		this.username = username;
		this.heslo = heslo;
		this.heslo2 = heslo2;
		this.jmeno = jmeno;
		this.prijmeni = prijmeni;
		this.telefon = telefon;
		this.idAdresa = idAdresa;
		this.mesto = mesto;
		this.ulice = ulice;
		this.cp = cp;
		this.cp2 = cp2;
		this.psc = psc;
	}




	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHeslo() {
		return heslo;
	}

	public void setHeslo(String heslo) {
		this.heslo = heslo;
	}

	public String getHeslo2() {
		return heslo2;
	}

	public void setHeslo2(String heslo2) {
		this.heslo2 = heslo2;
	}

	public String getJmeno() {
		return jmeno;
	}

	public void setJmeno(String jmeno) {
		this.jmeno = jmeno;
	}

	public String getPrijmeni() {
		return prijmeni;
	}

	public void setPrijmeni(String prijmeni) {
		this.prijmeni = prijmeni;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public String getUlice() {
		return ulice;
	}

	public void setUlice(String ulice) {
		this.ulice = ulice;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public Integer getCp() {
		return cp;
	}


	public void setCp(Integer cp) {
		this.cp = cp;
	}


	public String getPsc() {
		return psc;
	}


	public void setPsc(String psc) {
		this.psc = psc;
	}



	public Integer getIdAdresa() {
		return idAdresa;
	}



	public void setIdAdresa(Integer idAdresa) {
		this.idAdresa = idAdresa;
	}



	public Integer getCp2() {
		return cp2;
	}



	public void setCp2(Integer cp2) {
		this.cp2 = cp2;
	}


	public Integer getIdUzivatel() {
		return idUzivatel;
	}


	public void setIdUzivatel(Integer idUzivatel) {
		this.idUzivatel = idUzivatel;
	}

}
