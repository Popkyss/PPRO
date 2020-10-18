package com.popkyss.sweetShop.setting.ltd;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import net.sourceforge.stripes.exception.StripesJspException;
/*     */ import net.sourceforge.stripes.tag.LinkTag;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class ActionLinkTag
/*     */   extends LinkTag
/*     */ {
/*  22 */   private static final Logger log = Logger.getLogger(ActionLinkTag.class);
/*     */   
/*     */   private Boolean test;
/*     */   
/*     */   private boolean zobrazit = false;
/*     */ 
/*     */   
/*     */   public int doEndTag() throws JspException {
/*     */     try {
/*  31 */       set("href", buildUrl());
/*  32 */       if (lzeRenderovat()) {
/*  33 */         writeOpenTag(getPageContext().getOut(), "a");
/*     */       }
/*     */       
/*  36 */       String body = getBodyContentAsString();
/*  37 */       if (body == null || body.trim().length() == 0) {
/*  38 */         body = "&nbsp;";
/*     */       }
/*  40 */       if (lzeRenderovat() || (!lzeRenderovat() && this.zobrazit)) {
/*  41 */         getPageContext().getOut().write(body.trim());
/*     */       }
/*  43 */       if (lzeRenderovat()) {
/*  44 */         writeCloseTag(getPageContext().getOut(), "a");
/*     */       }
/*  46 */     } catch (IOException ioe) {
/*  47 */       log.error("IOException while writing output in ActionLinkTag", ioe);
/*  48 */       throw new StripesJspException("IOException while writing output in ActionLinkTag", ioe);
/*     */     } 
/*     */ 
/*     */     
/*  52 */     getAttributes().remove("href");
/*  53 */     clearParameters();
/*  54 */     return 6;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean lzeRenderovat() {
/*  59 */     if (this.test == null || this.test.booleanValue()) {
/*  60 */       return true;
/*     */     }
/*  62 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNavysku() {
/*  70 */     return ((Boolean)getParameters().get("sestavaNaVysku")).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNavysku(boolean navysku) {
/*  75 */     addParameter("sestavaNaVysku", Boolean.valueOf(navysku));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStahovat() {
/*  80 */     return ((Boolean)getParameters().get("stahovatSoubor")).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStahovat(boolean stahovat) {
/*  85 */     addParameter("stahovatSoubor", Boolean.valueOf(stahovat));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTest(Boolean test) {
/*  92 */     this.test = test;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean isTest() {
/*  97 */     return this.test;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setZobrazit(boolean zobrazit) {
/* 102 */     this.zobrazit = zobrazit;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isZobrazit() {
/* 107 */     return this.zobrazit;
/*     */   }
/*     */ }


/* Location:              /Users/janpokorny/Downloads/datex_j2ee (kopie).jar!/cz/datexhk/web/tags/ActionLinkTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */