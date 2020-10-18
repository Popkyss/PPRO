package com.popkyss.sweetShop.setting;

import org.apache.log4j.Logger;

import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;

public class BunkaTabulky {
	private String obsahBunky;
	private CellStyle stylBunky;
	private PdfPCell bunka;
	static Logger log = Logger.getLogger(BunkaTabulky.class);

	private BunkaTabulky(String obsahBunky, CellStyle stylBunky) {
		this.obsahBunky = obsahBunky;
		this.stylBunky = stylBunky;
		this.bunka = new PdfPCell();
		this.bunka.setNoWrap(!stylBunky.isWrapped());
		this.bunka.setBorderColor(stylBunky.getBarvaOhraniceniTabulky());
		this.bunka.setBackgroundColor(stylBunky.getVyplnBunky());
		this.bunka.setHorizontalAlignment(stylBunky.getZarovnaniBunky());
		this.bunka.setVerticalAlignment(5);
		this.bunka.setPhrase(new Phrase(obsahBunky, stylBunky.getFontBunky()));
	}

	public static BunkaTabulky defaultHlavicka(String obsahBunky) {
		FormatHlavicky hf = new FormatHlavicky();
		return new BunkaTabulky(obsahBunky, (CellStyle) hf);
	}

	public static BunkaTabulky hlavicka(String obsahBunky, FormatHlavicky formatHlavicky) {
		return new BunkaTabulky(obsahBunky, (CellStyle) formatHlavicky);
	}

	public static BunkaTabulky defaultBunka(String obsahBunky) {
		return new BunkaTabulky(obsahBunky, (CellStyle) new FormatBunky());
	}

	public static BunkaTabulky bunka(String obsahBunky, CellStyle cs) {
		return new BunkaTabulky(obsahBunky, cs);
	}

	public String getObsahBunky() {
		return this.obsahBunky;
	}

	public void setObsahBunky(String obsahBunky) {
		this.bunka.setPhrase(new Phrase(obsahBunky, this.stylBunky.getFontBunky()));
		this.obsahBunky = obsahBunky;
	}

	@Deprecated
	public CellStyle getStylBunky() {
		return this.stylBunky;
	}

	@Deprecated
	public void setStylBunky(CellStyle stylBunky) {
		this.bunka.setPhrase(new Phrase(this.obsahBunky, stylBunky.getFontBunky()));
		this.stylBunky = stylBunky;
	}

	public PdfPCell getBunka() {
		return this.bunka;
	}

	public void setPhrase(Phrase phrase) {
		this.bunka.setPhrase(phrase);
	}
}