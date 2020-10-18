package com.popkyss.sweetShop.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Kosik
 */
@Entity
@Table(name = "KOSIK")
public class Kosik implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int idKosik;
	
	private Zakaznik idZakaznik;	
	
	private Set<Polozka> polozkas = new HashSet<Polozka>(0);
	
	
	public Kosik() {
	}


	@Id
	@Column(name = "KOSIK_ID", unique = true, nullable = false, precision = 8, scale = 0)
	public int getIdKosik() {
		return idKosik;
	}

	public void setIdKosik(int idKosik) {
		this.idKosik = idKosik;
	}

	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ZAKAZNIK_ID", nullable = true)
	public Zakaznik getIdZakaznik() {
		return idZakaznik;
	}

	public void setIdZakaznik(Zakaznik idZakaznik) {
		this.idZakaznik = idZakaznik;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idKosik")
	public Set<Polozka> getPolozkas() {
		return polozkas;
	}

	public void setPolozkas(Set<Polozka> polozkas) {
		this.polozkas = polozkas;
	}

}
