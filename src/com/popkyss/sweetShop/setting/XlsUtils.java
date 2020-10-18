package com.popkyss.sweetShop.setting;

import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTFont;

public class XlsUtils {
	private static final String[] ABECEDA = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
			"M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	public static String getNazevBunky(int row, int col) {
		String result;
		if (col < ABECEDA.length) {
			result = String.valueOf(ABECEDA[col]) + (row + 1);
		} else {
			result = String.valueOf(ABECEDA[col / ABECEDA.length - 1]) + ABECEDA[col % ABECEDA.length] + (row + 1);
		}
		return result;
	}

	public static void nastavHodnotuBunky(Cell cell, Object hodnota) {
		if (hodnota instanceof String) {
			cell.setCellValue((String) hodnota);
		} else if (hodnota instanceof Boolean) {
			cell.setCellValue(((Boolean) hodnota).booleanValue());
		} else if (hodnota instanceof Date) {
			cell.setCellValue((Date) hodnota);
		} else if (hodnota instanceof Number) {
			double val = ((Number) hodnota).doubleValue();
			cell.setCellValue(val);
		} else if (hodnota == null) {
			cell.setCellType(3);
		} else {
			cell.setCellValue(hodnota.toString());
		}
	}

	public static void createAndSet(Row row, int col, Object hodnota, CellStyle style, int type) {
		Cell cell = getCell(row, col);

		cell.setCellStyle(style);
		cell.setCellType(type);
		nastavHodnotuBunky(cell, hodnota);
	}

	public static void createAndSet(Row row, int col, Object hodnota, Cell vzor) {
		createAndSet(row, col, hodnota, vzor.getCellStyle(), vzor.getCellType());
	}

	public static void createAndSet(Row row, int col, Object hodnota, XlsCellProperties vlastnost) {
		createAndSet(row, col, hodnota, vlastnost.getStyle(), vlastnost.getType());
	}

	public static void createAndSet(Sheet sheet, int row, int col, Object hodnota, CellStyle style, int type) {
		createAndSet(getRow(sheet, row), col, hodnota, style, type);
	}

	public static void createAndSet(Sheet sheet, int row, int col, Object hodnota, XlsCellProperties vlastnost) {
		createAndSet(getRow(sheet, row), col, hodnota, vlastnost);
	}

	public static Cell createCell(Sheet sheet, int rowNum, int colNum) {
		Row row = getRow(sheet, rowNum);
		Cell cell = getCell(row, colNum);
		return cell;
	}

	public static Cell getCell(Row row, int colNum) {
		Cell cell = row.getCell(colNum);
		if (cell == null) {
			return row.createCell(colNum);
		}
		return cell;
	}

	public static Row getRow(Sheet sheet, int rowNum) {
		Row row = sheet.getRow(rowNum);
		if (row == null) {
			return sheet.createRow(rowNum);
		}
		return row;
	}

	public static Font getBoldFont(Workbook workbook, int size, String fontName) {
		Font bold = workbook.createFont();
		bold.setFontHeightInPoints((short) size);
		bold.setFontName((fontName == null) ? "Arial" : fontName);
		bold.setBoldweight((short) 700);

		return bold;
	}

	public Font getNormalFont(Workbook workbook, int size, String fontName) {
		Font normal = workbook.createFont();
		normal.setFontHeightInPoints((short) size);
		normal.setFontName((fontName == null) ? "Arial" : fontName);
		normal.setBoldweight((short) 400);

		return normal;
	}

	public static Font getKurziva(Workbook workbook, Font font) {
		Font ital = copyFontProps(font, workbook);
		ital.setStrikeout(true);
		return ital;
	}

	public static Font[] deriveIndexFonts(Cell cell) {
		if (cell == null) {
			return null;
		}
		return deriveIndexFonts(cell.getRow().getSheet().getWorkbook().getFontAt(cell.getCellStyle().getFontIndex()),
				cell.getSheet().getWorkbook());
	}

	public static Font[] deriveIndexFonts(Font font, Workbook wb) {
		if (font == null) {
			return null;
		}
		Font[] fonty = new Font[3];

		if (font instanceof XSSFFont) {
			fonty[1] = (Font) new XSSFFont((CTFont) ((XSSFFont) font).getCTFont());

			fonty[0] = (Font) new XSSFFont((CTFont) ((XSSFFont) fonty[1]).getCTFont());
			fonty[0].setTypeOffset((short) 1);

			fonty[2] = (Font) new XSSFFont((CTFont) ((XSSFFont) fonty[1]).getCTFont());
			fonty[2].setTypeOffset((short) 2);
		} else if (font instanceof org.apache.poi.hssf.usermodel.HSSFFont) {
			fonty[1] = copyFontProps(font, wb);

			fonty[0] = copyFontProps(font, wb);
			fonty[0].setTypeOffset((short) 1);

			fonty[2] = copyFontProps(font, wb);
			fonty[2].setTypeOffset((short) 2);
		}

		return fonty;
	}

	private static Font copyFontProps(Font font, Workbook wb) {
		Font f = wb.createFont();
		f.setFontHeightInPoints(font.getFontHeightInPoints());
		f.setColor(font.getColor());
		f.setBoldweight(font.getBoldweight());
		f.setItalic(font.getItalic());
		f.setStrikeout(font.getStrikeout());
		f.setUnderline(font.getUnderline());
		f.setFontName(font.getFontName());
		f.setTypeOffset(font.getTypeOffset());
		return f;
	}

	public static int[] nactiSirkySloupcu(Sheet sheet, int pocet) {
		int[] sirky = new int[pocet];
		for (int i = 0; i < pocet; i++) {
			sirky[i] = sheet.getColumnWidth(i);
		}
		return sirky;
	}

	public static String escapeExcelSheetName(String text, String nahrada) {
		if (text == null) {
			return null;
		}
		return text.replaceAll("[\\[\\]\\\\\\/\\?\\*]", (nahrada == null) ? "" : nahrada);
	}
}