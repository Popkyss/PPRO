package com.popkyss.sweetShop.setting;

public class Kriteria {
	private String nazevKriteria = "";

	private String typKriteria = "";

	private String[] kriteria;

	private String oddelovacKriteria = ", ";

	public static final String ODRADKOVANI = "\n";

	private boolean indexed = false;

	public Kriteria(String nazevKriteria, String typKriteria, String... kriteria) {
		this.nazevKriteria = nazevKriteria;
		this.typKriteria = typKriteria;
		this.kriteria = kriteria;
	}

	public Kriteria(String nazevKriteria, String typKriteria, boolean indexed, String... kriteria) {
		this.nazevKriteria = nazevKriteria;
		this.typKriteria = typKriteria;
		this.kriteria = kriteria;
		this.indexed = indexed;
	}

	public String getNazevKriteria() {
		return this.nazevKriteria;
	}

	public String getTypKriteria() {
		return this.typKriteria;
	}

	public String[] getKriteria() {
		return this.kriteria;
	}

	public String getOddelovacKriteria() {
		return this.oddelovacKriteria;
	}

	public void setOddelovacKriteria(String oddelovac) {
		this.oddelovacKriteria = oddelovac;
	}

	public boolean isIndexed() {
		return this.indexed;
	}

	public void setIndexed(boolean indexed) {
		this.indexed = indexed;
	}
}