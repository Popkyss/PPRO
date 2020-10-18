package com.popkyss.sweetShop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Role
 */
@Entity
@Table(name = "ROLE")
public class Role implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int idRole;
	
	private int idZamestnanec;

	private String nazev;

	
	public Role() {
	}

	@Id
	@Column(name = "ROLE_ID", unique = true, nullable = false, precision = 8, scale = 0)
	public int getIdRole() {
		return idRole;
	}


	public void setIdRole(int idRole) {
		this.idRole = idRole;
	}

	@Column(name = "ZAMESTNANEC_ID", nullable = false, precision = 8, scale = 0)
	public int getIdZamestnanec() {
		return idZamestnanec;
	}


	public void setIdZamestnanec(int idZamestnanec) {
		this.idZamestnanec = idZamestnanec;
	}

	@Column(name = "NAZEV", nullable = false, length = 30)
	public String getNazev() {
		return nazev;
	}


	public void setNazev(String nazev) {
		this.nazev = nazev;
	}
	
	
}
