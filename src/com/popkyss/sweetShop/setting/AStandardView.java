package com.popkyss.sweetShop.setting;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public abstract class AStandardView extends PdfPageEventHelper {
	protected Logger log = Logger.getLogger(getClass());

	protected PdfWriter writer;

	protected ByteArrayOutputStream buffer;

	protected Document doc;

	protected PdfPTable zahlavi;

	protected PdfPTable zapati;

	protected PdfTemplate tpl;

	protected AStandardSestava sestava;

	public AStandardView(AStandardSestava sestava) {
		this.sestava = sestava;
	}

	public void onOpenDocument(PdfWriter writer, Document document) {
		try {
			pridejZahlavi();

			this.tpl = writer.getDirectContent().createTemplate(100.0F, 100.0F);
			this.tpl.setBoundingBox(new Rectangle(-20.0F, -20.0F, 100.0F, 100.0F));

			pridejZapati();

		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	public void onEndPage(PdfWriter writer, Document document) {
		PdfContentByte cb = writer.getDirectContent();
		cb.saveState();

		this.zahlavi.setTotalWidth(document.right() - document.left());

		this.zahlavi.writeSelectedRows(0, -1, document.left(), document.top() + 25.0F, cb);
		this.zapati.setTotalWidth(document.right() - document.left());
		this.zapati.writeSelectedRows(0, -1, document.left(), document.bottom() - 10.0F, cb);

		String text = String.valueOf(writer.getPageNumber()) + "/";
		String pomText = String.valueOf(text) + "00";
		float textSize = PDFFont.ZAHLAVI.getBaseFont().getWidthPoint(pomText, 9.0F);
		float textBase = document.top() + 15.0F;

		cb.beginText();
		cb.setFontAndSize(PDFFont.ZAHLAVI.getBaseFont(), 9.0F);

		float adjust = PDFFont.ZAHLAVI.getBaseFont().getWidthPoint("0", 9.0F);
		cb.setTextMatrix(document.right() - textSize - adjust, textBase);
		cb.showText(text);
		cb.endText();
		cb.addTemplate(this.tpl, document.right() - adjust, textBase);
		cb.restoreState();
	}

	public void onCloseDocument(PdfWriter writer, Document document) {
		this.tpl.beginText();
		this.tpl.setFontAndSize(PDFFont.ZAHLAVI.getBaseFont(), 9.0F);
		float textSize = PDFFont.ZAHLAVI.getBaseFont().getWidthPoint("00", 9.0F);
		this.tpl.setTextMatrix(-textSize, 0.0F);
		int pocetetStran = writer.getPageNumber() - 1;
		this.tpl.showText(pocetetStran + "");
		this.tpl.endText();
	}

	private void pridejZahlavi() {
		ZahlaviZapati zData = this.sestava.getZahlavi();
		if (zData == null) {
			this.zahlavi = new PdfPTable(1);
			addTypBunky(this.zahlavi, ZahlaviZapatiTyp.NAZEV_APLIKACE);
		} else {

			this.zahlavi = new PdfPTable(zData.getPocetSloupcu() + 1);
			int i = 0;
			for (BunkaTabulky bunka : zData.getBunky()) {
				if (zData.getTypy().containsKey(Integer.valueOf(i))) {
					if (i == 0) {
						this.zahlavi.getDefaultCell().setHorizontalAlignment(0);
					} else {
						this.zahlavi.getDefaultCell().setHorizontalAlignment(1);
					}
					addTypBunky(this.zahlavi, (ZahlaviZapatiTyp) zData.getTypy().get(Integer.valueOf(i)));
				} else {
					PdfPCell cell = bunka.getBunka();
					cell.setBorderWidth(0.0F);
					cell.setBorderColorBottom(BaseColor.BLACK);
					cell.setBorderWidthBottom(0.3F);
					if (this.sestava.isIndexed())
						cell = PdfWriteUtils.getPdfpCellwithIndex(cell);
					this.zahlavi.addCell(cell);
				}
				i++;
			}

			this.zahlavi.addCell(" ");
		}
	}

	private void pridejZapati() {
		ZahlaviZapati zData = this.sestava.getZapati();
		if (zData == null) {
			this.zapati = new PdfPTable(2);
			addTypBunky(this.zapati, ZahlaviZapatiTyp.ZPRACOVANO);
			this.zapati.getDefaultCell().setHorizontalAlignment(2);
			addTypBunky(this.zapati, ZahlaviZapatiTyp.ZPRACOVAL);
		} else {
			this.zapati = new PdfPTable(zData.getPocetSloupcu());
			int i = 0;
			for (BunkaTabulky bunka : zData.getBunky()) {
				if (zData.getTypy().containsKey(Integer.valueOf(i))) {
					if (i == zData.getPocetSloupcu() - 1) {
						this.zapati.getDefaultCell().setHorizontalAlignment(2);
					} else if (i == 0) {
						this.zapati.getDefaultCell().setHorizontalAlignment(0);
					} else {
						this.zapati.getDefaultCell().setHorizontalAlignment(1);
					}
					addTypBunky(this.zapati, (ZahlaviZapatiTyp) zData.getTypy().get(Integer.valueOf(i)));
				} else {
					PdfPCell cell = bunka.getBunka();
					if (i == zData.getPocetSloupcu() - 1) {
						cell.setHorizontalAlignment(2);
					} else if (i == 0) {
						cell.setHorizontalAlignment(0);
					} else {
						cell.setHorizontalAlignment(1);
					}
					cell.setBorderWidth(0.0F);
					cell.setBorderColorBottom(BaseColor.BLACK);
					cell.setBorderWidthBottom(0.3F);
					if (this.sestava.isIndexed())
						cell = PdfWriteUtils.getPdfpCellwithIndex(cell);
					this.zapati.addCell(cell);
				}
				i++;
			}
		}
	}

	private void addTypBunky(PdfPTable table, ZahlaviZapatiTyp typ) {
		table.getDefaultCell().setBorderWidth(0.0F);
		table.getDefaultCell().setBorderColorBottom(BaseColor.BLACK);
		table.getDefaultCell().setBorderWidthBottom(0.3F);
		Phrase p = null;
		Chunk ck = null;
		if (!this.sestava.isIndexed()) {
			switch (typ) {
			case NAZEV_APLIKACE:
				p = new Phrase();
				ck = new Chunk(this.sestava.getPopisky().getNazevAplikace(), PDFFont.ZAHLAVI);
				p.add((Element) ck);
				table.addCell(p);
				break;
			case ZPRACOVAL:
				p = new Phrase();
				ck = new Chunk(String.valueOf(this.sestava.getPopisky().getNazevZpracoval()) + " ", PDFFont.BOLD);
				p.add((Element) ck);
				ck = new Chunk(this.sestava.getPopisky().getZpracoval(), PDFFont.NORMAL);
				p.add((Element) ck);
				table.addCell(p);
				break;
			case ZPRACOVANO:
				p = new Phrase();
				ck = new Chunk(String.valueOf(this.sestava.getPopisky().getNazevDatumVyberu()) + " ", PDFFont.BOLD);
				p.add((Element) ck);
				ck = new Chunk(this.sestava.getPopisky().getDatumVyberu(), PDFFont.NORMAL);
				p.add((Element) ck);
				table.getDefaultCell().setHorizontalAlignment(0);
				table.addCell(p);
				break;
			}

		} else {
			switch (typ) {
			case NAZEV_APLIKACE:
				p = PdfWriteUtils.getPharseWithIndex(this.sestava.getPopisky().getNazevAplikace(), PDFFont.ZAHLAVI);
				table.addCell(p);
				break;
			case ZPRACOVAL:
				p = new Phrase();
				ck = new Chunk(String.valueOf(this.sestava.getPopisky().getNazevZpracoval()) + " ", PDFFont.BOLD);
				p.add((Element) ck);
				ck = new Chunk(this.sestava.getPopisky().getZpracoval(), PDFFont.NORMAL);
				p.add((Element) ck);
				table.addCell(p);
				break;
			case ZPRACOVANO:
				p = new Phrase();
				ck = new Chunk(String.valueOf(this.sestava.getPopisky().getNazevDatumVyberu()) + " ", PDFFont.BOLD);
				p.add((Element) ck);
				ck = new Chunk(this.sestava.getPopisky().getDatumVyberu(), PDFFont.NORMAL);
				p.add((Element) ck);
				table.getDefaultCell().setHorizontalAlignment(0);
				table.addCell(p);
				break;
			}
		}
	}

	private void generujDokument() {
		try {
			this.doc = new Document(this.sestava.getPageSize(), this.sestava.getMarginLeft(),
					this.sestava.getMarginRight(), this.sestava.getMarginTop(), this.sestava.getMarginBottom());
			if (this.sestava.isRotate()) {
				this.doc.setPageSize(this.doc.getPageSize().rotate());
			}
			this.buffer = new ByteArrayOutputStream();

			this.writer = PdfWriter.getInstance(this.doc, this.buffer);

			this.writer.setPageEvent((PdfPageEvent) this);

			this.doc.open();

			pridejObsah();

			this.doc.close();
		} catch (Exception e) {
			this.log.error("Vyskytla se chyba pri generovani dokumentu: \n", e);
			throw new RuntimeException(e);
		}
	}

	public ByteArrayOutputStream getBuffer() {
		if (this.buffer == null) {
			generujDokument();
		}
		return this.buffer;
	}

	public Document getDoc() {
		if (this.doc == null) {
			generujDokument();
		}
		return this.doc;
	}

	protected abstract void pridejObsah();
}
