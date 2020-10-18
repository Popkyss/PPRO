package com.popkyss.sweetShop.setting.ltd;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;

/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.tagext.SimpleTagSupport;
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
/*     */ public class StavTag
/*     */   extends SimpleTagSupport
/*     */ {
/*     */   private Color barva;
/*     */   private String id;
/*     */   private String title;
/*     */   private String clazz;
/*     */   
/*     */   public void doTag() throws JspException {
/*     */     try {
/*  28 */       StringBuilder content = new StringBuilder();
/*  29 */       content.append("<span ");
/*     */       
/*  31 */       if (this.id != null) {
/*  32 */         content.append("id=\"");
/*  33 */         content.append(this.id);
/*  34 */         content.append("\" ");
/*     */       } 
/*     */ 
/*     */       
/*  38 */       if (this.clazz != null) {
/*  39 */         content.append("class=\"");
/*  40 */         content.append(this.clazz);
/*  41 */         content.append("\" ");
/*     */       } 
/*     */ 
/*     */       
/*  45 */       if (this.barva != null) {
/*  46 */         content.append("style=\"background-color:rgb(");
/*  47 */         content.append(String.valueOf(this.barva.getRed()) + ",");
/*  48 */         content.append(String.valueOf(this.barva.getGreen()) + ",");
/*  49 */         content.append(String.valueOf(this.barva.getBlue()) + "); ");
/*     */       } else {
/*  51 */         throw new IOException("Nelze vytvorit telo tagu, neni zadana barva");
/*     */       } 
/*     */       
/*  54 */       if (this.clazz == null) {
/*     */         
/*  56 */         content.append("border: 1px solid #505050; ");
/*  57 */         content.append("width: 11pt; ");
/*  58 */         content.append("height: 8pt; ");
/*  59 */         content.append("font-size: 4pt; ");
/*  60 */         content.append("font-weight: bold; ");
/*  61 */         content.append("margin-right: 4pt; ");
/*  62 */         content.append("padding: 4pt; ");
/*     */       } 
/*  64 */       content.append("\" ");
/*     */ 
/*     */       
/*  67 */       if (this.title != null) {
/*  68 */         content.append("title=\"");
/*  69 */         content.append(this.title);
/*  70 */         content.append("\" ");
/*     */       } 
/*  72 */       content.append(">");
/*     */ 
/*     */       
/*  75 */       content.append("&nbsp;&nbsp;&nbsp;&nbsp;");
/*     */       
/*  77 */       content.append("</span>");
/*     */       
/*  79 */       getJspContext().getOut().print(content.toString());
/*  80 */     } catch (IOException e) {
/*  81 */       throw new JspException("Chyba pri renderovani tagu StavTag", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Color getBarva() {
/*  87 */     return this.barva;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBarva(Color barva) {
/*  92 */     this.barva = barva;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTitle() {
/*  97 */     return this.title;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTitle(String title) {
/* 102 */     this.title = title;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setId(String id) {
/* 107 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 112 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getClazz() {
/* 117 */     return this.clazz;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClazz(String clazz) {
/* 122 */     this.clazz = clazz;
/*     */   }
/*     */ }


/* Location:              /Users/janpokorny/Downloads/datex_j2ee (kopie).jar!/cz/datexhk/web/tags/StavTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */