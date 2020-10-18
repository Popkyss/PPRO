package com.popkyss.sweetShop.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Data
 */
@Entity
@Table(name = "DATA")
public class Data implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int idData;

	private String nazev;

	private int format;
	
	private String popis;
	
	private byte[] data;
		

	private Set<Objednavka> objednavkas = new HashSet<Objednavka>(0);
	
	
	public Data() {
	}


	@Id
	@Column(name = "DATA_ID", unique = true, nullable = false, precision = 8, scale = 0)
	public int getIdData() {
		return idData;
	}


	public void setIdData(int idData) {
		this.idData = idData;
	}

	@Column(name = "NAZEV", nullable = false, length = 20)
	public String getNazev() {
		return nazev;
	}


	public void setNazev(String nazev) {
		this.nazev = nazev;
	}

	@Column(name = "FORMAT", nullable = false, precision = 1, scale = 0)
	public int getFormat() {
		return format;
	}


	public void setFormat(int format) {
		this.format = format;
	}

	@Column(name = "POPIS", length = 100)
	public String getPopis() {
		return popis;
	}


	public void setPopis(String popis) {
		this.popis = popis;
	}

	@Column(name = "DATA", nullable = false)
	public byte[] getData() {
		return data;
	}


	public void setData(byte[] data) {
		this.data = data;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "idData")
	public Set<Objednavka> getObjednavkas() {
		return this.objednavkas;
	}


	public void setObjednavkas(Set<Objednavka> objednavkas) {
		this.objednavkas = objednavkas;
	}
}
