package com.popkyss.sweetShop.setting.ltd;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;

/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;

import com.popkyss.sweetShop.setting.DatexCollections;
import com.popkyss.sweetShop.setting.StringUtils;

/*     */ import net.sourceforge.stripes.localization.LocalizationUtility;
/*     */ import net.sourceforge.stripes.tag.InputSelectTag;
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
/*     */ public class DtxMultiSelectBox
/*     */   extends InputSelectTag
/*     */ {
/*     */   private Integer width;
/*  27 */   private String idDiv = null;
/*     */   private Boolean moznostVse;
/*  29 */   private List<Object> selectedShortValues = new ArrayList();
/*  30 */   private List<Object> selectedLongValues = new ArrayList();
/*     */ 
/*     */ 
/*     */   
/*     */   public int doStartInputTag() throws JspException {
/*  35 */     this.selectedShortValues.clear();
/*  36 */     this.selectedLongValues.clear();
/*  37 */     this.idDiv = getName();
/*  38 */     this.idDiv = String.valueOf(this.idDiv.replaceAll("\\.", "")) + "_div";
/*  39 */     setMultiple("true");
/*  40 */     setStyle("width:" + (this.width.intValue() - 7) + "px;");
/*  41 */     if (getCssClass() == null) {
/*  42 */       setCssClass("multiSelectBox");
/*     */     }
/*     */     
/*  45 */     writeToJsp("<div id=\"" + this.idDiv + "\" class=\"multiSelectDiv\" style=\"display:none;\">");
/*  46 */     int val = super.doStartInputTag();
/*     */     
/*  48 */     return val;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeOpenTag(JspWriter writer, String tag) throws JspException {
/*  54 */     boolean vse = Boolean.TRUE.equals(this.moznostVse);
/*  55 */     if (vse) {
/*     */       
/*  57 */       String size = getSize();
/*  58 */       if (StringUtils.isInteger(size)) {
/*  59 */         setSize(String.valueOf(Integer.parseInt(size) + 1));
/*     */       }
/*     */ 
/*     */       
/*  63 */       String onchange = (getOnchange() == null) ? "" : getOnchange();
/*  64 */       if (!onchange.contains("vyberVse(this);")) {
/*  65 */         setOnchange(String.valueOf(onchange) + " vyberVse(this);");
/*     */       }
/*     */     } 
/*     */     
/*  69 */     super.writeOpenTag(writer, tag);
/*     */     
/*  71 */     if (vse) {
/*     */       
/*  73 */       Locale locale = getPageContext().getRequest().getLocale();
/*     */       
/*  75 */       writeToJsp("<option value=\"\" id=\"idOptionMultiselectVse\">");
/*  76 */       writeToJsp(LocalizationUtility.getLocalizedFieldName("select.options.vse", null, null, locale));
/*  77 */       writeToJsp("</option>");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int doEndInputTag() throws JspException {
/*  84 */     int val = super.doEndInputTag();
/*  85 */     writeToJsp("</div>");
/*     */     
/*  87 */     String btnClass = "class=\"multiSelectButton\"";
/*  88 */     String onclick = "onclick=\"multiSelectBoxScript(this, '" + this.idDiv + "', " + (this.width.intValue() - 7) + ");\"";
/*  89 */     String value = "value=\"" + DatexCollections.collectionToString(this.selectedShortValues, "", " | ") + "\"";
/*  90 */     String title = "title=\"" + DatexCollections.collectionToString(this.selectedLongValues, "", "&#013;") + "\"";
/*     */     
/*  92 */     writeToJsp("<input type=\"button\" style=\"width:" + this.width + "px;\" " + btnClass + " " + onclick + " " + value + " " + title + " />");
/*     */     
/*  94 */     return val;
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeToJsp(String text) throws JspException {
/*     */     try {
/* 100 */       JspWriter writer = getPageContext().getOut();
/* 101 */       writer.print(text);
/* 102 */     } catch (IOException ioe) {
/* 103 */       throw new JspException("IOException encountered while writing multiselect box tag to the JspWriter.", ioe);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getWidth() {
/* 109 */     return this.width;
/*     */   }
/*     */   
/*     */   public void setWidth(Integer width) {
/* 113 */     this.width = width;
/*     */   }
/*     */   
/*     */   public Boolean getMoznostVse() {
/* 117 */     return this.moznostVse;
/*     */   }
/*     */   
/*     */   public void setMoznostVse(Boolean moznostVse) {
/* 121 */     this.moznostVse = moznostVse;
/*     */   }
/*     */   
/*     */   public List<Object> getSelectedShortValues() {
/* 125 */     return this.selectedShortValues;
/*     */   }
/*     */   
/*     */   public void setSelectedShortValues(List<Object> selectedShortValues) {
/* 129 */     this.selectedShortValues = selectedShortValues;
/*     */   }
/*     */   
/*     */   public List<Object> getSelectedLongValues() {
/* 133 */     return this.selectedLongValues;
/*     */   }
/*     */   
/*     */   public void setSelectedLongValues(List<Object> selectedLongValues) {
/* 137 */     this.selectedLongValues = selectedLongValues;
/*     */   }
/*     */ }


/* Location:              /Users/janpokorny/Downloads/datex_j2ee (kopie).jar!/cz/datexhk/web/tags/DtxMultiSelectBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */