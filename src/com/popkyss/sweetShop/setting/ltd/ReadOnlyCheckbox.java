package com.popkyss.sweetShop.setting.ltd;
/*    */ 
/*    */ import net.sourceforge.stripes.exception.StripesJspException;
/*    */ import net.sourceforge.stripes.tag.InputCheckBoxTag;
/*    */ import net.sourceforge.stripes.tag.InputTagSupport;
/*    */ import net.sourceforge.stripes.tag.PopulationStrategy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReadOnlyCheckbox
/*    */   extends InputCheckBoxTag
/*    */ {
/* 15 */   private PopulationStrategy strat = (PopulationStrategy)new TagFirstPopulationStrategy();
/*    */ 
/*    */ 
/*    */   
/*    */   protected Object getOverrideValueOrValues() throws StripesJspException {
/* 20 */     return this.strat.getValue((InputTagSupport)this);
/*    */   }
/*    */ }


/* Location:              /Users/janpokorny/Downloads/datex_j2ee (kopie).jar!/cz/datexhk/web/tags/ReadOnlyCheckbox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */