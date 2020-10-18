package com.popkyss.sweetShop.model;

import java.io.Serializable;

public class Uzivatel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String jmeno;
	private String username;
	private String telefon;
	private String email;
	private String heslo;
	private Integer idZamestnanec;
	private Integer idZakaznik;

	public Uzivatel() {
	}

	public Uzivatel(String jmeno, String username, String telefon, String email) {
		super();
		this.jmeno = jmeno;
		this.username = username;
		this.telefon = telefon;
		this.email = email;
	}

	public String getJmeno() {
		return jmeno;
	}

	public void setJmeno(String jmeno) {
		this.jmeno = jmeno;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIdZamestnanec() {
		return idZamestnanec;
	}

	public void setIdZamestnanec(Integer idZamestnanec) {
		this.idZamestnanec = idZamestnanec;
	}

	public Integer getIdZakaznik() {
		return idZakaznik;
	}

	public void setIdZakaznik(Integer idZakaznik) {
		this.idZakaznik = idZakaznik;
	}

	public String getHeslo() {
		return heslo;
	}

	public void setHeslo(String heslo) {
		this.heslo = heslo;
	}

}
