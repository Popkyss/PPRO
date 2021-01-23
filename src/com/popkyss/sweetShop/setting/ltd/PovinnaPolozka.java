package com.popkyss.sweetShop.setting.ltd;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import net.sourceforge.stripes.tag.HtmlTagSupport;


public class PovinnaPolozka
extends HtmlTagSupport
{
	private static final String DEFAULT_STYL = "color:#d1262f; font-weight:bold; vertical-align:top;";
	private static final String DEFAULT_VALUE = "*";
	private String value;
	
	public int doStartTag() throws JspException {
		return 0;
	}
	
	
	
	public int doEndTag() throws JspException {
		try {
			getPageContext().getOut().append(getHtml());
		} catch (IOException e) {
			throw new RuntimeException(e);
		} 
		
		return 0;
	}
	
	
	protected String getHtml() {
		StringBuilder sb = new StringBuilder(200);
		
		sb.append("<span ");
		if (getCssClass() == null) {
			sb.append("style=\"");
			sb.append(DEFAULT_STYL);
			sb.append("\" ");
		} else {
			sb.append("class=\"");
			sb.append(getCssClass());
			sb.append("\" ");
		} 
		if (getTitle() != null) {
			sb.append("title=\"");
			sb.append(getTitle());
			sb.append("\" ");
		} 
		sb.append(">");
		sb.append((getValue() == null) ? DEFAULT_VALUE : this.value);
		sb.append("</span>");
		
		return sb.toString();
	}
	
	
	public void setValue(String value) {
		this.value = value;
	}
	
	
	public String getValue() {
		return this.value;
	}
}

