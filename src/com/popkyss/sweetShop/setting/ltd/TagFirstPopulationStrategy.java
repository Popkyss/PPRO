package com.popkyss.sweetShop.setting.ltd;
/*    */ import org.apache.log4j.Logger;

/*    */ 
/*    */ import net.sourceforge.stripes.config.DontAutoLoad;
/*    */ import net.sourceforge.stripes.controller.StripesFilter;
/*    */ import net.sourceforge.stripes.exception.StripesJspException;
/*    */ import net.sourceforge.stripes.tag.DefaultPopulationStrategy;
/*    */ import net.sourceforge.stripes.tag.InputTagSupport;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @DontAutoLoad
/*    */ public class TagFirstPopulationStrategy
/*    */   extends DefaultPopulationStrategy
/*    */ {
/* 21 */   private static Logger log = Logger.getLogger(TagFirstPopulationStrategy.class);
/*    */ 
/*    */   
/*    */   public TagFirstPopulationStrategy() {
/*    */     try {
/* 26 */       init(StripesFilter.getConfiguration());
/* 27 */     } catch (Exception e) {
/* 28 */       log.error(e.getMessage(), e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getValue(InputTagSupport tag) throws StripesJspException {
/* 39 */     if (tag.hasErrors()) {
/* 40 */       return super.getValue(tag);
/*    */     }
/*    */     
/* 43 */     Object value = getValuesFromRequest(tag);
/*    */ 
/*    */     
/* 46 */     if (value == null) {
/* 47 */       value = getValueFromTag(tag);
/*    */     }
/*    */ 
/*    */     
/* 51 */     if (value == null) {
/* 52 */       value = Boolean.FALSE;
/*    */     }
/*    */     
/* 55 */     return value;
/*    */   }
/*    */ }


/* Location:              /Users/janpokorny/Downloads/datex_j2ee (kopie).jar!/cz/datexhk/web/tags/TagFirstPopulationStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */