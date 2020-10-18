package com.popkyss.sweetShop.setting;

import com.itextpdf.text.pdf.PdfPCell;
import java.util.ArrayList;
import java.util.List;

public class HlavickaTabulky extends AHlavickaDatoveTabulky {
	private List<BunkaTabulky> hlavickaTabulky = new ArrayList<BunkaTabulky>();

	private List<TrideniTabulky> trideniTabulky = new ArrayList<TrideniTabulky>();

	@Deprecated
	public HlavickaTabulky() {
	}

	public HlavickaTabulky(int pocetDatovychSloupcu) {
		super(pocetDatovychSloupcu);
		this.hlavickaTabulky = new ArrayList<BunkaTabulky>(pocetDatovychSloupcu);
	}

	public void addBunka(BunkaTabulky bunka, int poradiTrideneni, boolean ascending) {
		this.hlavickaTabulky.add(bunka);
		this.trideniTabulky.add(new TrideniTabulky(poradiTrideneni, ascending));
	}

	public void addBunka(BunkaTabulky bunka) {
		this.hlavickaTabulky.add(bunka);
		this.trideniTabulky.add(new TrideniTabulky(0, false));
	}

	public List<BunkaTabulky> getHlavickaTabulky() {
		return this.hlavickaTabulky;
	}

	@Deprecated
	public int size() {
		return this.hlavickaTabulky.size();
	}

	public PdfPCell getCell(int index) {
		TrideniTabulky tt = this.trideniTabulky.get(index);
		BunkaTabulky bt = this.hlavickaTabulky.get(index);
		if (tt.hasTrideni()) {
			return getTridenaBunka(bt, tt.getPoradiTrideni(), tt.isAscending());
		}
		return bt.getBunka();
	}

	public int getPocetSloupcu() {
		if (this.pocetDatovychSloupcu == null) {
			return this.hlavickaTabulky.size();
		}
		return this.pocetDatovychSloupcu.intValue();
	}

	public PdfPCell getTridenaBunka(BunkaTabulky bunka, int poradiTrideni, boolean ascending) {
		return super.getTridenaBunka(bunka, poradiTrideni, ascending);
	}
}