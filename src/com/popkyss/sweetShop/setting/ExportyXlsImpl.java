package com.popkyss.sweetShop.setting;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportyXlsImpl implements IExportyXls {
	public ByteArrayOutputStream exportSeznamXls(List<? extends Object> radky, List<FormatSloupce> sloupce,
			Properties lng, Locale locale, List<KriteriumSestavy> kriteria) throws IOException, RuntimeException {
		return exportSeznamXls(radky, sloupce, lng, locale, kriteria, 2147483647);
	}

	public ByteArrayOutputStream exportSeznamXls(List<?> radky, List<FormatSloupce> sloupce, Properties lng,
			Locale locale, List<KriteriumSestavy> kriteria, int pocetBunekSirkyLimit)
			throws IOException, RuntimeException {
		Workbook wb = vytvorWorkBook(radky, sloupce, lng, locale, kriteria, pocetBunekSirkyLimit);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		wb.write(bos);

		return bos;
	}

	protected Workbook vytvorWorkBook(List<?> radky, List<FormatSloupce> sloupce, Properties lng, Locale locale,
			List<KriteriumSestavy> kriteria, int pocetBunekSirkyLimit) throws RuntimeException {
		XSSFWorkbook xSSFWorkbook = new XSSFWorkbook();

		Sheet sheet = xSSFWorkbook.createSheet(lng.getProperty("export.list.nazev", "data"));

		int delkaKriterii = vytvorFiltracniKriteria(sheet, kriteria, lng, locale, 0);
		int prvniRadek = (delkaKriterii == 0) ? 0 : (delkaKriterii + 2);

		vytvorZahlavi(sheet, sloupce, lng, prvniRadek);
		if (radky.size() > 0) {
			vytvorTeloSeznamu(sheet, radky, sloupce, lng, prvniRadek + 1, pocetBunekSirkyLimit);
		}

		return (Workbook) xSSFWorkbook;
	}

	protected int vytvorFiltracniKriteria(Sheet sheet, List<KriteriumSestavy> kriteria, Properties lng, Locale locale,
			int radek) {
		if (kriteria == null || kriteria.isEmpty()) {
			return 0;
		}

		XlsCellStyleFactory styles = getCellStyleFactory(sheet.getWorkbook());

		nastavHlavickuKriterii(sheet, radek, lng, styles);

		int poradi = 0;
		for (KriteriumSestavy krit : kriteria) {
			if (krit == null) {
				continue;
			}

			nastavOznaceniKrieria(sheet, radek + poradi + 1, krit, lng, styles);
			nastavHodnotuKriteria(sheet, radek + poradi + 1, krit, lng, styles, locale);

			poradi++;
		}

		return poradi;
	}

	protected void nastavOznaceniKrieria(Sheet sheet, int radek, KriteriumSestavy krit, Properties lng,
			XlsCellStyleFactory styles) {
		XlsUtils.createAndSet(sheet, radek, 0, lng.getProperty(krit.getNazevKlic(), krit.getNazevKlic()),
				styles.getNadpis((short) 1), 1);
	}

	protected void nastavHodnotuKriteria(Sheet sheet, int radek, KriteriumSestavy krit, Properties lng,
			XlsCellStyleFactory styles, Locale locale) {
		NumberFormat numberFormat = new DecimalFormat(krit.getMaskaCislo(), new DecimalFormatSymbols(locale));
		DateFormat dateFormat = new SimpleDateFormat(krit.getMaskaDatum(), new DateFormatSymbols(locale));
		XlsUtils.createAndSet(sheet, radek, 1,
				naformatujHodnotu(krit.getHodnota(), krit.getOddelovac(),lng, numberFormat, dateFormat),
				styles.getTextStyle((short) 1), 0);

		if (krit.getHodnota() instanceof Collection) {
			Cell cell = sheet.getRow(radek).getCell(1);
			nastavBunkuProSeznam(krit, cell, styles);
		}
	}

	@SuppressWarnings({ "incomplete-switch", "rawtypes" })
	protected void nastavBunkuProSeznam(KriteriumSestavy krit, Cell cell, XlsCellStyleFactory styles) {
		int vyska;
		CellStyle style;
		int i, pocet = 1;
		if (krit.getHodnota() instanceof Collection) {
			pocet = ((Collection) krit.getHodnota()).size();
		}

		switch (krit.getExpandList()) {

		case NEW_LINE:
			vyska = cell.getRow().getHeight();
			cell.getRow().setHeight(
					((Integer) MathUtils.min(Integer.valueOf(vyska * pocet), Integer.valueOf(32767))).shortValue());

			style = styles.getUserStyle("criteriaMultilineStyle");
			if (style == null) {
				style = styles.registerNewUserStyle("criteriaMultilineStyle");
				style.cloneStyleFrom(cell.getCellStyle());
				style.setWrapText(true);
			}
			cell.setCellStyle(style);
			break;

		case NONE:
			for (i = 1; i < pocet; i++) {
				XlsUtils.createCell(cell.getSheet(), cell.getRowIndex(), cell.getColumnIndex() + i);
			}
			cell.getSheet().addMergedRegion(new CellRangeAddress(cell.getRowIndex(), cell.getRowIndex(), 1, pocet));
			break;
		}
	}

	protected void nastavHlavickuKriterii(Sheet sheet, int radek, Properties lng, XlsCellStyleFactory styles) {
		XlsUtils.createAndSet(sheet, radek, 0, lng.getProperty("export.kriteria", ""), styles.getNadpis((short) 2), 1);
		sheet.addMergedRegion(new CellRangeAddress(radek, radek, 0, 1));
	}

	protected XlsCellStyleFactory getCellStyleFactory(Workbook workbook) {
		return new XlsCellStyleFactory(workbook);
	}

	@SuppressWarnings("unchecked")
	protected Object naformatujHodnotu(Object hodnota, String oddelovac, Properties lng, NumberFormat numberFormat,
			DateFormat dateFormat) {
		Collection<? extends Object> prvky;
		if (hodnota == null) {
			return null;
		}

		if (hodnota instanceof Collection) {
			prvky = (Collection<? extends Object>) hodnota;
		} else {
			prvky = Arrays.asList(new Object[] { hodnota });
		}

		StringBuilder sb = new StringBuilder();
		for (Iterator<? extends Object> it = prvky.iterator(); it.hasNext();) {
			Object prvek = it.next();
			if (prvek == null) {
				continue;
			}
			if (prvek instanceof Boolean) {
				sb.append(lng.getProperty(((Boolean) prvek).booleanValue() ? "ano" : "ne", prvek.toString()));
			} else if (prvek instanceof Number) {
				sb.append(numberFormat.format(prvek));
			} else if (prvek instanceof Date) {
				sb.append(dateFormat.format(prvek));
			} else if (prvek instanceof Konstanty.IVycet) {
				sb.append(((Konstanty.IVycet) prvek).getPopisek());
			} else {
				sb.append(prvek.toString());
			}
			if (it.hasNext()) {
				sb.append(oddelovac);
			}
		}

		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	protected void vytvorTeloSeznamu(Sheet sheet, List<?> radky, List<FormatSloupce> sloupce, Properties lng,
			int cisloRadku, int pocetBunekSirkyLimit) throws RuntimeException {
		int poradiSloupce = 0;
		int radekSirky = pocetBunekSirkyLimit / sloupce.size();
		boolean sirkyNastaveny = false;

		XlsCellStyleFactory styles = getCellStyleFactory(sheet.getWorkbook());

		Method[] metody = zjistiMetody(radky.get(0), sloupce);

		int radekCislo = cisloRadku;
		try {
			for (Object radek : radky) {
				Row row = sheet.createRow(radekCislo);
				poradiSloupce = 0;
				for (FormatSloupce format : sloupce) {
					Cell cell = row.createCell(poradiSloupce);

					Object hodnotaSloupce = metody[poradiSloupce].invoke(radek, new Object[0]);

					nastavStylBunky(cell, format, styles);

					if (hodnotaSloupce instanceof Boolean && format.getSubstituteHodnota() == null) {
						hodnotaSloupce = lng.getProperty(((Boolean) hodnotaSloupce).booleanValue() ? "ano" : "ne",
								hodnotaSloupce.toString());
					} else if (format.getSubstituteHodnota() != null) {
						hodnotaSloupce = lng.getProperty(String.valueOf(format.getSubstituteHodnota()) + hodnotaSloupce,
								(hodnotaSloupce == null) ? "" : hodnotaSloupce.toString());
					} else if (hodnotaSloupce instanceof Konstanty.IVycet) {
						hodnotaSloupce = ((Konstanty.IVycet) hodnotaSloupce).getPopisek();
					} else if (hodnotaSloupce instanceof Collection) {
						hodnotaSloupce = PokyCollections.collectionToString((Collection) hodnotaSloupce, "");
					}

					nastavHodnotuBunky(cell, hodnotaSloupce);

					poradiSloupce++;
				}

				radekCislo++;
				if (radekCislo == radekSirky) {
					nastavSirkySloupcu(sheet, sloupce.size());
					sirkyNastaveny = true;
				}
			}

			if (!sirkyNastaveny) {
				nastavSirkySloupcu(sheet, sloupce.size());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void nastavHodnotuBunky(Cell cell, Object hodnotaSloupce) {
		XlsUtils.nastavHodnotuBunky(cell, hodnotaSloupce);
	}

	protected void nastavSirkySloupcu(Sheet sheet, int pocetSloupcu) {
		for (int i = 0; i < pocetSloupcu; i++) {
			sheet.autoSizeColumn(i, true);
		}
	}

	protected void nastavStylBunky(Cell cell, FormatSloupce format, XlsCellStyleFactory styles) {
		if (Number.class.isAssignableFrom(format.getTypHodnoty())) {
			cell.setCellType(0);
			cell.setCellStyle(styles.getNumericStyle(getZarovnaniShortValue(format.getZarovnaniHodnota()),
					format.getMaskaCislo()));
		} else if (Boolean.class.isAssignableFrom(format.getTypHodnoty())) {
			cell.setCellType(4);
			cell.setCellStyle(styles.getTextStyle(getZarovnaniShortValue(format.getZarovnaniHodnota())));
		} else if (Date.class.isAssignableFrom(format.getTypHodnoty())) {
			cell.setCellType(0);
			cell.setCellStyle(
					styles.getDateStyle(getZarovnaniShortValue(format.getZarovnaniHodnota()), format.getMaskaDatum()));
		} else {
			cell.setCellType(1);
			cell.setCellStyle(styles.getTextStyle(getZarovnaniShortValue(format.getZarovnaniHodnota())));
		}
	}

	private Method[] zjistiMetody(Object object, List<FormatSloupce> sloupce) throws RuntimeException {
		Method[] metody = new Method[sloupce.size()];
		int poradi = 0;
		for (FormatSloupce format : sloupce) {
			metody[poradi++] = ReflectionUtils.getMethod(object,
					ReflectionUtils.getGetterMethod(object, format.getNazevSloupce()), new Class[0]);
		}
		return metody;
	}

	protected void vytvorZahlavi(Sheet sheet, List<FormatSloupce> sloupce, Properties lng, int cisloRadku) {
		Row row = sheet.createRow(cisloRadku);

		XlsCellStyleFactory styles = getCellStyleFactory(sheet.getWorkbook());

		int poradi = 0;
		for (FormatSloupce sl : sloupce) {
			Cell cell = row.createCell(poradi++);

			nastavHodnotuZahlavi(cell, sl, lng, styles);
		}
	}

	protected void nastavHodnotuZahlavi(Cell cell, FormatSloupce sloupec, Properties lng, XlsCellStyleFactory styles) {
		String head = lng.getProperty(sloupec.getNazevSloupceLng(), sloupec.getNazevSloupceLng());
		cell.setCellType(1);
		cell.setCellStyle(styles.getNadpis(getZarovnaniShortValue(sloupec.getZarovnaniHlavicka())));
		cell.setCellValue(head);
	}

	private short getZarovnaniShortValue(FormatSloupce.Zarovnani zarovnani) {
		short zarovnaniShort;
		if (zarovnani == null || zarovnani == FormatSloupce.Zarovnani.NASTRED) {
			zarovnaniShort = 2;
		} else if (zarovnani == FormatSloupce.Zarovnani.VLEVO) {
			zarovnaniShort = 1;
		} else {
			zarovnaniShort = 3;
		}
		return zarovnaniShort;
	}
}