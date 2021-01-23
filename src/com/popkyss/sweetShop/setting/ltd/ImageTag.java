package com.popkyss.sweetShop.setting.ltd;

import javax.servlet.jsp.JspException;
import net.sourceforge.stripes.tag.LinkTagSupport;



public class ImageTag extends LinkTagSupport {
	public int doStartTag() throws JspException {
		return 0;
	}
	
		
	public int doEndTag() throws JspException {
		if (get("alt") == null) {
			set("alt", "");
		}
		set("src", buildUrl());
		writeSingletonTag(getPageContext().getOut(), "img");
		
		
		getAttributes().remove("src");
		clearParameters();
		return 6;
	}
	
	
	public String getAlt() {
		return get(" alt");
	}
	
	
	public void setAlt(String alt) {
		set("alt", alt);
	}
	
	
	public String getWidth() {
		return get(" width");
	}
	
	
	public void setWidth(String width) {
		set("width", width);
	}
	
	
	public String getHeight() {
		return get(" height");
	}
	
	
	public void setHeight(String height) {
		set("height", height);
	}
	
	
	public String getUsemap() {
		return get(" usemap");
	}
	
	
	public void setUsemap(String usemap) {
		set("usemap", usemap);
	}
}
