package com.popkyss.sweetShop.setting;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;

public abstract class AHlavickaDatoveTabulky extends AHlavickaTabulky {
	protected Integer pocetDatovychSloupcu = null;

	@Deprecated
	public AHlavickaDatoveTabulky() {
	}

	public AHlavickaDatoveTabulky(int pocetDatovychSloupcu) {
		super(pocetDatovychSloupcu);
	}

	public abstract void addBunka(BunkaTabulky paramBunkaTabulky, int paramInt, boolean paramBoolean);

	public PdfPCell getTridenaBunka(BunkaTabulky bunka, int poradiTrideni, boolean ascending) {
		String obsahBunky = bunka.getObsahBunky();
		Chunk ch = null;
		Phrase p = bunka.getBunka().getPhrase();
		Font font = bunka.getBunka().getPhrase().getFont();
		p.setFont(font);

		if (poradiTrideni > 0) {
			obsahBunky = String.valueOf(obsahBunky) + "\n" + poradiTrideni;

			ch = new Chunk("\n" + poradiTrideni);
			p.add((Element) ch);

			if (ascending) {
				ch = new Chunk(FormatHlavicky.getUP_ARROW(), 0.0F, 0.0F);
			} else {
				ch = new Chunk(FormatHlavicky.getDOWN_ARROW(), 0.0F, 0.0F);
			}
		}

		p.add((Element) ch);
		bunka.setPhrase(p);
		if (this.log.isDebugEnabled())
			this.log.debug("Tabulka je tridena dle sloupcu: " + poradiTrideni + ". " + bunka.getObsahBunky());
		return bunka.getBunka();
	}
}