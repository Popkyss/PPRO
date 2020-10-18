package com.popkyss.sweetShop.setting;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class SirkaKriteriaStandard extends SirkaSloupcuStandard {
	private float kns = 0.18F;

	public SirkaKriteriaStandard(int pocetSloupcu, Document doc) {
		super(pocetSloupcu, doc);
		this.kns = 742.0F / getDocWidth() * 0.17F / 1.42F;
	}

	public void addCell(PdfPCell cell, int sloupec, PdfPTable table) {
		table.addCell(cell);

		Font font = cell.getPhrase().getFont();
		String text = cell.getPhrase().getContent();
		String[] radky = text.split("\n");
		for (int i = 0; i < radky.length; i++) {

			BaseFont bf = font.getCalculatedBaseFont(false);
			float sirka = bf.getWidthPoint(radky[i], font.getSize());

			sirka += bf.getAscentPoint(radky[i], font.getSize());

			sirka = PdfWriteUtils.getPhraseLenght(cell.getPhrase());
			sirka += sirka * this.kns;

			if (this.sirkaSloupcu[sloupec] < sirka) {
				this.sirkaSloupcu[sloupec] = sirka;
			}
			if (cell.isNoWrap() && this.sirkaSloupcuNoWrapped[sloupec] < sirka) {
				this.sirkaSloupcuNoWrapped[sloupec] = sirka;
			}
		}
	}

	public float getSirkaBunky(PdfPCell cell) {
		float sirka = super.getSirkaBunky(cell);
		sirka += sirka * this.kns;
		return sirka;
	}
}