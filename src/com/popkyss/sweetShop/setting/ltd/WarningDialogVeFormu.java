package com.popkyss.sweetShop.setting.ltd;
/*     */ import java.io.IOException;

/*     */ import javax.servlet.jsp.JspException;

import com.popkyss.sweetShop.setting.ltd.dto.DialogData;

/*     */ import net.sourceforge.stripes.tag.FormTag;
/*     */ import net.sourceforge.stripes.tag.HtmlTagSupport;
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
/*     */ public class WarningDialogVeFormu
/*     */   extends HtmlTagSupport
/*     */ {
/*     */   private static final String DEFAULT_NAZEV_PREPRAVKY = "dialogData";
/*     */   private DialogData dialogData;
/*     */   private String nazevPrepravky;
/*     */   private String idForm;
/*     */   
/*     */   public int doStartTag() throws JspException {
/*  29 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int doEndTag() throws JspException {
/*  35 */     DialogData data = this.dialogData;
/*  36 */     String nazev = (getNazevPrepravky() == null) ? "dialogData" : getNazevPrepravky();
/*  37 */     if (data.getZobrazit().booleanValue()) {
/*     */       try {
/*  39 */         getPageContext().getOut().append(getHtml(data, nazev));
/*  40 */       } catch (IOException e) {
/*  41 */         throw new RuntimeException(e);
/*     */       } 
/*     */     }
/*     */     
/*  45 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getHtml(DialogData data, String nazev) {
/*  50 */     StringBuilder sb = new StringBuilder(200);
/*  51 */     sb.append("<script>warningMessage(\"");
/*  52 */     sb.append(zjistiIdFormulare());
/*  53 */     sb.append("\",\"");
/*  54 */     sb.append(data.getMethodOk());
/*  55 */     sb.append("\", \"");
/*  56 */     sb.append(data.getMessage());
/*  57 */     sb.append("\", \"");
/*  58 */     sb.append(data.getMethodStorno());
/*  59 */     sb.append("\",\"");
/*  60 */     sb.append(nazev);
/*  61 */     sb.append("\", '");
/*  62 */     sb.append(data.getTyp());
/*  63 */     sb.append("');</script>");
/*     */     
/*  65 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String zjistiIdFormulare() {
/*  70 */     if (this.idForm != null) {
/*  71 */       return this.idForm;
/*     */     }
/*  73 */     FormTag tag = (FormTag)getParentTag(FormTag.class);
/*  74 */     if (tag != null) {
/*  75 */       return tag.getId();
/*     */     }
/*  77 */     throw new RuntimeException("Nepodarilo se zjistit id formulare");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DialogData getDialogData() {
/*  83 */     return this.dialogData;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDialogData(DialogData dialogData) {
/*  88 */     this.dialogData = dialogData;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNazevPrepravky() {
/*  93 */     return this.nazevPrepravky;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNazevPrepravky(String nazevPrepravky) {
/*  98 */     this.nazevPrepravky = nazevPrepravky;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIdForm(String idForm) {
/* 103 */     this.idForm = idForm;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIdForm() {
/* 108 */     return this.idForm;
/*     */   }
/*     */ }


/* Location:              /Users/janpokorny/Downloads/datex_j2ee (kopie).jar!/cz/datexhk/web/tags/WarningDialogVeFormu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */