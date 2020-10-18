package com.popkyss.sweetShop.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Oblibene
 */
@Entity
@Table(name = "OBLIBENE")
public class Oblibene implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int idOblibene;
	
    private int idProdukt;

    private int idZakaznik;
	
	public Oblibene() {
	}

	@Id
	@Column(name = "OBLIBENE_ID", unique = true, nullable = false, precision = 8, scale = 0)
	public int getIdOblibene() {
		return idOblibene;
	}

	public void setIdOblibene(int idOblibene) {
		this.idOblibene = idOblibene;
	}
	
	@Column(name = "PRODUKT_ID", nullable = false, precision = 8, scale = 0)
	public int getIdProdukt() {
		return idProdukt;
	}

	public void setIdProdukt(int idProdukt) {
		this.idProdukt = idProdukt;
	}

	@Column(name = "ZAKAZNIK_ID", nullable = false, precision = 8, scale = 0)
	public int getIdZakaznik() {
		return idZakaznik;
	}

	public void setIdZakaznik(int idZakaznik) {
		this.idZakaznik = idZakaznik;
	}
	
	
}
