package com.popkyss.sweetShop.setting;

import java.math.BigDecimal;
import java.math.MathContext;

public class MathUtils {
	private static final Class<?>[] PRIMITIVNI_TYPY = new Class[] { byte.class, short.class, int.class, long.class,
			float.class, double.class };

	public static BigDecimal getNahodneCislo(double min, double max, boolean desetinne) {
		double a = Math.random() * (max - min) + min;
		if (desetinne) {
			return (new BigDecimal(a)).round(MathContext.DECIMAL32);
		}
		return new BigDecimal((new BigDecimal(a + 0.5D)).toBigInteger());
	}

	public static boolean isTridaReprezentujeCislo(Class<?> clazz) {
		if (Number.class.isAssignableFrom(clazz))
			return true;
		byte b;
		int i;
		Class<?>[] arrayOfClass;
		for (i = (arrayOfClass = PRIMITIVNI_TYPY).length, b = 0; b < i;) {
			Class<?> primitivni = arrayOfClass[b];
			if (primitivni.isAssignableFrom(clazz))
				return true;
			b++;
		}

		return false;
	}

	public static <T extends Comparable<T>> T max(T a, T b) {
		if (a == null) {
			return b;
		}
		if (b == null) {
			return a;
		}
		int res = a.compareTo(b);
		if (res >= 0) {
			return a;
		}
		return b;
	}

	public static <T extends Comparable<T>> T min(T a, T b) {
		if (a == null) {
			return b;
		}
		if (b == null) {
			return a;
		}
		int res = a.compareTo(b);
		if (res <= 0) {
			return a;
		}
		return b;
	}
}
