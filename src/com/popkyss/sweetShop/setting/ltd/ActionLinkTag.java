package com.popkyss.sweetShop.setting.ltd;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import net.sourceforge.stripes.exception.StripesJspException;
import net.sourceforge.stripes.tag.LinkTag;
import org.apache.log4j.Logger;


public class ActionLinkTag
extends LinkTag
{
	private static final Logger log = Logger.getLogger(ActionLinkTag.class);
	
	private Boolean test;
	
	private boolean zobrazit = false;
	
	
	public int doEndTag() throws JspException {
		try {
			set("href", buildUrl());
			if (lzeRenderovat()) {
				writeOpenTag(getPageContext().getOut(), "a");
			}
			
			String body = getBodyContentAsString();
			if (body == null || body.trim().length() == 0) {
				body = "&nbsp;";
			}
			if (lzeRenderovat() || (!lzeRenderovat() && this.zobrazit)) {
				getPageContext().getOut().write(body.trim());
			}
			if (lzeRenderovat()) {
				writeCloseTag(getPageContext().getOut(), "a");
			}
		} catch (IOException ioe) {
			log.error("IOException while writing output in ActionLinkTag", ioe);
			throw new StripesJspException("IOException while writing output in ActionLinkTag", ioe);
		}
		
		
		getAttributes().remove("href");
		clearParameters();
		return 6;
	}
	
	
	private boolean lzeRenderovat() {
		if (this.test == null || this.test.booleanValue()) {
			return true;
		}
		return false;
	}
	
	public boolean isNavysku() {
		return ((Boolean)getParameters().get("sestavaNaVysku")).booleanValue();
	}
	
	
	public void setNavysku(boolean navysku) {
		addParameter("sestavaNaVysku", Boolean.valueOf(navysku));
	}
	
	
	public boolean isStahovat() {
		return ((Boolean)getParameters().get("stahovatSoubor")).booleanValue();
	}
	
	
	public void setStahovat(boolean stahovat) {
		addParameter("stahovatSoubor", Boolean.valueOf(stahovat));
	}
	
	
	
	
	public void setTest(Boolean test) {
		this.test = test;
	}
	
	
	public Boolean isTest() {
		return this.test;
	}
	
	
	public void setZobrazit(boolean zobrazit) {
		this.zobrazit = zobrazit;
	}
	
	
	public boolean isZobrazit() {
		return this.zobrazit;
	}
}