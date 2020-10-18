package com.popkyss.sweetShop.setting;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class SlozenaHlavickaTabulky extends AHlavickaDatoveTabulky {
	private PdfPTable table = null;

	public SlozenaHlavickaTabulky(int pocetDatovychSloupcu) {
		super(pocetDatovychSloupcu);
		this.table = new PdfPTable(pocetDatovychSloupcu);
	}

	public void addBunka(PdfPTable t, int colspan) {
		PdfPCell cell = new PdfPCell(t);
		cell.setColspan(colspan);
		this.table.addCell(cell);
	}

	public void addBunka(BunkaTabulky bt, int colspan) {
		bt.getBunka().setColspan(colspan);
		this.table.addCell(bt.getBunka());
	}

	public void addBunka(PdfPTable t) {
		this.table.addCell(t);
	}

	public void addBunka(BunkaTabulky bunka, int poradiTrideni, boolean ascending) {
		this.table.addCell(getTridenaBunka(bunka, poradiTrideni, ascending));
	}

	public void addBunka(BunkaTabulky bt) {
		this.table.addCell(bt.getBunka());
	}

	public PdfPTable getTable() {
		return this.table;
	}

	public PdfPCell getTridenaBunka(BunkaTabulky bunka, int poradiTrideni, boolean ascending) {
		return super.getTridenaBunka(bunka, poradiTrideni, ascending);
	}
}