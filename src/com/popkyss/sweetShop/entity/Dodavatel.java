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
 * Dodavatel
 */
@Entity
@Table(name = "DODAVATEL")
public class Dodavatel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private int idDodavatel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADRESA_ID", nullable = false)
	private Adresa idAdresa;

	private String email;
	
	private String telefon;
	
	private String nazev;
	
	private String web;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idDodavatel")
	private Set<Dodavka> dodavkas = new HashSet<Dodavka>(0);
	
	
	public Dodavatel() {
	}



	@Column(name = "DODAVATEL_ID", unique = true, nullable = false, precision = 8, scale = 0)
	public int getIdDodavatel() {
		return idDodavatel;
	}


	public void setIdDodavatel(int idDodavatel) {
		this.idDodavatel = idDodavatel;
	}
	
	
	
	public Adresa getAdresaId() {
		return idAdresa;
	}


	public void setAdresaId(Adresa idAdresa) {
		this.idAdresa = idAdresa;
	}

	@Column(name = "EMAIL", nullable = false, length = 30)
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "TELEFON", length = 13)
	public String getTelefon() {
		return telefon;
	}


	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	@Column(name = "NAZEV", nullable = false, length = 50)
	public String getNazev() {
		return nazev;
	}


	public void setNazev(String nazev) {
		this.nazev = nazev;
	}

	@Column(name = "WEB", nullable = false, length = 100)
	public String getWeb() {
		return web;
	}


	public void setWeb(String web) {
		this.web = web;
	}

	
	public Set<Dodavka> getDodavkas() {
		return dodavkas;
	}


	public void setDodavkas(Set<Dodavka> dodavkas) {
		this.dodavkas = dodavkas;
	}
}
