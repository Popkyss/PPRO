package com.popkyss.sweetShop.setting.ltd;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import net.sourceforge.stripes.tag.HtmlTagSupport;
/*     */ import net.sourceforge.stripes.util.HtmlUtil;
/*     */ import net.sourceforge.stripes.util.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LongText
/*     */   extends HtmlTagSupport
/*     */ {
/*  20 */   private static final Log log = Log.getInstance(LongText.class);
/*     */   
/*     */   private int maxTextLength;
/*     */   
/*     */   private Object value;
/*     */   
/*     */   private Boolean useTitle;
/*     */   
/*     */   public int doStartTag() throws JspException {
/*  29 */     Object val = this.value;
/*  30 */     boolean title = (this.useTitle == null) ? true : this.useTitle.booleanValue();
/*  31 */     if (val != null) {
/*  32 */       String s = getSafeStringValue(val);
/*  33 */       if (s.length() > this.maxTextLength && title) {
/*  34 */         setTitle(s);
/*  35 */         writeOpenTag(this.pageContext.getOut(), "span");
/*     */       } 
/*     */     } 
/*     */     
/*  39 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int doEndTag() throws JspException {
/*  45 */     Object val = this.value;
/*  46 */     boolean title = (this.useTitle == null) ? true : this.useTitle.booleanValue();
/*  47 */     if (val != null) {
/*  48 */       String s = getSafeStringValue(val);
/*  49 */       if (s.length() > this.maxTextLength) {
/*  50 */         s = String.valueOf(s.substring(0, this.maxTextLength - 3)) + "...";
/*  51 */         writeTextContent(s);
/*  52 */         if (title) {
/*  53 */           writeCloseTag(this.pageContext.getOut(), "span");
/*     */         }
/*     */       } else {
/*  56 */         writeTextContent(s);
/*     */       } 
/*     */     } 
/*  59 */     release();
/*     */     
/*  61 */     return 6;
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeTextContent(String text) throws JspException {
/*     */     try {
/*  67 */       this.pageContext.getOut().print(text);
/*  68 */     } catch (IOException e) {
/*  69 */       JspException jspe = new JspException("IOException encountered while writing value '" + text + "'.", e);
/*  70 */       log.warn((Throwable)jspe, new Object[0]);
/*  71 */       throw jspe;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getSafeStringValue(Object o) {
/*  78 */     String value = HtmlUtil.encode(o.toString());
/*  79 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/*  85 */     super.release();
/*  86 */     this.useTitle = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxTextLength() {
/*  91 */     return this.maxTextLength;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaxTextLength(int maxTextLength) {
/*  96 */     this.maxTextLength = maxTextLength;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValue() {
/* 101 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValue(Object value) {
/* 106 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUseTitle(Boolean useTitle) {
/* 111 */     this.useTitle = useTitle;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean getUseTitle() {
/* 116 */     return this.useTitle;
/*     */   }
/*     */ }


/* Location:              /Users/janpokorny/Downloads/datex_j2ee (kopie).jar!/cz/datexhk/web/tags/LongText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */