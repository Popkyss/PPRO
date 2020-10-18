package com.popkyss.sweetShop.setting;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;

public class PDFFont {
	public static Font H1 = FontFactory.getFont("Helvetica", "Cp1250", 14.5F, 1, BaseColor.BLACK);

	public static Font H2 = FontFactory.getFont("Helvetica", "Cp1250", 13.0F, 0, BaseColor.BLACK);

	public static Font NORMAL = FontFactory.getFont("Helvetica", "Cp1250", 9.5F, 0, BaseColor.BLACK);

	public static Font NORMAL_105 = FontFactory.getFont("Helvetica", "Cp1250", 10.5F, 0, BaseColor.BLACK);

	public static Font NORMAL_85 = FontFactory.getFont("Helvetica", "Cp1250", 8.5F, 0, BaseColor.BLACK);

	public static Font NORMAL_75 = FontFactory.getFont("Helvetica", "Cp1250", 7.5F, 0, BaseColor.BLACK);

	public static Font NORMAL_65 = FontFactory.getFont("Helvetica", "Cp1250", 6.5F, 0, BaseColor.BLACK);

	public static Font BOLD = FontFactory.getFont("Helvetica", "Cp1250", 9.5F, 1, BaseColor.BLACK);

	public static Font ITALIC = FontFactory.getFont("Helvetica", "Cp1250", 9.5F, 2, BaseColor.BLACK);

	public static Font CODE_10 = FontFactory.getFont("Courier", "Cp1250", 10.0F, 1, BaseColor.BLACK);

	public static Font CODE_8 = FontFactory.getFont("Helvetica", "Cp1250", 8.0F, 1, BaseColor.BLACK);

	public static Font ZAHLAVI = FontFactory.getFont("Helvetica", "Cp1250", 9.0F, 0, BaseColor.BLACK);

	public static Font TABLE = FontFactory.getFont("Helvetica", "Cp1250", 10.0F, 0, BaseColor.BLACK);

	public static Font TABLE_HLAVICKA = FontFactory.getFont("Helvetica", "Cp1250", 9.5F, 1, BaseColor.BLACK);

	public static Font ZAHLAVI_BOLD = FontFactory.getFont("Helvetica", "Cp1250", 13.0F, 1, BaseColor.BLACK);

	public static Font ZAHLAVI_S = FontFactory.getFont("Helvetica", "Cp1250", 13.0F, 0, BaseColor.BLACK);

	public static Font POPISKY = FontFactory.getFont("Times-Roman", "Cp1250", 6.0F, 0, BaseColor.BLACK);

	public static Font TRIDENI_TABULKY = FontFactory.getFont("Helvetica", "ISO-8859-1", 6.0F, 0, BaseColor.BLACK);

	public static Font getNORMAL(float fontSize) {
		return FontFactory.getFont("Helvetica", "Cp1250", fontSize, 0, BaseColor.BLACK);
	}

	public static Font getNORMAL(float fontSize, BaseColor c) {
		return FontFactory.getFont("Helvetica", "Cp1250", fontSize, 0, c);
	}

	@Deprecated
	public static Font h1 = FontFactory.getFont("Helvetica", "Cp1250", 14.5F, 1, BaseColor.BLACK);

	@Deprecated
	public static Font h2 = FontFactory.getFont("Helvetica", "Cp1250", 13.0F, 0, BaseColor.BLACK);

	@Deprecated
	public static Font normal = FontFactory.getFont("Helvetica", "Cp1250", 9.5F, 0, BaseColor.BLACK);

	@Deprecated
	public static Font bold = FontFactory.getFont("Helvetica", "Cp1250", 9.5F, 1, BaseColor.BLACK);

	@Deprecated
	public static Font italic = FontFactory.getFont("Helvetica", "Cp1250", 11.0F, 2, BaseColor.BLACK);

	@Deprecated
	public static Font code1 = FontFactory.getFont("Courier", "Cp1250", 10.0F, 1, BaseColor.BLACK);
	@Deprecated
	public static Font code2 = FontFactory.getFont("Helvetica", "Cp1250", 8.0F, 1, BaseColor.BLACK);
	@Deprecated
	public static Font zahlavi = FontFactory.getFont("Helvetica", "Cp1250", 9.0F, 0, BaseColor.BLACK);
	@Deprecated
	public static Font table = FontFactory.getFont("Helvetica", "Cp1250", 10.0F, 0, BaseColor.BLACK);
	@Deprecated
	public static Font tableHlavicka = FontFactory.getFont("Helvetica", "Cp1250", 9.5F, 1, BaseColor.BLACK);
}