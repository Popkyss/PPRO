package com.popkyss.sweetShop.setting.ltd;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import net.sourceforge.stripes.tag.HtmlTagSupport;
import net.sourceforge.stripes.util.HtmlUtil;
import net.sourceforge.stripes.util.Log;



public class LongText
extends HtmlTagSupport
{
	private static final Log log = Log.getInstance(LongText.class);
	
	private int maxTextLength;
	
	private Object value;
	
	private Boolean useTitle;
	
	public int doStartTag() throws JspException {
		Object val = this.value;
		boolean title = (this.useTitle == null) ? true : this.useTitle.booleanValue();
		if (val != null) {
			String s = getSafeStringValue(val);
			if (s.length() > this.maxTextLength && title) {
				setTitle(s);
				writeOpenTag(this.pageContext.getOut(), "span");
			} 
		} 
		
		return 0;
	}
	
	
	
	public int doEndTag() throws JspException {
		Object val = this.value;
		boolean title = (this.useTitle == null) ? true : this.useTitle.booleanValue();
		if (val != null) {
			String s = getSafeStringValue(val);
			if (s.length() > this.maxTextLength) {
				s = String.valueOf(s.substring(0, this.maxTextLength - 3)) + "...";
				writeTextContent(s);
				if (title) {
					writeCloseTag(this.pageContext.getOut(), "span");
				}
			} else {
				writeTextContent(s);
			} 
		} 
		release();
		
		return 6;
	}
	
	
	private void writeTextContent(String text) throws JspException {
		try {
			this.pageContext.getOut().print(text);
		} catch (IOException e) {
			JspException jspe = new JspException("IOException encountered while writing value '" + text + "'.", e);
			log.warn((Throwable)jspe, new Object[0]);
			throw jspe;
		} 
	}
	
	
	
	private String getSafeStringValue(Object o) {
		String value = HtmlUtil.encode(o.toString());
		return value;
	}
	
	
	
	public void release() {
		super.release();
		this.useTitle = null;
	}
	
	
	public int getMaxTextLength() {
		return this.maxTextLength;
	}
	
	
	public void setMaxTextLength(int maxTextLength) {
		this.maxTextLength = maxTextLength;
	}
	
	
	public Object getValue() {
		return this.value;
	}
	
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	
	public void setUseTitle(Boolean useTitle) {
		this.useTitle = useTitle;
	}
	
	
	public Boolean getUseTitle() {
		return this.useTitle;
	}
}

