package com.popkyss.sweetShop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * Skupina
 */
@Entity
@Table(name = "SKUPINA")
public class Skupina implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int idSkupina;
	
	private int idProdukt;

	private String nazev;

	
	public Skupina() {
	}

	@Id
	@Column(name = "SKUPINA_ID", unique = true, nullable = false, precision = 8, scale = 0)
	public int getIdSkupina() {
		return idSkupina;
	}


	public void setIdSkupina(int idSkupina) {
		this.idSkupina = idSkupina;
	}

	@Column(name = "PRODUKT_ID", nullable = false, precision = 8, scale = 0)
	public int getIdProdukt() {
		return idProdukt;
	}


	public void setIdProdukt(int idProdukt) {
		this.idProdukt = idProdukt;
	}

	@Column(name = "NAZEV", nullable = false, length = 30)
	public String getNazev() {
		return nazev;
	}


	public void setNazev(String nazev) {
		this.nazev = nazev;
	}
	
	
	
}
