package com.popkyss.sweetShop.setting.ltd;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.servlet.jsp.JspException;
/*    */ import net.sourceforge.stripes.tag.HtmlTagSupport;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PovinnaPolozka
/*    */   extends HtmlTagSupport
/*    */ {
/*    */   private static final String DEFAULT_STYL = "color:#d1262f; font-weight:bold; vertical-align:top;";
/*    */   private static final String DEFAULT_VALUE = "*";
/*    */   private String value;
/*    */   
/*    */   public int doStartTag() throws JspException {
/* 26 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int doEndTag() throws JspException {
/*    */     try {
/* 33 */       getPageContext().getOut().append(getHtml());
/* 34 */     } catch (IOException e) {
/* 35 */       throw new RuntimeException(e);
/*    */     } 
/*    */     
/* 38 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getHtml() {
/* 43 */     StringBuilder sb = new StringBuilder(200);
/*    */     
/* 45 */     sb.append("<span ");
/* 46 */     if (getCssClass() == null) {
/* 47 */       sb.append("style=\"");
/* 48 */       sb.append("color:#d1262f; font-weight:bold; vertical-align:top;");
/* 49 */       sb.append("\" ");
/*    */     } else {
/* 51 */       sb.append("class=\"");
/* 52 */       sb.append(getCssClass());
/* 53 */       sb.append("\" ");
/*    */     } 
/* 55 */     if (getTitle() != null) {
/* 56 */       sb.append("title=\"");
/* 57 */       sb.append(getTitle());
/* 58 */       sb.append("\" ");
/*    */     } 
/* 60 */     sb.append(">");
/* 61 */     sb.append((getValue() == null) ? "*" : this.value);
/* 62 */     sb.append("</span>");
/*    */     
/* 64 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(String value) {
/* 69 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getValue() {
/* 74 */     return this.value;
/*    */   }
/*    */ }


/* Location:              /Users/janpokorny/Downloads/datex_j2ee (kopie).jar!/cz/datexhk/web/tags/PovinnaPolozka.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */