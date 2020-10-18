package com.popkyss.sweetShop.setting;

import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;

public abstract class SirkaSloupcuAlg implements ISirkaSloupcu {
	protected Logger log = Logger.getLogger(getClass());

	protected float[] sirkaSloupcu;

	protected float[] sirkaSloupcuNoWrapped;

	protected float sirkaTableAll = 0.0F;

	protected float sirkaTableNoWrapped = 0.0F;

	protected Document doc;

	public SirkaSloupcuAlg(int pocetSloupcu, Document doc) {
		this.sirkaSloupcu = new float[pocetSloupcu];
		this.sirkaSloupcuNoWrapped = new float[pocetSloupcu];
		this.doc = doc;
	}

	public static ISirkaSloupcu sirkaSloupcuFactory(SirkaSloupcuSwitcher switcher, int pocetSloupcu, Document doc) {
		if (switcher == SirkaSloupcuSwitcher.KRITERIA_STANDARD) {
			return new SirkaKriteriaStandard(pocetSloupcu, doc);
		}
		return new SirkaSloupcuStandard(pocetSloupcu, doc);
	}

	protected float getDocWidth() {
		return this.doc.getPageSize().getWidth() - this.doc.leftMargin() + this.doc.rightMargin();
	}

	public float getTotalTableWidth() {
		return this.sirkaTableAll;
	}

	public float[] getSirkySloupcu() {
		return this.sirkaSloupcu;
	}

	public float getSirkaBunky(PdfPCell cell) {
		float sirka = 0.0F;

		Font font = cell.getPhrase().getFont();
		String text = cell.getPhrase().getContent();
		BaseFont bf = font.getCalculatedBaseFont(false);
		String[] radky = text.split("\n");
		for (int i = 0; i < radky.length; i++) {
			float temp = bf.getWidthPoint(radky[i], font.getSize());
			temp += bf.getAscentPoint(radky[i], font.getSize());
			if (temp > sirka)
				sirka = temp;
		}
		return sirka;
	}
}