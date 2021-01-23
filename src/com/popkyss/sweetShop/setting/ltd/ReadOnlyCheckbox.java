package com.popkyss.sweetShop.setting.ltd;

import net.sourceforge.stripes.exception.StripesJspException;
import net.sourceforge.stripes.tag.InputCheckBoxTag;
import net.sourceforge.stripes.tag.InputTagSupport;
import net.sourceforge.stripes.tag.PopulationStrategy;


public class ReadOnlyCheckbox
extends InputCheckBoxTag
{
	private PopulationStrategy strat = (PopulationStrategy)new TagFirstPopulationStrategy();
		
	protected Object getOverrideValueOrValues() throws StripesJspException {
		return this.strat.getValue((InputTagSupport)this);
	}
}
