package com.popkyss.sweetShop.setting.ltd;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import net.sourceforge.stripes.exception.StripesJspException;
import net.sourceforge.stripes.tag.InputOptionTag;
import net.sourceforge.stripes.tag.InputSelectTag;
import net.sourceforge.stripes.util.HtmlUtil;


public class NullValueOptionTag
extends InputOptionTag
{
	private boolean renderlabel;
	
	public int doEndInputTag() throws JspException {
		Object actualValue;
		InputSelectTag selectTag = (InputSelectTag)getParentTag(InputSelectTag.class);
		if (selectTag == null) {
			throw new StripesJspException("Option tags must always be contained inside a select tag.");
		}
		
		
		String actualLabel = getBodyContentAsString();
		if (actualLabel == null) {
			actualLabel = HtmlUtil.encode(getLabel());
		}
		
		
		
		if (getValue() == null && this.renderlabel) {
			actualValue = actualLabel;
		} else {
			actualValue = getValue();
		} 
		getAttributes().put("value", format(actualValue));
		
		
		if (selectTag.isOptionSelected(actualValue, (getSelected() != null))) {
			getAttributes().put("selected", "selected");
		}
		
		
		try {
			writeOpenTag(getPageContext().getOut(), "option");
			if (actualLabel != null) {
				getPageContext().getOut().write(actualLabel);
			}
			writeCloseTag(getPageContext().getOut(), "option");
			
			
			getAttributes().remove("selected");
			getAttributes().remove("value");
		} catch (IOException ioe) {
			throw new JspException("IOException in NullValueOptionTag.doEndTag().", ioe);
		} 
		
		return 6;
	}
	
	
	public void setRenderlabel(boolean renderLabel) {
		this.renderlabel = renderLabel;
	}
	
	
	public boolean isRenderlabel() {
		return this.renderlabel;
	}
}
