package com.popkyss.sweetShop.setting;

import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;

public interface ITables {
  PdfPTable createCriteriaTable(List<Kriteria> paramList, Document paramDocument) throws DocumentException;
  
  PdfPTable createDataTable(SimpleDataTable paramSimpleDataTable, Document paramDocument) throws DocumentException;
}
