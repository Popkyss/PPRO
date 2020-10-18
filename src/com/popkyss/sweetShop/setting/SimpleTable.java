package com.popkyss.sweetShop.setting;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;

public class SimpleTable extends AStandardView {
	private SimpleTableModel stm = null;

	public SimpleTable(AStandardSestava sestava) {
		super(sestava);
	}

	protected void pridejObsah() {
		if (this.sestava instanceof SimpleTableModel) {
			this.stm = (SimpleTableModel) this.sestava;
		} else {

			LoggUtils.logE("Astandard sestava musi byt typu SimpleTable model", this.log);
		}
		try {
			SimpleTableService simpleTableService = new SimpleTableService((AStandardSestava) this.stm);
			PdfPTable tableKriteria = simpleTableService.createCriteriaTable(this.stm.getKriteria(), this.doc);
			PdfPTable tableData = simpleTableService.createDataTable(this.stm.getTable(), this.doc);

			String text = this.stm.getPopisky().getNazevSestavy();
			Paragraph p;
			if (!this.stm.isIndexed()) {
				Chunk ck = new Chunk(text, PDFFont.H1);
				p = new Paragraph(ck);
			} else {
				p = new Paragraph(PdfWriteUtils.getPharseWithIndex(text, PDFFont.H1));
			}
			p.setSpacingAfter(20.0F);
			p.setAlignment(1);
			this.doc.add((Element) p);

			p = new Paragraph();
			tableKriteria.setHorizontalAlignment(0);
			p.add((Element) tableKriteria);
			p.setSpacingAfter(20.0F);
			this.doc.add((Element) p);

			tableData.setHorizontalAlignment(0);
			if (this.stm.getSirkaSloupcuTabulky() != null) {
				tableData.setWidthPercentage(this.stm.getSirkaSloupcuTabulky(), this.doc.getPageSize());
			}
			tableData.setWidthPercentage(100.0F);

			p = new Paragraph();
			p.add((Element) tableData);
			p.setAlignment(0);
			p.setSpacingAfter(10.0F);
			this.doc.add((Element) p);
		} catch (DocumentException e) {
			this.log.error("Nepodarilo se vytvorit dokument \n" + e);
			throw new RuntimeException(e);
		}
	}

	public SimpleTableModel getStm() {
		return this.stm;
	}

	public void setStm(SimpleTableModel stm) {
		this.stm = stm;
	}
}
