package com.popkyss.sweetShop.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Sleva
 */
@Entity
@Table(name = "SLEVA")
public class Sleva implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int idSleva;

	private String nazev;

	private int velikost;
	
	private Date platnostOd;
	
	private Date platnostDo;
			
	private Set<Produkt> produkts = new HashSet<Produkt>(0);
	
	
	public Sleva() {
	}


	@Id
	@Column(name = "SLEVA_ID", unique = true, nullable = false, precision = 8, scale = 0)
	public int getIdSleva() {
		return idSleva;
	}


	public void setIdSleva(int idSleva) {
		this.idSleva = idSleva;
	}

	@Column(name = "NAZEV", nullable = false, length = 20)
	public String getNazev() {
		return nazev;
	}


	public void setNazev(String nazev) {
		this.nazev = nazev;
	}

	@Column(name = "VELIKOST", nullable = false, precision = 3, scale = 0)
	public int getVelikost() {
		return velikost;
	}


	public void setVelikost(int velikost) {
		this.velikost = velikost;
	}

	@Column(name = "PLATNOST_OD", nullable = false, length = 7)
	public Date getPlatnostOd() {
		return platnostOd;
	}


	public void setPlatnostOd(Date platnostOd) {
		this.platnostOd = platnostOd;
	}

	@Column(name = "PLATNOST_DO", nullable = false, length = 7)
	public Date getPlatnostDo() {
		return platnostDo;
	}


	public void setPlatnostDo(Date platnostDo) {
		this.platnostDo = platnostDo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idSleva")
	public Set<Produkt> getProdukts() {
		return produkts;
	}


	public void setProdukts(Set<Produkt> produkts) {
		this.produkts = produkts;
	}
}
