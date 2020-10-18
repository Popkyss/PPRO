package com.popkyss.sweetShop.setting.ltd;

/* *     */ import java.awt.Color;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Random;

import com.popkyss.sweetShop.setting.DatexCollections;
import com.popkyss.sweetShop.setting.StringUtils;

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
/*     */ 
/*     */ 
/*     */ public class Functions
/*     */ {
/*     */   public static int xor(int a, int b) {
/*  28 */     return a ^ b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int min(int a, int b) {
/*  36 */     return Math.min(a, b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int max(int a, int b) {
/*  44 */     return Math.max(a, b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String encodeObject(Object o) {
/*  54 */     return Base64.encodeObject((Serializable)o, 26);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object decodeObject(String s) {
/*  64 */     byte[] bb = Base64.decode(s, 26);
/*  65 */     String encoded = Base64.encodeBytes(bb);
/*  66 */     return Base64.decodeToObject(encoded);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String encryptInt(int param) {
/*     */     try {
/*  78 */       return EncryptionUtils.zasifrujParametr(Integer.valueOf(param), int.class);
/*  79 */     } catch (Exception e) {
/*  80 */       return "";
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
/*     */   public static String encryptShort(short param) {
/*     */     try {
/*  93 */       return EncryptionUtils.zasifrujParametr(Short.valueOf(param), short.class);
/*  94 */     } catch (Exception e) {
/*  95 */       return "";
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
/*     */   public static String encryptChar(char param) {
/*     */     try {
/* 108 */       return EncryptionUtils.zasifrujParametr(Character.valueOf(param), char.class);
/* 109 */     } catch (Exception e) {
/* 110 */       return "";
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
/*     */   public static String encryptByte(byte param) {
/*     */     try {
/* 123 */       return EncryptionUtils.zasifrujParametr(Byte.valueOf(param), byte.class);
/* 124 */     } catch (Exception e) {
/* 125 */       return "";
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
/*     */   public static String encryptObject(Object param) {
/*     */     try {
/* 138 */       return EncryptionUtils.zasifrujParametr(param, Serializable.class);
/* 139 */     } catch (Exception e) {
/* 140 */       return "";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String sul(int delka) {
/* 151 */     StringBuilder sb = new StringBuilder(delka);
/*     */     
/* 153 */     Random r = new Random();
/* 154 */     for (int i = 0; i < delka; ) {
/* 155 */       int a = r.nextInt(96) + 32;
/* 156 */       if ((a >= 48 && a <= 57) || (a >= 65 && a <= 90) || (a >= 97 && a <= 122)) {
/* 157 */         sb.append((char)a);
/* 158 */         i++;
/*     */       } 
/*     */     } 
/* 161 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String encodeHtml(String retezec) {
/* 171 */     if (retezec == null) {
/* 172 */       return null;
/*     */     }
/* 174 */     return StringUtils.htmlFilter(retezec);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isDate(Object obj) {
/* 184 */     return obj instanceof java.util.Date;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isTimeStamp(Object obj) {
/* 194 */     return obj instanceof java.sql.Timestamp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNumber(Object obj) {
/* 204 */     return obj instanceof Number;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isList(Object obj) {
/* 214 */     return obj instanceof java.util.List;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getBarvaHexa(Color barva) {
/* 225 */     if (barva == null) {
/* 226 */       return null;
/*     */     }
/* 228 */     return String.format("#%06x", new Object[] { Integer.valueOf(barva.getRGB() & 0xFFFFFF) });
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
/*     */   public static String nahradEntry(String text) {
/* 240 */     if (text == null) {
/* 241 */       return null;
/*     */     }
/*     */     
/* 244 */     String tmp = text.replaceAll("\r\n", "\n");
/* 245 */     tmp = tmp.replaceAll("\n\r", "\n");
/* 246 */     tmp = tmp.replaceAll("\n", "<br />");
/* 247 */     return tmp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String collectionToString(Collection<?> collection) {
/* 258 */     return DatexCollections.collectionToString(collection, "", ", ");
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
/*     */   public static String collectionToString2(Collection<?> collection, String oddelovac) {
/* 270 */     return DatexCollections.collectionToString(collection, "", oddelovac);
/*     */   }
/*     */ }


/* Location:              /Users/janpokorny/Downloads/datex_j2ee (kopie).jar!/cz/datexhk/web/tags/Functions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */