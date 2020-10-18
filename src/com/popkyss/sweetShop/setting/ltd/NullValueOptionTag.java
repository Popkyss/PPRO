package com.popkyss.sweetShop.setting.ltd;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.servlet.jsp.JspException;
/*    */ import net.sourceforge.stripes.exception.StripesJspException;
/*    */ import net.sourceforge.stripes.tag.InputOptionTag;
/*    */ import net.sourceforge.stripes.tag.InputSelectTag;
/*    */ import net.sourceforge.stripes.util.HtmlUtil;
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
/*    */ public class NullValueOptionTag
/*    */   extends InputOptionTag
/*    */ {
/*    */   private boolean renderlabel;
/*    */   
/*    */   public int doEndInputTag() throws JspException {
/*    */     Object actualValue;
/* 26 */     InputSelectTag selectTag = (InputSelectTag)getParentTag(InputSelectTag.class);
/* 27 */     if (selectTag == null) {
/* 28 */       throw new StripesJspException("Option tags must always be contained inside a select tag.");
/*    */     }
/*    */ 
/*    */     
/* 32 */     String actualLabel = getBodyContentAsString();
/* 33 */     if (actualLabel == null) {
/* 34 */       actualLabel = HtmlUtil.encode(getLabel());
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 39 */     if (getValue() == null && this.renderlabel) {
/* 40 */       actualValue = actualLabel;
/*    */     } else {
/* 42 */       actualValue = getValue();
/*    */     } 
/* 44 */     getAttributes().put("value", format(actualValue));
/*    */ 
/*    */     
/* 47 */     if (selectTag.isOptionSelected(actualValue, (getSelected() != null))) {
/* 48 */       getAttributes().put("selected", "selected");
/*    */     }
/*    */ 
/*    */     
/*    */     try {
/* 53 */       writeOpenTag(getPageContext().getOut(), "option");
/* 54 */       if (actualLabel != null) {
/* 55 */         getPageContext().getOut().write(actualLabel);
/*    */       }
/* 57 */       writeCloseTag(getPageContext().getOut(), "option");
/*    */ 
/*    */       
/* 60 */       getAttributes().remove("selected");
/* 61 */       getAttributes().remove("value");
/* 62 */     } catch (IOException ioe) {
/* 63 */       throw new JspException("IOException in NullValueOptionTag.doEndTag().", ioe);
/*    */     } 
/*    */     
/* 66 */     return 6;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRenderlabel(boolean renderLabel) {
/* 71 */     this.renderlabel = renderLabel;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRenderlabel() {
/* 76 */     return this.renderlabel;
/*    */   }
/*    */ }


/* Location:              /Users/janpokorny/Downloads/datex_j2ee (kopie).jar!/cz/datexhk/web/tags/NullValueOptionTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */