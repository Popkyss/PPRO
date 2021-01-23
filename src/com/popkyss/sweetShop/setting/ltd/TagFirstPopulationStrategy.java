package com.popkyss.sweetShop.setting.ltd;
import org.apache.log4j.Logger;


import net.sourceforge.stripes.config.DontAutoLoad;
import net.sourceforge.stripes.controller.StripesFilter;
import net.sourceforge.stripes.exception.StripesJspException;
import net.sourceforge.stripes.tag.DefaultPopulationStrategy;
import net.sourceforge.stripes.tag.InputTagSupport;


@DontAutoLoad
public class TagFirstPopulationStrategy
extends DefaultPopulationStrategy
{
	private static Logger log = Logger.getLogger(TagFirstPopulationStrategy.class);
	
	
	public TagFirstPopulationStrategy() {
		try {
			init(StripesFilter.getConfiguration());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} 
	}
	
	
	public Object getValue(InputTagSupport tag) throws StripesJspException {
		if (tag.hasErrors()) {
			return super.getValue(tag);
		}
		
		Object value = getValuesFromRequest(tag);
		
		
		if (value == null) {
			value = getValueFromTag(tag);
		}
		
		
		if (value == null) {
			value = Boolean.FALSE;
		}
		
		return value;
	}
}
