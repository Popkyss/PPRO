package com.popkyss.sweetShop.setting;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public interface ISirkaSloupcu {
	void addCell(PdfPCell paramPdfPCell, int paramInt, PdfPTable paramPdfPTable);

	void setTableWidths(PdfPTable paramPdfPTable) throws DocumentException;

	float getTotalTableWidth();

	float[] getSirkySloupcu();

	float getSirkaBunky(PdfPCell paramPdfPCell);
}