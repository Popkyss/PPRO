package com.popkyss.sweetShop.setting.ltd;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import net.sourceforge.stripes.exception.StripesJspException;
/*     */ import net.sourceforge.stripes.localization.LocalizationUtility;
/*     */ import net.sourceforge.stripes.tag.InputOptionsCollectionTag;
/*     */ import net.sourceforge.stripes.util.ReflectUtil;
/*     */ import net.sourceforge.stripes.util.bean.BeanUtil;
/*     */ import net.sourceforge.stripes.util.bean.ExpressionException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DtxOptionEnumeration
/*     */   extends InputOptionsCollectionTag
/*     */ {
/*     */   private String className;
/*     */   private String zkratka;
/*     */   private String ordinals;
/*     */   
/*     */   public void setEnum(String name) {
/*  28 */     this.className = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getEnum() {
/*  33 */     return this.className;
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
/*     */   public int doStartTag() throws JspException {
/*  47 */     Class<Enum> clazz = null;
/*     */     try {
/*  49 */       clazz = ReflectUtil.findClass(this.className);
/*     */     }
/*  51 */     catch (Exception e) {
/*     */ 
/*     */       
/*     */       try {
/*  55 */         int last = this.className.lastIndexOf('.');
/*  56 */         if (last > 0) {
/*  57 */           String n2 = (new StringBuilder(this.className)).replace(last, last + 1, "$").toString();
/*  58 */           clazz = ReflectUtil.findClass(n2);
/*     */         }
/*     */       
/*     */       }
/*  62 */       catch (Exception e2) {
/*  63 */         throw new StripesJspException(
/*  64 */             "Could not process class [" + this.className + "]. Attribute 'enum' on " + 
/*  65 */             "tag options-enumeration must be the fully qualified name of a " + 
/*  66 */             "class which is a java 1.5 enum.", e);
/*     */       } 
/*     */     } 
/*     */     
/*  70 */     if (clazz == null || !clazz.isEnum()) {
/*  71 */       throw new StripesJspException(
/*  72 */           "The class name supplied, [" + this.className + "], does not appear to be " + 
/*  73 */           "a JDK1.5 enum class.");
/*     */     }
/*     */     
/*  76 */     Enum[] enums = clazz.getEnumConstants();
/*     */     
/*     */     try {
/*  79 */       Locale locale = getPageContext().getRequest().getLocale();
/*     */       
/*  81 */       DtxMultiSelectBox selectTag = (DtxMultiSelectBox)getParentTag(DtxMultiSelectBox.class);
/*  82 */       if (selectTag == null) {
/*  83 */         throw new StripesJspException(
/*  84 */             "Option tags must always be contained inside a DtxMultiSelectBox tag.");
/*     */       }
/*  86 */       boolean vse = true; byte b; int i; Enum[] arrayOfEnum;
/*  87 */       for (i = (arrayOfEnum = enums).length, b = 0; b < i; ) { Enum item = arrayOfEnum[b];
/*  88 */         Object label = null;
/*  89 */         String packageName = (clazz.getPackage() == null) ? "" : clazz.getPackage().getName();
/*  90 */         String simpleName = LocalizationUtility.getSimpleName(clazz);
/*     */ 
/*     */         
/*  93 */         label = LocalizationUtility.getLocalizedFieldName(String.valueOf(simpleName) + "." + item.name(), 
/*  94 */             packageName, 
/*  95 */             null, 
/*  96 */             locale);
/*  97 */         if (label == null) {
/*  98 */           if (getLabel() != null) {
/*  99 */             label = BeanUtil.getPropertyValue(getLabel(), item) + " (" + 
/* 100 */               BeanUtil.getPropertyValue(this.zkratka, item) + ")";
/*     */           } else {
/*     */             
/* 103 */             label = item.toString();
/*     */           } 
/*     */         }
/*     */         
/* 107 */         Object group = null;
/* 108 */         if (getGroup() != null) {
/* 109 */           group = BeanUtil.getPropertyValue(getGroup(), item);
/*     */         }
/* 111 */         if (drawOption(item.ordinal())) {
/* 112 */           addEntry(item, label, item, group);
/*     */           
/* 114 */           if (selectTag.isOptionSelected(item.name(), false)) {
/* 115 */             selectTag.getSelectedShortValues().add(BeanUtil.getPropertyValue(this.zkratka, item));
/* 116 */             selectTag.getSelectedLongValues().add(BeanUtil.getPropertyValue(getLabel(), item));
/*     */           } else {
/* 118 */             vse = false;
/*     */           } 
/*     */         }  b++; }
/*     */       
/* 122 */       if (vse) {
/* 123 */         selectTag.getSelectedShortValues().clear();
/* 124 */         String vseLabel = LocalizationUtility.getLocalizedFieldName("select.options.vse", null, null, locale);
/* 125 */         selectTag.getSelectedShortValues().add(vseLabel);
/*     */       }
/*     */     
/* 128 */     } catch (ExpressionException ee) {
/* 129 */       throw new StripesJspException("A problem occurred generating an options-enumeration. Most likely either the class [" + 
/* 130 */           getEnum() + "] is not an enum or, [" + 
/* 131 */           getLabel() + "] is not a valid property of the enum.", ee);
/*     */     } 
/*     */     
/* 134 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean drawOption(int ordinal) {
/* 144 */     if (this.ordinals == null || this.ordinals.isEmpty()) {
/* 145 */       return true;
/*     */     }
/* 147 */     String[] ords = this.ordinals.split(","); byte b; int i; String[] arrayOfString1;
/* 148 */     for (i = (arrayOfString1 = ords).length, b = 0; b < i; ) { String ord = arrayOfString1[b];
/* 149 */       int o = Integer.parseInt(ord);
/* 150 */       if (o == ordinal)
/* 151 */         return true; 
/*     */       b++; }
/*     */     
/* 154 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getZkratka() {
/* 159 */     return this.zkratka;
/*     */   }
/*     */   
/*     */   public void setZkratka(String zkratka) {
/* 163 */     this.zkratka = zkratka;
/*     */   }
/*     */   
/*     */   public String getOrdinals() {
/* 167 */     return this.ordinals;
/*     */   }
/*     */   
/*     */   public void setOrdinals(String ordinals) {
/* 171 */     this.ordinals = ordinals;
/*     */   }
/*     */ }


/* Location:              /Users/janpokorny/Downloads/datex_j2ee (kopie).jar!/cz/datexhk/web/tags/DtxOptionEnumeration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */