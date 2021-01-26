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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class ExportyPdfImpl implements IExportyPdf {
	private IDateTime dateTime = BaseServiceFactory.getDateTime();

	public ByteArrayOutputStream exportSeznamPdf(List<?> radky, List<FormatSloupce> sloupce, Properties lng,
			Locale locale, boolean naVysku, List<KriteriumSestavy> kriteria) throws IOException, RuntimeException {
		List<RadekTabulky> radkyTabulky;
		HlavickaTabulky hlavicka = vytvorZahlavi(sloupce, lng);

		if (radky != null && radky.size() > 0) {
			radkyTabulky = vytvorTeloSeznamu(radky, sloupce, lng, locale);
		} else {
			radkyTabulky = Collections.singletonList(getPrazdnyRadekTabulky(sloupce.size()));
		}

		SimpleDataTable sdt = new SimpleDataTable((AHlavickaDatoveTabulky) hlavicka, radkyTabulky);
		SimpleTableModel stm = new SimpleTableModel(sdt, vytvorKriteria(kriteria, lng, locale),
				getPopisky(lng, locale));

		stm.setRotate(!naVysku);

		return (new SimpleTable((AStandardSestava) stm)).getBuffer();
	}

	private List<Kriteria> vytvorKriteria(List<KriteriumSestavy> kriteria, Properties lng, Locale locale) {
		List<Kriteria> krits = new ArrayList<Kriteria>();
		if (kriteria == null || kriteria.isEmpty()) {
			return krits;
		}

		for (KriteriumSestavy krit : kriteria) {
			if (krit == null) {
				continue;
			}
			NumberFormat numberFormat = new DecimalFormat(krit.getMaskaCislo(), new DecimalFormatSymbols(locale));
			DateFormat dateFormat = new SimpleDateFormat(krit.getMaskaDatum(), new DateFormatSymbols(locale));
			Kriteria tmp = new Kriteria(lng.getProperty(krit.getNazevKlic(), krit.getNazevKlic()), null,
					formatujHodnotyKriteria(krit.getHodnota(), lng, numberFormat, dateFormat));
			tmp.setOddelovacKriteria(krit.getOddelovac());
			krits.add(tmp);
		}

		return krits;
	}

	@SuppressWarnings("rawtypes")
	private String[] formatujHodnotyKriteria(Object hodnota, Properties lng, NumberFormat numberFormat,
			DateFormat dateFormat) {
		Collection<?> prvky;
		if (hodnota == null) {
			return new String[0];
		}

		if (hodnota instanceof Collection) {
			prvky = (Collection) hodnota;
		} else {
			prvky = Arrays.asList(new Object[] { hodnota });
		}

		List<String> vysledek = new ArrayList<String>();

		for (Object prvek : prvky) {
			if (prvek != null) {
				if (hodnota instanceof Boolean) {
					vysledek.add(lng.getProperty(((Boolean) prvek).booleanValue() ? "ano" : "ne", prvek.toString()));
					continue;
				}
				vysledek.add(formatujHodnotu(prvek, numberFormat, dateFormat));
			}
		}

		return vysledek.<String>toArray(new String[vysledek.size()]);
	}

	private Popisky getPopisky(Properties lng, Locale locale) {
		DateFormat form = new SimpleDateFormat("dd.MM.yyyy HH:mm", new DateFormatSymbols(locale));
		String sestavaNazev = lng.getProperty("export.sestava.nazev", lng.getProperty("pageName", ""));

		Popisky popisky = new Popisky();
		popisky.setDatumVyberu(form.format(this.dateTime.getCurrentDate()));
		popisky.setNazevAplikace(lng.getProperty("app.nazev", ""));
		popisky.setNazevDatumVyberu(lng.getProperty("export.datum.vyberu", ""));
		popisky.setNazevSestavy(sestavaNazev);
		popisky.setNazevTabulkyKriteria(lng.getProperty("export.kriteria", ""));
		return popisky;
	}

	private RadekTabulky getPrazdnyRadekTabulky(int pocetSloupcu) {
		RadekTabulky radek = new RadekTabulky();
		FormatBunky formatBunky = new FormatBunky();
		for (int i = 0; i < pocetSloupcu; i++) {
			radek.addBunka(BunkaTabulky.bunka("", (CellStyle) formatBunky));
		}
		return radek;
	}

	private List<RadekTabulky> vytvorTeloSeznamu(List<?> radky, List<FormatSloupce> sloupce, Properties lng,
			Locale locale) {
		List<RadekTabulky> radkyTabulky = new ArrayList<RadekTabulky>();

		Map<String, NumberFormat> formatyCisel = getFormatyCisel(sloupce, locale);
		Map<String, DateFormat> formatyDatumu = getFormatyDatumu(sloupce, locale);

		CellStyle[] styly = vytvorStyly(sloupce);
		Method[] metody = zjistiMetody(radky.get(0), sloupce);

		BunkaTabulky bunka = null;

		try {
			for (Object radek : radky) {
				RadekTabulky rt = new RadekTabulky();
				int poradiSloupce = 0;
				for (FormatSloupce sloupec : sloupce) {
					Object hodnotaSloupce = metody[poradiSloupce].invoke(radek, new Object[0]);

					bunka = BunkaTabulky.bunka(getHodnotaSloupceAsString(hodnotaSloupce, sloupec, lng,
							formatyCisel.get(sloupec.getMaskaCislo()), formatyDatumu.get(sloupec.getMaskaDatum())),
							styly[poradiSloupce]);

					poradiSloupce++;
					rt.addBunka(bunka);
				}
				radkyTabulky.add(rt);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return radkyTabulky;
	}

	private String getHodnotaSloupceAsString(Object hodnota, FormatSloupce popisovac, Properties lng,
			NumberFormat numberFormat, DateFormat dateFormat) {
		String text;
		if (hodnota == null) {
			return "";
		}

		if (hodnota instanceof Boolean && popisovac.getSubstituteHodnota() == null) {
			text = lng.getProperty(((Boolean) hodnota).booleanValue() ? "ano" : "ne", hodnota.toString());
		} else if (popisovac.getSubstituteHodnota() != null) {
			text = lng.getProperty(String.valueOf(popisovac.getSubstituteHodnota()) + hodnota, hodnota.toString());
		} else if (hodnota instanceof Konstanty.IVycet) {
			text = ((Konstanty.IVycet) hodnota).getPopisek();
		} else {

			text = formatujHodnotu(hodnota, numberFormat, dateFormat);
		}
		return text;
	}

	@SuppressWarnings("rawtypes")
	private String formatujHodnotu(Object hodnota, NumberFormat numberFormat, DateFormat dateFormat) {
		if (hodnota instanceof Number) {
			if (numberFormat != null) {
				return numberFormat.format(hodnota);
			}
		} else if (hodnota instanceof java.util.Date) {
			if (dateFormat != null)
				return dateFormat.format(hodnota);
		} else {
			if (hodnota instanceof Konstanty.IVycet)
				return ((Konstanty.IVycet) hodnota).getPopisek();
			if (hodnota instanceof Collection)
				return PokyCollections.collectionToString((Collection) hodnota, "");
		}
		return hodnota.toString();
	}

	private CellStyle[] vytvorStyly(List<FormatSloupce> popisovace) {
		CellStyle[] styly = new CellStyle[popisovace.size()];
		int poradi = 0;

		for (FormatSloupce sl : popisovace) {
			FormatBunky styl = new FormatBunky();
			styl.setWrapped(sl.isZalamovatHodnota());
			styl.setZarovnaniBunky(getZarovnaniInt(sl.getZarovnaniHodnota()));
			styly[poradi] = (CellStyle) styl;
			poradi++;
		}
		return styly;
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

	private Map<String, NumberFormat> getFormatyCisel(List<FormatSloupce> sloupce, Locale locale) {
		Map<String, NumberFormat> map = new HashMap<String, NumberFormat>();

		for (FormatSloupce sl : sloupce) {
			if (sl.getMaskaCislo() != null) {
				NumberFormat form = new DecimalFormat(sl.getMaskaCislo(), new DecimalFormatSymbols(locale));
				map.put(sl.getMaskaCislo(), form);
			}
		}
		return map;
	}

	private Map<String, DateFormat> getFormatyDatumu(List<FormatSloupce> sloupce, Locale locale) {
		Map<String, DateFormat> map = new HashMap<String, DateFormat>();

		for (FormatSloupce sl : sloupce) {
			if (sl.getMaskaDatum() != null) {
				DateFormat form = new SimpleDateFormat(sl.getMaskaDatum(), new DateFormatSymbols(locale));
				map.put(sl.getMaskaDatum(), form);
			}
		}
		return map;
	}

	private HlavickaTabulky vytvorZahlavi(List<FormatSloupce> sloupce, Properties lng) {
		HlavickaTabulky hlavicka = new HlavickaTabulky(sloupce.size());

		for (FormatSloupce sl : sloupce) {
			String head = lng.getProperty(sl.getNazevSloupceLng(), sl.getNazevSloupceLng());
			addTridenaHlavickaTabulky(sl, head, hlavicka);
		}

		return hlavicka;
	}

	private void addTridenaHlavickaTabulky(FormatSloupce popisovac, String textSloupce, HlavickaTabulky hlavicka) {
		addTridenaHlavickaTabulky(popisovac, textSloupce, hlavicka, 1, 1);
	}

	private void addTridenaHlavickaTabulky(FormatSloupce popisovac, String textSloupce, HlavickaTabulky hlavicka,
			int rowspan, int colspan) {
		FormatHlavicky hf = new FormatHlavicky();
		hf.setFontBunky(PDFFont.BOLD);
		hf.setWrapped(popisovac.isZalamovatHlavicka());
		hf.setZarovnaniBunky(getZarovnaniInt(popisovac.getZarovnaniHlavicka()));

		BunkaTabulky bunka = BunkaTabulky.hlavicka(textSloupce, hf);
		bunka.getBunka().setColspan(colspan);
		bunka.getBunka().setRowspan(rowspan);

		if (popisovac.isTrideni()) {
			hlavicka.addBunka(bunka, popisovac.getPoradiTrideni(), popisovac.getSmerTrideni().isAscending());
		} else {
			hlavicka.addBunka(bunka);
		}
	}

	private int getZarovnaniInt(FormatSloupce.Zarovnani zarovnani) {
		if (zarovnani == null || zarovnani == FormatSloupce.Zarovnani.VLEVO)
			return 0;
		if (zarovnani == FormatSloupce.Zarovnani.VPRAVO)
			return 2;
		if (zarovnani == FormatSloupce.Zarovnani.NASTRED) {
			return 1;
		}
		return 0;
	}
}