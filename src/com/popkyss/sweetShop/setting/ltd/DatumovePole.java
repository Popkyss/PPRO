package com.popkyss.sweetShop.setting.ltd;
/*     */ import java.io.IOException;
/*     */ import java.util.concurrent.atomic.AtomicInteger;

/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;

import com.popkyss.sweetShop.setting.StringUtils;

/*     */ import net.sourceforge.stripes.tag.InputTextTag;
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
/*     */ public class DatumovePole
/*     */   extends InputTextTag
/*     */ {
/*  25 */   private AtomicInteger poradi = new AtomicInteger(0);
/*     */   
/*     */   private Boolean showOtherMonths;
/*     */   
/*     */   private Boolean changeMonth;
/*     */   
/*     */   private Boolean changeYear;
/*     */   private Boolean showButtons;
/*     */   private String onCloseEvent;
/*     */   
/*     */   public int doEndInputTag() throws JspException {
/*  36 */     boolean idSet = trySetId();
/*  37 */     int tmp = super.doEndInputTag();
/*     */     
/*     */     try {
/*  40 */       JspWriter out = getPageContext().getOut();
/*  41 */       out.append(getHtml());
/*  42 */     } catch (IOException e) {
/*  43 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/*  46 */     if (idSet) {
/*  47 */       getAttributes().remove("id");
/*     */     }
/*     */     
/*  50 */     return tmp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHtml() {
/*  59 */     return constructScript(toPlainBoolean(getShowOtherMonths(), true), toPlainBoolean(getChangeMonth(), false), 
/*  60 */         toPlainBoolean(getChangeYear(), false), toPlainBoolean(getShowButtons(), false), getOnCloseEvent());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean toPlainBoolean(Boolean value, boolean defaultValue) {
/*  68 */     if (value == null) {
/*  69 */       return defaultValue;
/*     */     }
/*  71 */     return value.booleanValue();
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
/*     */   protected String constructScript(boolean otherMonths, boolean chMonth, boolean chYear, boolean shButtons, String onClose) {
/*  86 */     StringBuilder sb = new StringBuilder(100);
/*  87 */     sb.append("<script type=\"text/javascript\">");
/*  88 */     sb.append("jQuery( \"#" + get("id") + "\" )." + getDialogMethodName() + "({ ");
/*  89 */     if (onClose != null && !onClose.isEmpty()) {
/*  90 */       sb.append("onClose: function(dateText, inst) {" + onClose + "}, ");
/*     */     }
/*  92 */     sb.append("showOtherMonths: " + otherMonths + ", ");
/*  93 */     sb.append("changeMonth: " + chMonth + ", ");
/*  94 */     sb.append("changeYear: " + chYear + ", ");
/*  95 */     sb.append("showButtonPanel: " + shButtons + " ");
/*  96 */     sb.append("});");
/*  97 */     sb.append("</script>");
/*     */     
/*  99 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDialogMethodName() {
/* 108 */     return "datepicker";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean trySetId() {
/* 117 */     if (StringUtils.isEmpty(get("id"))) {
/* 118 */       String id = String.format("date%08x%04d", new Object[] { Integer.valueOf(hashCode()), Integer.valueOf(this.poradi.incrementAndGet()) });
/* 119 */       set("id", id);
/* 120 */       return true;
/*     */     } 
/* 122 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChangeYear(Boolean changeYear) {
/* 127 */     this.changeYear = changeYear;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean getChangeYear() {
/* 132 */     return this.changeYear;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChangeMonth(Boolean changeMonth) {
/* 137 */     this.changeMonth = changeMonth;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean getChangeMonth() {
/* 142 */     return this.changeMonth;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setShowOtherMonths(Boolean showOtherMonths) {
/* 147 */     this.showOtherMonths = showOtherMonths;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean getShowOtherMonths() {
/* 152 */     return this.showOtherMonths;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setShowButtons(Boolean showButtons) {
/* 157 */     this.showButtons = showButtons;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean getShowButtons() {
/* 162 */     return this.showButtons;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOnCloseEvent(String onCloseEvent) {
/* 167 */     this.onCloseEvent = onCloseEvent;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getOnCloseEvent() {
/* 172 */     return this.onCloseEvent;
/*     */   }
/*     */ }


/* Location:              /Users/janpokorny/Downloads/datex_j2ee (kopie).jar!/cz/datexhk/web/tags/DatumovePole.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */