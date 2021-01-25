package com.popkyss.sweetShop.setting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
//import java.util.Random;

public final class StringUtils {
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	@Deprecated
	public static String stringToChar(String s, int size) {
		if (s != null) {
			int tempSize = s.length();
			if (tempSize <= size) {
				while (size > tempSize) {
					s = String.valueOf(s) + " ";
					tempSize++;
				}
			}
		}
		return s;
	}

	public static String rightFill(String text, int pocetZnaku) {
		if (text == null) {
			return null;
		}
		if (text.length() >= pocetZnaku) {
			return text;
		}

		char[] filler = new char[pocetZnaku - text.length()];
		Arrays.fill(filler, ' ');
		StringBuilder sb = new StringBuilder(text);
		sb.append(filler);

		return sb.toString();
	}

	@Deprecated
	public static String trimRight(String s) {
		if (s != null) {
			int from = s.length() - 1;
			for (int i = from; i > -1; i--) {
				if (s.substring(i, i + 1).equals(" ")) {
					s = s.substring(0, i);
				} else {
					return s;
				}
			}
		}
		return s;
	}

	public static String rightTrim(String text) {
		if (text == null) {
			return null;
		}
		int len = text.length();
		int start = 0;
		int off = 0;
		char[] val = text.toCharArray();
		while (start < len && val[off + len - 1] <= ' ') {
			len--;
		}
		return (start > 0 || len < text.length()) ? text.substring(start, len) : text;
	}

	public static String orizniNaDelku(String text, int pocetZnaku, boolean konciMezerou) {
		if (text == null) {
			return null;
		}

		String trimmed = text;
		if (text.length() > pocetZnaku) {
			trimmed = trimmed.substring(0, pocetZnaku);
		}
		if (!konciMezerou) {
			trimmed = rightTrim(trimmed);
		}

		return trimmed;
	}

//	public static String getNahodnyText(int pocetZnaku) {
//		int pocetSlov = Math.max(pocetZnaku / 5, 1);
//		LoremIpsum lipsum = new LoremIpsum();
//		Random r = new Random();
//		String text = lipsum.getWords(pocetSlov, r.nextInt(50));
//
//		return text;
//	}

	public static boolean isEmpty(String s) {
		if (s == null) {
			return true;
		}
		if (s.equals("")) {
			return true;
		}
		return false;
	}

	public static String tabToHtml(String s1, String s2, int size) {
		s1 = trimRight(s1);
		s2 = trimRight(s2);
		String s = s1;
		int pom = size - s1.length();
		if (size > 0) {
			for (int i = 0; i < pom; i++) {
				s = String.valueOf(s) + "&nbsp;";
			}
			s = String.valueOf(s) + s2;
		} else {
			s = String.valueOf(s) + " " + s2;
		}
		return trimRight(s);
	}

	public static String stringToNotNullChar(String s) {
		if (isEmpty(s)) {
			return " ";
		}
		return s;
	}

	public static String htmlFilter(String message) {
		return htmlFilterTrim(message, true);
	}

	public static String htmlFilterWithoutRtrim(String message) {
		return htmlFilterTrim(message, false);
	}

	public static String htmlFilterTrim(String message, boolean rtrim) {
		if (message == null) {
			return "";
		}

		if (message.trim().equals("") && message.length() > 0) {
			return " ";
		}
		char[] content = new char[message.length()];
		message.getChars(0, message.length(), content, 0);

		StringBuilder result = new StringBuilder(content.length + 50);
		for (int i = 0; i < content.length; i++) {
			switch (content[i]) {
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			case '&':
				result.append("&amp;");
				break;
			case '"':
				result.append("&quot;");
				break;
			case '\'':
				result.append("&#039;");
				break;
			case '~':
				result.append("&#126;");
				break;
			default:
				result.append(content[i]);
				break;
			}
		}
		if (rtrim) {
			return ("X" + result.toString()).trim().substring(1);
		}
		return result.toString();
	}

	public static String stringToHtml(String s) {
		String temp = htmlFilter(s);
		temp = temp.replaceAll("\n", "<br />");

		return temp;
	}

	public static String replaceKeysInString(String s, Map<String, String> properties) {
		String result = s;
		for (Map.Entry<String, String> e : properties.entrySet()) {
			result = result.replaceAll(e.getKey(), e.getValue());
		}
		return result;
	}

	public static String replaceKeysInSourceCode(File file, Map<String, String> properties) throws IOException {
		BufferedReader in = null;
		try {
			StringBuilder code = new StringBuilder();
			in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
			String str;
			while ((str = in.readLine()) != null) {
				code.append(replaceKeysInString(str, properties));
				code.append("\n");
			}
			return code.toString();
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	public static String decodeString(String text, String encoding) {
		byte[] bytes;
		try {
			bytes = text.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return new String(bytes);
	}

	public static boolean isInteger(String s) {
		if (isEmpty(s)) {
			return false;
		}
		try {
			Integer.valueOf(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getProperty(String key, T defaultValue, Properties props) {
		try {
			Object result;
			@SuppressWarnings("rawtypes")
			Class<T> clazz = (Class) defaultValue.getClass();

			String prop;
			if (props == null || (prop = props.getProperty(key)) == null) {
				return defaultValue;
			}

			if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
				result = Integer.valueOf(Integer.parseInt(prop));
			} else if (clazz.equals(byte.class) || clazz.equals(Byte.class)) {
				result = Byte.valueOf(Byte.parseByte(prop));
			} else if (clazz.equals(char.class) || clazz.equals(Character.class)) {
				result = Character.valueOf(prop.charAt(0));
			} else if (clazz.equals(short.class) || clazz.equals(Short.class)) {
				result = Short.valueOf(Short.parseShort(prop));
			} else if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
				result = Boolean.valueOf(Boolean.parseBoolean(prop));
			} else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
				result = Long.valueOf(Long.parseLong(prop));
			} else if (clazz.equals(float.class) || clazz.equals(Float.class)) {
				result = Float.valueOf(Float.parseFloat(prop));
			} else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
				result = Double.valueOf(Double.parseDouble(prop));
			} else if (clazz.equals(String[].class)) {

				String[] splitted = prop.split(" ");
				int vloz = 0;
				for (int i = 0; i < splitted.length; i++) {
					String s = splitted[i];
					if (s != null && !s.equals("") && !s.startsWith("#")) {

						splitted[vloz++] = s;
					}
				}
				result = Arrays.copyOf(splitted, vloz);
			} else {

				result = prop;
			}
			return (T) result;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static String escapeExcelSheetName(String text, String nahrada) {
		if (text == null) {
			return null;
		}
		return text.replaceAll("[\\[\\]\\\\\\/\\?\\*]", (nahrada == null) ? "" : nahrada);
	}

	public static String getSimpleClassName(Class<?> clazz) {
		if (clazz.getDeclaringClass() == null) {
			clazz = clazz.getSuperclass();
		}
		return clazz.getSimpleName();
	}

	public static String[] split(String input) {
		if (input == null) {
			return new String[0];
		}

		return input.trim().split("[\\s,]+");
	}

	public static String rightPad(String text, String mask) {
		return (String.valueOf(text) + mask).substring(0, mask.length());
	}

	public static String leftPad(String text, String mask) {
		return (String.valueOf(mask) + text).substring(text.length());
	}
}