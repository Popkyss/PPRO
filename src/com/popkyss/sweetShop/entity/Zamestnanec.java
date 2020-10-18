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
 * Zamestnanec
 */
@Entity
@Table(name = "ZAMESTNANEC")
public class Zamestnanec implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private int idZamestnanec;
	
	private Adresa idAdresa;

	private String username;

	private String jmeno;
	
	private String prijmeni;
	
	private String email;
	
	private String telefon;
		
	private Set<Role> roles = new HashSet<Role>(0);
	
	
	public Zamestnanec() {
	}


	
	@Id
	@Column(name = "ZAMESTNANEC_ID", unique = true, nullable = false, precision = 8, scale = 0)
	public int getIdZamestnanec() {
		return idZamestnanec;
	}
	
	
	public void setIdZamestnanec(int idZamestnanec) {
		this.idZamestnanec = idZamestnanec;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADRESA_ID", nullable = false)
	public Adresa getIdAdresa() {
		return idAdresa;
	}


	public void setIdAdresa(Adresa idAdresa) {
		this.idAdresa = idAdresa;
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

	@Column(name = "EMAIL", nullable = false, length = 50)
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "TELEFON", nullable = false, length = 13)
	public String getTelefon() {
		return telefon;
	}


	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idZamestnanec")
	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
