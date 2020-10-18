package com.popkyss.sweetShop.setting;

import java.util.ArrayList;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class SirkaSloupcuStandard extends SirkaSloupcuAlg {
	public SirkaSloupcuStandard(int pocetSloupcu, Document doc) {
		super(pocetSloupcu, doc);
	}

	public void addCell(PdfPCell cell, int sloupec, PdfPTable table) {
		table.addCell(cell);

		String text = cell.getPhrase().getContent();
		if (text != null && !text.equalsIgnoreCase("")) {
			Font font = ((Chunk) cell.getPhrase().getChunks().get(0)).getFont();

			String[] radky = text.split("\n");
			for (int i = 0; i < radky.length; i++) {

				BaseFont bf = font.getCalculatedBaseFont(false);
				float sirka = bf.getWidthPoint(radky[i], font.getSize());

				sirka += bf.getAscentPoint(radky[i], font.getSize());

				if (this.sirkaSloupcu[sloupec] < sirka) {
					this.sirkaSloupcu[sloupec] = sirka;
				}
				if (cell.isNoWrap() && this.sirkaSloupcuNoWrapped[sloupec] < sirka) {
					this.sirkaSloupcuNoWrapped[sloupec] = sirka;
				}
			}
		}
	}

	public void setTableWidths(PdfPTable table) throws DocumentException {
		float docWidth = getDocWidth();
		this.log.debug("Sirka dokumentu je:" + docWidth);
		if (this.log.isDebugEnabled())
			this.log.debug("Nastavuje se sirka sloupcu tabulky");
		float[] konecnaSirkaSloupcu = new float[this.sirkaSloupcu.length];
		for (int i = 0; i < this.sirkaSloupcu.length; i++) {
			this.sirkaTableAll += this.sirkaSloupcu[i];
			this.sirkaTableNoWrapped += this.sirkaSloupcuNoWrapped[i];
		}
		float temp = this.sirkaTableAll / docWidth;
		if (temp > 1.0F) {
			temp = this.sirkaTableNoWrapped / docWidth;
			if (temp > 1.0F) {
				table.setWidthPercentage(this.sirkaSloupcu, this.doc.getPageSize());
				this.log.warn(
						"Sirka tabulky je vetsi nez sirka stranky - hrozi preteceni tabulky. " + temp + "wrapped");
			} else {
				if (this.log.isDebugEnabled())
					this.log.debug("Prepocitava se sirku tabulky");
				temp = 1.0F - temp;
				float size = 0.0F;

				for (int j = 0; j < this.sirkaSloupcu.length; j++) {
					if (this.sirkaSloupcu[j] > this.sirkaSloupcuNoWrapped[j]) {
						size += this.sirkaSloupcu[j];
					} else {
						konecnaSirkaSloupcu[j] = this.sirkaSloupcu[j];
					}
				}
				float realSize = temp * docWidth;

				ArrayList<Integer> list = new ArrayList<Integer>();
				float size2 = 0.0F;
				float sizeX = 0.0F;
				int k;
				for (k = 0; k < this.sirkaSloupcu.length; k++) {
					if (this.sirkaSloupcu[k] > this.sirkaSloupcuNoWrapped[k]) {
						float sirkaSloupce = this.sirkaSloupcu[k] / size * realSize + this.sirkaSloupcuNoWrapped[k];
						if (this.sirkaSloupcu[k] <= sirkaSloupce) {
							konecnaSirkaSloupcu[k] = this.sirkaSloupcu[k];
							this.sirkaSloupcuNoWrapped[k] = this.sirkaSloupcu[k];
							sizeX += sirkaSloupce - this.sirkaSloupcu[k];
						} else {
							list.add(Integer.valueOf(k));
							size2 += this.sirkaSloupcu[k];
						}
					}
				}
				this.sirkaTableNoWrapped = 0.0F;
				for (k = 0; k < this.sirkaSloupcu.length; k++) {
					this.sirkaTableNoWrapped += this.sirkaSloupcuNoWrapped[k];
				}
				temp = this.sirkaTableNoWrapped / docWidth;
				temp = 1.0F - temp;
				realSize = temp * docWidth;
				if (this.log.isDebugEnabled())
					this.log.debug("sirka nowraped sloupcu:" + this.sirkaTableNoWrapped);
				size2 += sizeX;
				sizeX /= list.size();
				for (k = 0; k < list.size(); k++) {
					Integer alfa = list.get(k);
					konecnaSirkaSloupcu[alfa.intValue()] = (this.sirkaSloupcu[alfa.intValue()] + sizeX) / size2
							* realSize + this.sirkaSloupcuNoWrapped[alfa.intValue()];
				}
				this.sirkaTableAll = 0.0F;
				for (k = 0; k < this.sirkaSloupcu.length; k++) {
					this.sirkaTableAll += konecnaSirkaSloupcu[k];
				}
				this.sirkaSloupcu = konecnaSirkaSloupcu;
				table.setWidthPercentage(this.sirkaSloupcu, this.doc.getPageSize());
			}
		} else {
			table.setWidthPercentage(this.sirkaSloupcu, this.doc.getPageSize());
		}
		if (this.log.isDebugEnabled())
			this.log.debug("konecna sirka tabulky je +" + this.sirkaTableAll);
	}
}
