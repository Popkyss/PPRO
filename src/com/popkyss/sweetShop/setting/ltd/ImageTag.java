package com.popkyss.sweetShop.setting.ltd;
/*    */ 
/*    */ import javax.servlet.jsp.JspException;
/*    */ import net.sourceforge.stripes.tag.LinkTagSupport;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImageTag
/*    */   extends LinkTagSupport
/*    */ {
/*    */   public int doStartTag() throws JspException {
/* 16 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int doEndTag() throws JspException {
/* 22 */     if (get("alt") == null) {
/* 23 */       set("alt", "");
/*    */     }
/* 25 */     set("src", buildUrl());
/* 26 */     writeSingletonTag(getPageContext().getOut(), "img");
/*    */ 
/*    */     
/* 29 */     getAttributes().remove("src");
/* 30 */     clearParameters();
/* 31 */     return 6;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAlt() {
/* 36 */     return get(" alt");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setAlt(String alt) {
/* 41 */     set("alt", alt);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getWidth() {
/* 46 */     return get(" width");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setWidth(String width) {
/* 51 */     set("width", width);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHeight() {
/* 56 */     return get(" height");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setHeight(String height) {
/* 61 */     set("height", height);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUsemap() {
/* 66 */     return get(" usemap");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setUsemap(String usemap) {
/* 71 */     set("usemap", usemap);
/*    */   }
/*    */ }


/* Location:              /Users/janpokorny/Downloads/datex_j2ee (kopie).jar!/cz/datexhk/web/tags/ImageTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */