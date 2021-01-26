package com.popkyss.sweetShop.setting.ltd;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collection;
import java.util.Random;

import com.popkyss.sweetShop.setting.PokyCollections;
import com.popkyss.sweetShop.setting.StringUtils;

import net.sourceforge.stripes.util.Base64;


public class Functions
{
	public static int xor(int a, int b) {
		return a ^ b;
	}
	
	public static int min(int a, int b) {
		return Math.min(a, b);
	}
	
	
	public static int max(int a, int b) {
		return Math.max(a, b);
	}
	
	
	public static String encodeObject(Object o) {
		return Base64.encodeObject((Serializable)o, 26);
	}
	
	
	
	public static Object decodeObject(String s) {
		byte[] bb = Base64.decode(s, 26);
		String encoded = Base64.encodeBytes(bb);
		return Base64.decodeToObject(encoded);
	}
	
	
	public static String encryptInt(int param) {
		try {
			return EncryptionUtils.zasifrujParametr(Integer.valueOf(param), int.class);
		} catch (Exception e) {
			return "";
		} 
	}
	
	
	public static String encryptShort(short param) {
		try {
			return EncryptionUtils.zasifrujParametr(Short.valueOf(param), short.class);
		} catch (Exception e) {
			return "";
		} 
	}
	
	
	
	public static String encryptChar(char param) {
		try {
			return EncryptionUtils.zasifrujParametr(Character.valueOf(param), char.class);
		} catch (Exception e) {
			return "";
		} 
	}
	
	
	public static String encryptByte(byte param) {
		try {
			return EncryptionUtils.zasifrujParametr(Byte.valueOf(param), byte.class);
		} catch (Exception e) {
			return "";
		} 
	}
	
	
	public static String encryptObject(Object param) {
		try {
			return EncryptionUtils.zasifrujParametr(param, Serializable.class);
		} catch (Exception e) {
			return "";
		} 
	}
	
	
	public static String sul(int delka) {
		StringBuilder sb = new StringBuilder(delka);
		
		Random r = new Random();
		for (int i = 0; i < delka; ) {
			int a = r.nextInt(96) + 32;
			if ((a >= 48 && a <= 57) || (a >= 65 && a <= 90) || (a >= 97 && a <= 122)) {
				sb.append((char)a);
				i++;
			} 
		} 
		return sb.toString();
	}
	
	
	public static String encodeHtml(String retezec) {
		if (retezec == null) {
			return null;
		}
		return StringUtils.htmlFilter(retezec);
	}
	
	
	public static boolean isDate(Object obj) {
		return obj instanceof java.util.Date;
	}
	
	
	public static boolean isTimeStamp(Object obj) {
		return obj instanceof java.sql.Timestamp;
	}
	
	
	
	public static boolean isNumber(Object obj) {
		return obj instanceof Number;
	}
	
	
	public static boolean isList(Object obj) {
		return obj instanceof java.util.List;
	}
	
	
	public static String getBarvaHexa(Color barva) {
		if (barva == null) {
			return null;
		}
		return String.format("#%06x", new Object[] { Integer.valueOf(barva.getRGB() & 0xFFFFFF) });
	}
	
	
	public static String nahradEntry(String text) {
		if (text == null) {
			return null;
		}
		
		String tmp = text.replaceAll("\r\n", "\n");
		tmp = tmp.replaceAll("\n\r", "\n");
		tmp = tmp.replaceAll("\n", "<br />");
		return tmp;
	}
	
	
	public static String collectionToString(Collection<?> collection) {
		return PokyCollections.collectionToString(collection, "", ", ");
	}
	
	
	public static String collectionToString2(Collection<?> collection, String oddelovac) {
		return PokyCollections.collectionToString(collection, "", oddelovac);
	}
}

