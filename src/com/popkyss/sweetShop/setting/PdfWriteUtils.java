package com.popkyss.sweetShop.setting;

import java.util.Iterator;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;

public final class PdfWriteUtils {
	public static PdfPCell getPdfpCellwithIndex(PdfPCell cell) {
		if (cell != null) {
			PdfPCell returnCell = new PdfPCell(cell);
			if (cell.getPhrase() != null) {
				Phrase p = new Phrase();
				p.setFont(cell.getPhrase().getFont());
				for (Object o : cell.getPhrase().getChunks()) {
					if (o instanceof Chunk) {
						Chunk ch = (Chunk) o;
						String s = ch.getContent();
						if (s != null || obsahujeIndex(s)) {
							p.add((Element) getPharseWithIndex(s, ch.getFont()));
							continue;
						}
						p.add((Element) ch);
					}
				}

				returnCell.setPhrase(p);
			}
			return returnCell;
		}
		return cell;
	}

	public static Phrase getPharseWithIndex(String s, Font f) {
		Phrase p = new Phrase();
		Font index = new Font(f);
		index.setSize(f.getSize() / 1.8F);
		if (s != null) {
			for (int i = 0; i < s.length();) {
				char c = s.charAt(i);
				if (c == '^' && i + 1 < s.length()) {
					Chunk temp = new Chunk(s.charAt(++i));
					temp.setFont(index);
					temp.setTextRise(4.8F);
					p.add((Element) temp);
				} else if (c == '_' && i + 1 < s.length()) {
					Chunk temp = new Chunk(s.charAt(++i));
					temp.setFont(index);
					temp.setTextRise(-1.5F);
					p.add((Element) temp);
				} else if (c != '_' && c != '^') {
					p.add((Element) new Chunk(c, f));
				}
				i++;
			}
		}
		return p;
	}

	@SuppressWarnings("rawtypes")
	public static float getPhraseLenght(Phrase p) {
		float sirka = 0.0F;
		Font font = p.getFont();
		String text = p.getContent();
		Chunk ch = null;
		Iterator iterator = p.getChunks().iterator();
		if (iterator.hasNext()) {
			Object o = iterator.next();
			if (o instanceof Chunk) {
				ch = (Chunk) o;
				font = ch.getFont();
			}
		}

		String[] radky = text.split("\n");
		float returnSirka = 0.0F;
		for (int i = 0; i < radky.length; i++) {

			BaseFont bf = font.getCalculatedBaseFont(false);
			sirka = bf.getWidthPoint(radky[i], font.getSize());

			sirka += bf.getAscentPoint(radky[i], font.getSize());
			if (returnSirka < sirka) {
				returnSirka = sirka;
			}
		}
		return returnSirka;
	}

	public static void bunkaTabulkyIndexed(BunkaTabulky bt, String obsah) {
		if (bt != null && obsahujeIndex(bt.getObsahBunky())) {
			Font font = bt.getBunka().getPhrase().getFont();
			bt.setPhrase(getPharseWithIndex(obsah, font));
		}
	}

	public static void bunkaTabulkyIndexed(BunkaTabulky bt) {
		if (bt != null) {
			String obsah = bt.getObsahBunky();
			bunkaTabulkyIndexed(bt, obsah);
		}
	}

	private static boolean obsahujeIndex(String obsahBunky) {
		if (obsahBunky == null) {
			return false;
		}
		boolean obsahujeHorni = (obsahBunky.indexOf('^') != -1);
		boolean obsahujeDolni = (obsahBunky.indexOf('_') != -1);
		return !(!obsahujeHorni && !obsahujeDolni);
	}

	public static void pdfpTableIndexed(PdfPTable t) {
		for (Object o : t.getRows()) {
			if (o instanceof PdfPRow) {
				PdfPRow row = (PdfPRow) o;
				byte b;
				int i;
				PdfPCell[] arrayOfPdfPCell;
				for (i = (arrayOfPdfPCell = row.getCells()).length, b = 0; b < i;) {
					PdfPCell tempCell = arrayOfPdfPCell[b];
					if (tempCell != null) {
						if (tempCell.getTable() != null) {
							pdfpTableIndexed(tempCell.getTable());
						}
						if (tempCell.getPhrase() != null)
							tempCell.setPhrase(getPdfpCellwithIndex(tempCell).getPhrase());
					}
					b++;
				}

			}
		}
	}
}