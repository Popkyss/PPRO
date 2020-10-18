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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Zakaznik
 */
@Entity
@Table(name = "ZAKAZNIK")
public class Zakaznik implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int idZakaznik;
	
	private Adresa idAdresa;
	
	private Kosik idKosik;

	private String username;

	private String jmeno;
	
	private String prijmeni;
	
	private String email;
	
	private String telefon;
	
	private Date narozeni;
	
	private Date registrace;
		

	private Set<Objednavka> objednavkas = new HashSet<Objednavka>(0);
	
	private Set<Oblibene> oblibenes = new HashSet<Oblibene>(0);
	
	
	public Zakaznik() {
	}


	@Id
	@Column(name = "ZAKAZNIK_ID", unique = true, nullable = false, precision = 8, scale = 0)
	public int getIdZakaznik() {
		return idZakaznik;
	}
	
	
	public void setIdZakaznik(int idZazaknik) {
		this.idZakaznik = idZazaknik;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADRESA_ID", nullable = false)
	public Adresa getIdAdresa() {
		return idAdresa;
	}


	public void setIdAdresa(Adresa idAdresa) {
		this.idAdresa = idAdresa;
	}
	

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "KOSIK_ID", nullable = true)
	public Kosik getIdKosik() {
		return idKosik;
	}


	public void setIdKosik(Kosik idKosik) {
		this.idKosik = idKosik;
	}
	
	
	@Column(name = "USERNAME", nullable = false, length = 30)
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}
	

	@Column(name = "JMENO", nullable = false, length = 30)
	public String getJmeno() {
		return jmeno;
	}


	public void setJmeno(String jmeno) {
		this.jmeno = jmeno;
	}

	@Column(name = "PRIJMENI", nullable = false, length = 50)
	public String getPrijmeni() {
		return prijmeni;
	}


	public void setPrijmeni(String prijmeni) {
		this.prijmeni = prijmeni;
	}

	@Column(name = "EMAIL", nullable = false, length = 100)
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "TELEFON", nullable = true, length = 13)
	public String getTelefon() {
		return telefon;
	}


	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	
	
	@Column(name = "NAROZENI", nullable = true, length = 7)
	public Date getNarozeni() {
		return narozeni;
	}


	public void setNarozeni(Date narozeni) {
		this.narozeni = narozeni;
	}

	@Column(name = "REGISTRACE", nullable = true, length = 7)
	public Date getRegistrace() {
		return registrace;
	}


	public void setRegistrace(Date registrace) {
		this.registrace = registrace;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idZakaznik")
	public Set<Objednavka> getObjednavkas() {
		return objednavkas;
	}

	public void setObjednavkas(Set<Objednavka> objednavkas) {
		this.objednavkas = objednavkas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idZakaznik")
	public Set<Oblibene> getOblibenes() {
		return oblibenes;
	}


	public void setOblibenes(Set<Oblibene> oblibenes) {
		this.oblibenes = oblibenes;
	}

	
	
}
