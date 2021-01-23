package com.popkyss.sweetShop.setting.ltd;

import java.awt.Color;
import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class StavTag
extends SimpleTagSupport
{
	private Color barva;
	private String id;
	private String title;
	private String clazz;
	
	public void doTag() throws JspException {
		try {
			StringBuilder content = new StringBuilder();
			content.append("<span ");
			
			if (this.id != null) {
				content.append("id=\"");
				content.append(this.id);
				content.append("\" ");
			} 
			
			
			if (this.clazz != null) {
				content.append("class=\"");
				content.append(this.clazz);
				content.append("\" ");
			} 
			
			
			if (this.barva != null) {
				content.append("style=\"background-color:rgb(");
				content.append(String.valueOf(this.barva.getRed()) + ",");
				content.append(String.valueOf(this.barva.getGreen()) + ",");
				content.append(String.valueOf(this.barva.getBlue()) + "); ");
			} else {
				throw new IOException("Nelze vytvorit telo tagu, neni zadana barva");
			} 
			
			if (this.clazz == null) {
				
				content.append("border: 1px solid #505050; ");
				content.append("width: 11pt; ");
				content.append("height: 8pt; ");
				content.append("font-size: 4pt; ");
				content.append("font-weight: bold; ");
				content.append("margin-right: 4pt; ");
				content.append("padding: 4pt; ");
			} 
			content.append("\" ");
			
			
			if (this.title != null) {
				content.append("title=\"");
				content.append(this.title);
				content.append("\" ");
			} 
			content.append(">");
			
			
			content.append("&nbsp;&nbsp;&nbsp;&nbsp;");
			
			content.append("</span>");
			
			getJspContext().getOut().print(content.toString());
		} catch (IOException e) {
			throw new JspException("Chyba pri renderovani tagu StavTag", e);
		} 
	}
	
	
	public Color getBarva() {
		return this.barva;
	}
	
	
	public void setBarva(Color barva) {
		this.barva = barva;
	}
	
	
	public String getTitle() {
		return this.title;
	}
	
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getId() {
		return this.id;
	}
	
	
	public String getClazz() {
		return this.clazz;
	}
	
	
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
}


