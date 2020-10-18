package com.popkyss.sweetShop.setting;

import java.util.List;

import org.apache.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class SimpleTableService implements ITables {
	Logger log = Logger.getLogger(SimpleTableService.class);

	private AStandardSestava st;

	public SimpleTableService(AStandardSestava st) {
		this.st = st;
	}

	public PdfPTable createCriteriaTable(List<Kriteria> kriteria, Document doc) throws DocumentException {
		int pocetSloupcu = 3;
		PdfPTable table = new PdfPTable(pocetSloupcu);
		ISirkaSloupcu tableWidth = SirkaSloupcuAlg.sirkaSloupcuFactory(SirkaSloupcuSwitcher.KRITERIA_STANDARD,
				pocetSloupcu, doc);

		BunkaTabulky bt = BunkaTabulky.defaultHlavicka(this.st.getPopisky().getNazevTabulkyKriteria());

		bt.getBunka().setBorderColor(BaseColor.BLACK);
		bt.getBunka().setColspan(3);
		bt.getBunka().setPadding(3.0F);
		if (this.st.isIndexed()) {
			PdfWriteUtils.bunkaTabulkyIndexed(bt);
		}
		table.addCell(bt.getBunka());
		table.setHeaderRows(1);

		float sirka = tableWidth.getSirkaBunky(bt.getBunka());

		for (int j = 0; j < kriteria.size(); j++) {
			float borderBottom = 0.0F;
			if (j == kriteria.size() - 1)
				borderBottom = 0.5F;
			Kriteria krit = kriteria.get(j);
			FormatBunky formatBunky = new FormatBunky();
			formatBunky.setFontBunky(PDFFont.BOLD);
			bt = BunkaTabulky.bunka(krit.getNazevKriteria(), (CellStyle) formatBunky);
			bt.getBunka().setBorderWidth(0.0F);
			bt.getBunka().setBorderColorLeft(BaseColor.BLACK);
			if (j == kriteria.size() - 1) {
				bt.getBunka().setBorderColorBottom(BaseColor.BLACK);
			}
			bt.getBunka().setBorderWidthRight(0.5F);
			bt.getBunka().setBorderWidthLeft(0.5F);
			bt.getBunka().setBorderWidthBottom(borderBottom);
			bt.getBunka().setNoWrap(true);
			if (this.st.isIndexed()) {
				PdfWriteUtils.bunkaTabulkyIndexed(bt);
			}
			tableWidth.addCell(bt.getBunka(), 0, table);
			bt = BunkaTabulky.bunka(krit.getTypKriteria(), (CellStyle) new FormatBunky());
			bt.getBunka().setBorderWidth(0.0F);
			bt.getBunka().setBorderWidthRight(0.5F);
			if (j == kriteria.size() - 1)
				bt.getBunka().setBorderColorBottom(BaseColor.BLACK);
			bt.getBunka().setBorderWidthBottom(borderBottom);
			bt.getBunka().setNoWrap(true);
			if (this.st.isIndexed()) {
				PdfWriteUtils.bunkaTabulkyIndexed(bt);
			}
			tableWidth.addCell(bt.getBunka(), 1, table);

			String s = "";
			for (int i = 0; i < (krit.getKriteria()).length; i++) {
				s = String.valueOf(s) + krit.getKriteria()[i];
				if (i < (krit.getKriteria()).length - 1) {
					s = String.valueOf(s) + krit.getOddelovacKriteria();
				}
			}
			bt = BunkaTabulky.bunka(s, (CellStyle) new FormatBunky());
			if (j == kriteria.size() - 1)
				bt.getBunka().setBorderColorBottom(BaseColor.BLACK);
			bt.getBunka().setBorderWidth(0.0F);
			bt.getBunka().setBorderWidthRight(0.5F);
			bt.getBunka().setBorderColorRight(BaseColor.BLACK);
			bt.getBunka().setBorderWidthBottom(borderBottom);
			if (this.st.isIndexed()) {
				PdfWriteUtils.bunkaTabulkyIndexed(bt);
			}
			tableWidth.addCell(bt.getBunka(), 2, table);
		}

		tableWidth.setTableWidths(table);
		if (tableWidth.getTotalTableWidth() < sirka) {
			float[] sirkySloupcu = tableWidth.getSirkySloupcu();

			for (int i = 0; i < sirkySloupcu.length; i++) {
				float rozdil = sirka - tableWidth.getTotalTableWidth();
				float podil = rozdil / tableWidth.getTotalTableWidth();
				float vysledek = podil * sirkySloupcu[i] + sirkySloupcu[i];
				sirkySloupcu[i] = vysledek;
			}

			table.setWidthPercentage(sirkySloupcu, doc.getPageSize());
		}
		return table;
	}

	public PdfPTable createDataTable(SimpleDataTable dataTable, Document doc) throws DocumentException {
		int pocetSloupcu = dataTable.size();
		int sloupec = 0;
		PdfPTable table = new PdfPTable(pocetSloupcu);

		ISirkaSloupcu tableWidth = SirkaSloupcuAlg.sirkaSloupcuFactory(dataTable.getSwitcher(), pocetSloupcu, doc);

		table.getDefaultCell().setPadding(3.0F);

		table.getDefaultCell().setBorderWidth(1.0F);

		table.getDefaultCell().setHorizontalAlignment(1);
		table.getDefaultCell().setBorderWidth(1.0F);

		table.getDefaultCell().setNoWrap(true);

		AHlavickaTabulky ht = dataTable.getHlavickaTabulky();
		if (ht instanceof HlavickaTabulky) {
			for (int i = 0; i < pocetSloupcu; i++) {
				PdfPCell cell = ((HlavickaTabulky) ht).getCell(i);
				if (this.st.isIndexed()) {
					cell = PdfWriteUtils.getPdfpCellwithIndex(cell);
				}
				tableWidth.addCell(cell, sloupec, table);
				sloupec++;
			}
		} else if (ht instanceof SlozenaHlavickaTabulky) {
			this.log.debug("tisknu slozenou hlavicku");
			PdfPTable t = ((SlozenaHlavickaTabulky) ht).getTable();
			if (dataTable.getSirkaSloupcuTabulky() != null) {
				t.setWidthPercentage(dataTable.getSirkaSloupcuTabulky(), doc.getPageSize());
			} else {
				this.log.warn(
						"Sirka sloupcu nebyla nastavbena! Hlavicka pravdepodobne nebude odpovidat zbytku tabulky");
			}
			if (this.st.isIndexed()) {
				PdfWriteUtils.pdfpTableIndexed(t);
			}
			PdfPCell cell = new PdfPCell(t);
			cell.setColspan(ht.getPocetSloupcu());
			cell.setBorderWidth(0.0F);
			cell.setPadding(0.0F);

			table.addCell(cell);
		} else {
			this.log.warn("nepodarilo se pridat hlavicku tabulky");
		}

		table.setHeaderRows(1);

		table.getDefaultCell().setBorderWidth(0.5F);

		if (dataTable.getPocetObarvenychRadku() != 0) {
			defaultniBarveniRadku(dataTable, table, tableWidth);
		} else {
			uzivatelskeBarveniRadku(dataTable, table, tableWidth);
		}
		tableWidth.setTableWidths(table);
		return table;
	}

	private void uzivatelskeBarveniRadku(SimpleDataTable dataTable, PdfPTable table, ISirkaSloupcu tableWidth) {
		for (int j = 0; j < dataTable.getRadkyTabulky().size(); j++) {
			RadekTabulky rt = dataTable.getRadkyTabulky().get(j);
			float borderBottom = 0.0F;
			if (j == dataTable.getRadkyTabulky().size() - 1)
				borderBottom = 0.5F;
			for (int i = 0; i < rt.size(); i++) {
				BunkaTabulky bn = rt.getBunka(i);

				bn.getBunka().setBorderWidth(0.0F);
				if (i == 0) {
					bn.getBunka().setBorderWidthLeft(0.5F);
					bn.getBunka().setBorderColorLeft(BaseColor.BLACK);
				}
				if (j == dataTable.getRadkyTabulky().size() - 1) {
					bn.getBunka().setBorderWidthBottom(0.5F);
					bn.getBunka().setBorderColorBottom(BaseColor.BLACK);
				} else {
					bn.getBunka().setBorderWidthBottom(borderBottom);
				}
				if (i == rt.size() - 1) {
					bn.getBunka().setBorderWidthRight(0.5F);
					bn.getBunka().setBorderColorRight(BaseColor.BLACK);
				} else {
					bn.getBunka().setBorderWidthRight(0.5F);
				}
				if (this.st.isIndexed()) {
					PdfWriteUtils.bunkaTabulkyIndexed(bn);
				}
				tableWidth.addCell(bn.getBunka(), i, table);
			}
		}
	}

	private void defaultniBarveniRadku(SimpleDataTable dataTable, PdfPTable table, ISirkaSloupcu tableWidth) {
		int temp = 0;
		for (int j = 0; j < dataTable.getRadkyTabulky().size(); j++) {
			temp++;
			RadekTabulky rt = dataTable.getRadkyTabulky().get(j);
			float borderBottom = 0.0F;
			if (j == dataTable.getRadkyTabulky().size() - 1)
				borderBottom = 0.5F;
			for (int i = 0; i < rt.size(); i++) {
				BunkaTabulky bn = rt.getBunka(i);

				if (bn.getBunka().getBackgroundColor().equals(BaseColor.WHITE)) {
					if (temp < dataTable.getPocetObarvenychRadku() + 1) {
						bn.getBunka().setGrayFill(1.0F);
					} else {
						bn.getBunka().setGrayFill(0.95F);
					}
				}
				bn.getBunka().setBorderWidth(0.0F);
				if (i == 0) {
					bn.getBunka().setBorderWidthLeft(0.5F);
					bn.getBunka().setBorderColorLeft(BaseColor.BLACK);
				}
				if (j == dataTable.getRadkyTabulky().size() - 1) {
					bn.getBunka().setBorderWidthBottom(0.5F);
					bn.getBunka().setBorderColorBottom(BaseColor.BLACK);
				} else {
					bn.getBunka().setBorderWidthBottom(borderBottom);
				}
				if (i == rt.size() - 1) {
					bn.getBunka().setBorderWidthRight(0.5F);
					bn.getBunka().setBorderColorRight(BaseColor.BLACK);
				} else {
					bn.getBunka().setBorderWidthRight(0.5F);
				}
				if (this.st.isIndexed()) {
					PdfWriteUtils.bunkaTabulkyIndexed(bn);
				}
				tableWidth.addCell(bn.getBunka(), i, table);
			}
			if (temp == dataTable.getPocetObarvenychRadku() * 2)
				temp = 0;
		}
	}
}