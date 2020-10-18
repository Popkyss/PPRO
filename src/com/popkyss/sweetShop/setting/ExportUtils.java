package com.popkyss.sweetShop.setting;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

public class ExportUtils {
	public static FormatSloupce getFormatSloupce(Class<?> dtoClass, Sloupec sloupec) {
		String attributeName = sloupec.getSloupec();
		Class<?> attributeType = ReflectionUtils.getAttributeClass(dtoClass, attributeName);
		String attributeMask = getAttributeMask(dtoClass, attributeName);
		FormatSloupce form = new FormatSloupce(attributeType);

		if (MathUtils.isTridaReprezentujeCislo(attributeType)) {

			if (Double.class.isAssignableFrom(attributeType) || double.class.isAssignableFrom(attributeType)
					|| Float.class.isAssignableFrom(attributeType) || float.class.isAssignableFrom(attributeType)
					|| BigDecimal.class.isAssignableFrom(attributeType)) {
				form.setMaskaCislo((attributeMask == null) ? "#0.000" : attributeMask);
			} else {

				form.setMaskaCislo((attributeMask == null) ? "#0" : attributeMask);
			}
			form.setZarovnaniHodnota(FormatSloupce.Zarovnani.VPRAVO);
		} else if (Date.class.isAssignableFrom(attributeType)) {
			form.setMaskaDatum((attributeMask == null) ? "dd.MM.yyyy HH:mm" : attributeMask);
			form.setZarovnaniHodnota(FormatSloupce.Zarovnani.VLEVO);
		} else if (Boolean.class.isAssignableFrom(attributeType) || boolean.class.isAssignableFrom(attributeType)) {
			form.setZarovnaniHodnota(FormatSloupce.Zarovnani.NASTRED);
		} else {

			form.setZarovnaniHodnota(FormatSloupce.Zarovnani.VLEVO);
			form.setZalamovatHodnota(true);
		}
		form.setNazevSloupce(attributeName);
		form.setNazevSloupceLng("head." + attributeName);
		form.setZarovnaniHlavicka(FormatSloupce.Zarovnani.NASTRED);
		form.setZalamovatHlavicka(true);

		form.setPoradiTrideni(sloupec.getPoradiTrideni());
		form.setSmerTrideni(
				sloupec.isAscending() ? FormatSloupce.SmerTrideni.VZESTUPNE : FormatSloupce.SmerTrideni.SESTUPNE);
		form.setTrideni(sloupec.isTrideni());

		return form;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static String getAttributeMask(Class<?> dtoClass, String attributeName) {
		try {
			Class<?> tmpClass = dtoClass;
			do {
				Field f;
				try {
					f = tmpClass.getDeclaredField(attributeName);
				} catch (NoSuchFieldException e) {
					f = null;
				}

				if (f != null && f.isAnnotationPresent((Class) ExportPattern.class)) {
					String pattern = ((ExportPattern) f.<ExportPattern>getAnnotation(ExportPattern.class)).pattern();
					return StringUtils.isEmpty(pattern) ? null : pattern;
				}

				tmpClass = tmpClass.getSuperclass();
			} while (tmpClass != null);

			return null;
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	public static KriteriumSestavy getKriterium(Field pole, String cestaDoLngZaklad, Object obj) {
		String cestaDoLng;
		KriteriumSestavy kr;
		KriteriumFiltru kf = pole.<KriteriumFiltru>getAnnotation(KriteriumFiltru.class);
		ReflectionUtils.makeFieldAccesible(pole);

		if (StringUtils.isEmpty(kf.cestaDoLng())) {
			cestaDoLng = String.valueOf(cestaDoLngZaklad) + pole.getName();
		} else {
			cestaDoLng = kf.cestaDoLng();
		}

		Object hodnotaPole = ReflectionUtils.getFieldValue(obj, pole);

		String oddelovac = kf.expandovaniSeznamu().getOddelovacKriterii();
		if (Date.class.isAssignableFrom(pole.getType())) {
			kr = new KriteriumSestavy(cestaDoLng, oddelovac, hodnotaPole, null, kf.formatDatum());
		} else {
			kr = new KriteriumSestavy(cestaDoLng, oddelovac, hodnotaPole, null, null);
		}
		kr.setExpandList(kf.expandovaniSeznamu());

		return kr;
	}
}