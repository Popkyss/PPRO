package com.popkyss.sweetShop.setting.ltd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.log4j.Logger;

import net.sourceforge.stripes.util.Base64;



public class EncryptionUtils
{
	private static Logger log = Logger.getLogger(EncryptionUtils.class);
	
	
	private static final byte[] DEFAULT_HESLO = digestHeslo("serialVersionUID = -6551415357575809201L;");
	
	
	
	public static byte[] digestHeslo(String heslo) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(heslo.getBytes("utf-8"));
			byte[] bb = md.digest();
			for (int i = 0; i < 16; i++) {
				bb[i] = (byte)(bb[i] ^ bb[i + 16]);
			}
			
			byte[] res = new byte[16];
			System.arraycopy(bb, 0, res, 0, 16);
			
			return res;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	
	public static <T> T desifrujParametr(String input, byte[] heslo, Class<T> clazz) {
		byte[] data = Base64.decode(input, 24);
		byte[] desifrovano = desifrujData(data, heslo);
		
		T res = vratObjektZBajtu(desifrovano, clazz);
		return res;
	}
	
	
	
	public static <T> T desifrujParametr(String input, Class<T> clazz) {
		return desifrujParametr(input, DEFAULT_HESLO, clazz);
	}
	
	
	
	@SuppressWarnings("unchecked")
	private static <T> T vratObjektZBajtu(byte[] desifrovano, Class<T> clazz) {
		try {
			Object result;
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(desifrovano));
			
			
			if (clazz.equals(int.class)) {
				result = Integer.valueOf(in.readInt());
			} else if (clazz.equals(byte.class)) {
				result = Byte.valueOf(in.readByte());
			} else if (clazz.equals(char.class)) {
				result = Character.valueOf(in.readChar());
			} else if (clazz.equals(short.class)) {
				result = Short.valueOf(in.readShort());
			} else if (clazz.equals(boolean.class)) {
				result = Boolean.valueOf(in.readBoolean());
			} else if (clazz.equals(long.class)) {
				result = Long.valueOf(in.readLong());
			} else if (clazz.equals(float.class)) {
				result = Float.valueOf(in.readFloat());
			} else if (clazz.equals(double.class)) {
				result = Double.valueOf(in.readDouble());
			} else {
				result = in.readObject();
			} 
			
			in.close();
			
			return (T)result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	
	public static byte[] desifrujData(byte[] input, byte[] heslo) {
		try {
			SecretKeySpec key = new SecretKeySpec(heslo, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(2, key);
			
			byte[] output = cipher.doFinal(input);
			return output;
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			throw new RuntimeException(e);
		} 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static <T extends java.io.Serializable> String zasifrujParametr(Object parametr, byte[] heslo, Class<T> clazz) {
		byte[] bajtyParametru = vratBajtyObjektu(parametr, clazz);
		byte[] zasifrovano = zasifrujData(bajtyParametru, heslo);
		
		String res = Base64.encodeBytes(zasifrovano, 24);
		return res;
	}
	
	
	
	public static <T extends java.io.Serializable> String zasifrujParametr(Object parametr, Class<T> clazz) {
		return zasifrujParametr(parametr, DEFAULT_HESLO, clazz);
	}
	
	
	
	private static <T extends java.io.Serializable> byte[] vratBajtyObjektu(Object parametr, Class<T> clazz) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(baos);
			
			if (parametr == null) {
				out.writeObject(null);
			} else if (clazz.equals(int.class)) {
				out.writeInt(((Integer)parametr).intValue());
			} else if (clazz.equals(byte.class)) {
				out.writeByte(((Byte)parametr).byteValue());
			} else if (clazz.equals(char.class)) {
				out.writeChar(((Character)parametr).charValue());
			} else if (clazz.equals(short.class)) {
				out.writeShort(((Short)parametr).shortValue());
			} else if (clazz.equals(boolean.class)) {
				out.writeBoolean(((Boolean)parametr).booleanValue());
			} else if (clazz.equals(long.class)) {
				out.writeLong(((Long)parametr).longValue());
			} else if (clazz.equals(float.class)) {
				out.writeFloat(((Float)parametr).floatValue());
			} else if (clazz.equals(double.class)) {
				out.writeDouble(((Double)parametr).doubleValue());
			} else {
				out.writeObject(parametr);
			} 
			
			out.close();
			
			return baos.toByteArray();
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			throw new RuntimeException(e);
		} 
	}
	
	
	public static byte[] zasifrujData(byte[] input, byte[] heslo) {
		try {
			SecretKeySpec key = new SecretKeySpec(heslo, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(1, key);
			
			byte[] output = cipher.doFinal(input);
			return output;
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			throw new RuntimeException(e);
		} 
	}
	
	
	private static final Map<Character, Integer> CHARACTER_LUT = new HashMap<Character, Integer>();
	private static final Map<Integer, Character> BYTE_LUT = new HashMap<Integer, Character>(); static {
		for (int i = 0; i < 16; i++) {
			char c = Integer.toHexString(i).toLowerCase().charAt(0);
			CHARACTER_LUT.put(Character.valueOf(c), Integer.valueOf(i));
			BYTE_LUT.put(Integer.valueOf(i), Character.valueOf(c));
		} 
	}
	
	
	
	public static byte[] dekodujBase16(String input) {
		if (input == null) {
			return null;
		}
		if (input.length() % 2 != 0) {
			throw new RuntimeException("Delka vstupu musi byt suda");
		}
		input = input.toLowerCase();
		if (!input.matches("[0-9a-f]*")) {
			throw new RuntimeException("Vstup neni kodovan v base 16");
		}
		
		char[] chars = input.toCharArray();
		byte[] bytes = new byte[chars.length / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte)(((Integer)CHARACTER_LUT.get(Character.valueOf(chars[2 * i]))).intValue() << 4 | ((Integer)CHARACTER_LUT.get(Character.valueOf(chars[2 * i + 1]))).intValue() << 0);
		}
		
		return bytes;
	}
	
	
	
	public static String zakodujBase16(byte[] input) {
		if (input == null) {
			return null;
		}
		
		byte[] bytes = input;
		char[] chars = new char[bytes.length * 2];
		for (int i = 0; i < bytes.length; i++) {
			chars[2 * i] = ((Character)BYTE_LUT.get(Integer.valueOf((bytes[i] & 0xFF) >> 4))).charValue();
			chars[2 * i + 1] = ((Character)BYTE_LUT.get(Integer.valueOf(bytes[i] & 0xF))).charValue();
		} 
		
		return new String(chars);
	}
}

