package com.popkyss.sweetShop.setting.ltd;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import org.apache.log4j.Logger;

import net.sourceforge.stripes.util.Base64;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncryptionUtils
/*     */ {
/*  27 */   private static Logger log = Logger.getLogger(EncryptionUtils.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   private static final byte[] DEFAULT_HESLO = digestHeslo("serialVersionUID = -6551415357575809201L;");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] digestHeslo(String heslo) {
/*     */     try {
/*  45 */       MessageDigest md = MessageDigest.getInstance("SHA-256");
/*  46 */       md.update(heslo.getBytes("utf-8"));
/*  47 */       byte[] bb = md.digest();
/*  48 */       for (int i = 0; i < 16; i++) {
/*  49 */         bb[i] = (byte)(bb[i] ^ bb[i + 16]);
/*     */       }
/*     */       
/*  52 */       byte[] res = new byte[16];
/*  53 */       System.arraycopy(bb, 0, res, 0, 16);
/*     */       
/*  55 */       return res;
/*  56 */     } catch (Exception e) {
/*  57 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T desifrujParametr(String input, byte[] heslo, Class<T> clazz) {
/*  74 */     byte[] data = Base64.decode(input, 24);
/*  75 */     byte[] desifrovano = desifrujData(data, heslo);
/*     */     
/*  77 */     T res = vratObjektZBajtu(desifrovano, clazz);
/*  78 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T desifrujParametr(String input, Class<T> clazz) {
/*  94 */     return desifrujParametr(input, DEFAULT_HESLO, clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> T vratObjektZBajtu(byte[] desifrovano, Class<T> clazz) {
/*     */     try {
/*     */       Object result;
/* 111 */       ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(desifrovano));
/*     */ 
/*     */       
/* 114 */       if (clazz.equals(int.class)) {
/* 115 */         result = Integer.valueOf(in.readInt());
/* 116 */       } else if (clazz.equals(byte.class)) {
/* 117 */         result = Byte.valueOf(in.readByte());
/* 118 */       } else if (clazz.equals(char.class)) {
/* 119 */         result = Character.valueOf(in.readChar());
/* 120 */       } else if (clazz.equals(short.class)) {
/* 121 */         result = Short.valueOf(in.readShort());
/* 122 */       } else if (clazz.equals(boolean.class)) {
/* 123 */         result = Boolean.valueOf(in.readBoolean());
/* 124 */       } else if (clazz.equals(long.class)) {
/* 125 */         result = Long.valueOf(in.readLong());
/* 126 */       } else if (clazz.equals(float.class)) {
/* 127 */         result = Float.valueOf(in.readFloat());
/* 128 */       } else if (clazz.equals(double.class)) {
/* 129 */         result = Double.valueOf(in.readDouble());
/*     */       } else {
/* 131 */         result = in.readObject();
/*     */       } 
/*     */       
/* 134 */       in.close();
/*     */       
/* 136 */       return (T)result;
/* 137 */     } catch (Exception e) {
/* 138 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] desifrujData(byte[] input, byte[] heslo) {
/*     */     try {
/* 152 */       SecretKeySpec key = new SecretKeySpec(heslo, "AES");
/* 153 */       Cipher cipher = Cipher.getInstance("AES");
/* 154 */       cipher.init(2, key);
/*     */       
/* 156 */       byte[] output = cipher.doFinal(input);
/* 157 */       return output;
/* 158 */     } catch (Exception e) {
/* 159 */       log.debug(e.getMessage(), e);
/* 160 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends java.io.Serializable> String zasifrujParametr(Object parametr, byte[] heslo, Class<T> clazz) {
/* 176 */     byte[] bajtyParametru = vratBajtyObjektu(parametr, clazz);
/* 177 */     byte[] zasifrovano = zasifrujData(bajtyParametru, heslo);
/*     */     
/* 179 */     String res = Base64.encodeBytes(zasifrovano, 24);
/* 180 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends java.io.Serializable> String zasifrujParametr(Object parametr, Class<T> clazz) {
/* 195 */     return zasifrujParametr(parametr, DEFAULT_HESLO, clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T extends java.io.Serializable> byte[] vratBajtyObjektu(Object parametr, Class<T> clazz) {
/*     */     try {
/* 210 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 211 */       ObjectOutputStream out = new ObjectOutputStream(baos);
/*     */       
/* 213 */       if (parametr == null) {
/* 214 */         out.writeObject(null);
/* 215 */       } else if (clazz.equals(int.class)) {
/* 216 */         out.writeInt(((Integer)parametr).intValue());
/* 217 */       } else if (clazz.equals(byte.class)) {
/* 218 */         out.writeByte(((Byte)parametr).byteValue());
/* 219 */       } else if (clazz.equals(char.class)) {
/* 220 */         out.writeChar(((Character)parametr).charValue());
/* 221 */       } else if (clazz.equals(short.class)) {
/* 222 */         out.writeShort(((Short)parametr).shortValue());
/* 223 */       } else if (clazz.equals(boolean.class)) {
/* 224 */         out.writeBoolean(((Boolean)parametr).booleanValue());
/* 225 */       } else if (clazz.equals(long.class)) {
/* 226 */         out.writeLong(((Long)parametr).longValue());
/* 227 */       } else if (clazz.equals(float.class)) {
/* 228 */         out.writeFloat(((Float)parametr).floatValue());
/* 229 */       } else if (clazz.equals(double.class)) {
/* 230 */         out.writeDouble(((Double)parametr).doubleValue());
/*     */       } else {
/* 232 */         out.writeObject(parametr);
/*     */       } 
/*     */       
/* 235 */       out.close();
/*     */       
/* 237 */       return baos.toByteArray();
/* 238 */     } catch (Exception e) {
/* 239 */       log.debug(e.getMessage(), e);
/* 240 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] zasifrujData(byte[] input, byte[] heslo) {
/*     */     try {
/* 254 */       SecretKeySpec key = new SecretKeySpec(heslo, "AES");
/* 255 */       Cipher cipher = Cipher.getInstance("AES");
/* 256 */       cipher.init(1, key);
/*     */       
/* 258 */       byte[] output = cipher.doFinal(input);
/* 259 */       return output;
/* 260 */     } catch (Exception e) {
/* 261 */       log.debug(e.getMessage(), e);
/* 262 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 273 */   private static final Map<Character, Integer> CHARACTER_LUT = new HashMap<Character, Integer>();
/* 274 */   private static final Map<Integer, Character> BYTE_LUT = new HashMap<Integer, Character>(); static {
/* 275 */     for (int i = 0; i < 16; i++) {
/* 276 */       char c = Integer.toHexString(i).toLowerCase().charAt(0);
/* 277 */       CHARACTER_LUT.put(Character.valueOf(c), Integer.valueOf(i));
/* 278 */       BYTE_LUT.put(Integer.valueOf(i), Character.valueOf(c));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] dekodujBase16(String input) {
/* 289 */     if (input == null) {
/* 290 */       return null;
/*     */     }
/* 292 */     if (input.length() % 2 != 0) {
/* 293 */       throw new RuntimeException("Delka vstupu musi byt suda");
/*     */     }
/* 295 */     input = input.toLowerCase();
/* 296 */     if (!input.matches("[0-9a-f]*")) {
/* 297 */       throw new RuntimeException("Vstup neni kodovan v base 16");
/*     */     }
/*     */     
/* 300 */     char[] chars = input.toCharArray();
/* 301 */     byte[] bytes = new byte[chars.length / 2];
/* 302 */     for (int i = 0; i < bytes.length; i++) {
/* 303 */       bytes[i] = (byte)(((Integer)CHARACTER_LUT.get(Character.valueOf(chars[2 * i]))).intValue() << 4 | ((Integer)CHARACTER_LUT.get(Character.valueOf(chars[2 * i + 1]))).intValue() << 0);
/*     */     }
/*     */     
/* 306 */     return bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String zakodujBase16(byte[] input) {
/* 316 */     if (input == null) {
/* 317 */       return null;
/*     */     }
/*     */     
/* 320 */     byte[] bytes = input;
/* 321 */     char[] chars = new char[bytes.length * 2];
/* 322 */     for (int i = 0; i < bytes.length; i++) {
/* 323 */       chars[2 * i] = ((Character)BYTE_LUT.get(Integer.valueOf((bytes[i] & 0xFF) >> 4))).charValue();
/* 324 */       chars[2 * i + 1] = ((Character)BYTE_LUT.get(Integer.valueOf(bytes[i] & 0xF))).charValue();
/*     */     } 
/*     */     
/* 327 */     return new String(chars);
/*     */   }
/*     */ }


/* Location:              /Users/janpokorny/Downloads/datex_j2ee (kopie).jar!/cz/datexhk/core/utils/EncryptionUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */