package com.popkyss.sweetShop.setting.ltd.dto;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.MessageFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DialogData
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private String methodOk;
/*     */   private String methodStorno;
/*     */   private String message;
/*     */   private Boolean potvrzeno;
/*     */   private ETypDialogovehoOkna typ;
/*     */   private Boolean zobrazit;
/*     */   
/*     */   public enum ETypDialogovehoOkna
/*     */   {
/*  25 */     INFO
/*     */     {
/*     */       public String toString()
/*     */       {
/*  29 */         return "i";
/*     */       }
/*     */     },
/*  32 */     WARNING
/*     */     {
/*     */       public String toString()
/*     */       {
/*  36 */         return "w";
/*     */       }
/*     */     },
/*  39 */     ERROR
/*     */     {
/*     */       public String toString()
/*     */       {
/*  43 */         return "e";
/*     */       }
/*     */     };
/*     */     
/*     */     public int getOrdinal() {
/*  48 */       return ordinal();
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
/*     */   public DialogData() {
/*  64 */     this.message = null;
/*  65 */     this.methodOk = "";
/*  66 */     this.methodStorno = "";
/*  67 */     this.potvrzeno = null;
/*  68 */     setZobrazit(Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMethodOk() {
/*  77 */     return this.methodOk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMethodStorno() {
/*  86 */     return this.methodStorno;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/*  95 */     return this.message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMessage(String message) {
/* 104 */     this.message = message;
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
/*     */   public void show(String metodaOK, String metodaStorno, String message, Object... params) {
/* 116 */     show(ETypDialogovehoOkna.WARNING, metodaOK, metodaStorno, message, params);
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
/*     */   public void show(ETypDialogovehoOkna typ, String metodaOK, String metodaStorno, String message, Object... params) {
/* 130 */     this.typ = typ;
/* 131 */     this.methodOk = metodaOK;
/* 132 */     this.methodStorno = metodaStorno;
/* 133 */     if (params.length > 0) {
/*     */       try {
/* 135 */         this.message = MessageFormat.format(message, params);
/* 136 */       } catch (IllegalArgumentException e) {
/* 137 */         this.message = message;
/*     */       } 
/*     */     } else {
/* 140 */       this.message = message;
/*     */     } 
/* 142 */     this.potvrzeno = null;
/* 143 */     this.zobrazit = Boolean.valueOf(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void hide() {
/* 152 */     this.message = null;
/* 153 */     this.methodOk = "";
/* 154 */     this.methodStorno = "";
/* 155 */     this.potvrzeno = null;
/* 156 */     this.zobrazit = Boolean.valueOf(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPotvrzeno(Boolean potvrzeno) {
/* 165 */     this.potvrzeno = potvrzeno;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean isPotvrzeno() {
/* 175 */     return this.potvrzeno;
/*     */   }
/*     */ 
/*     */   
/*     */   public ETypDialogovehoOkna getTyp() {
/* 180 */     return this.typ;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTyp(ETypDialogovehoOkna typ) {
/* 185 */     this.typ = typ;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setZobrazit(Boolean zobrazit) {
/* 190 */     this.zobrazit = zobrazit;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean getZobrazit() {
/* 195 */     return this.zobrazit;
/*     */   }
/*     */ }


/* Location:              /Users/janpokorny/Downloads/datex_j2ee (kopie).jar!/cz/datexhk/web/tags/dto/DialogData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */