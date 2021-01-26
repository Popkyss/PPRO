package com.popkyss.sweetShop.setting.ltd;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.popkyss.sweetShop.setting.PokyCollections;
import com.popkyss.sweetShop.setting.StringUtils;

import net.sourceforge.stripes.localization.LocalizationUtility;
import net.sourceforge.stripes.tag.InputSelectTag;


public class DtxMultiSelectBox
extends InputSelectTag
{
	private Integer width;
	private String idDiv = null;
	private Boolean moznostVse;
	private List<Object> selectedShortValues = new ArrayList<Object>();
	private List<Object> selectedLongValues = new ArrayList<Object>();
	
	
	public int doStartInputTag() throws JspException {
		this.selectedShortValues.clear();
		this.selectedLongValues.clear();
		this.idDiv = getName();
		this.idDiv = String.valueOf(this.idDiv.replaceAll("\\.", "")) + "_div";
		setMultiple("true");
		setStyle("width:" + (this.width.intValue() - 7) + "px;");
		if (getCssClass() == null) {
			setCssClass("multiSelectBox");
		}
		
		writeToJsp("<div id=\"" + this.idDiv + "\" class=\"multiSelectDiv\" style=\"display:none;\">");
		int val = super.doStartInputTag();
		
		return val;
	}
	
	
	
	protected void writeOpenTag(JspWriter writer, String tag) throws JspException {
		boolean vse = Boolean.TRUE.equals(this.moznostVse);
		if (vse) {
			
			String size = getSize();
			if (StringUtils.isInteger(size)) {
				setSize(String.valueOf(Integer.parseInt(size) + 1));
			}
			
			
			String onchange = (getOnchange() == null) ? "" : getOnchange();
			if (!onchange.contains("vyberVse(this);")) {
				setOnchange(String.valueOf(onchange) + " vyberVse(this);");
			}
		} 
		
		super.writeOpenTag(writer, tag);
		
		if (vse) {
			
			Locale locale = getPageContext().getRequest().getLocale();
			
			writeToJsp("<option value=\"\" id=\"idOptionMultiselectVse\">");
			writeToJsp(LocalizationUtility.getLocalizedFieldName("select.options.vse", null, null, locale));
			writeToJsp("</option>");
		} 
	}
	
	
	
	public int doEndInputTag() throws JspException {
		int val = super.doEndInputTag();
		writeToJsp("</div>");
		
		String btnClass = "class=\"multiSelectButton\"";
		String onclick = "onclick=\"multiSelectBoxScript(this, '" + this.idDiv + "', " + (this.width.intValue() - 7) + ");\"";
		String value = "value=\"" + PokyCollections.collectionToString(this.selectedShortValues, "", " | ") + "\"";
		String title = "title=\"" + PokyCollections.collectionToString(this.selectedLongValues, "", "&#013;") + "\"";
		
		writeToJsp("<input type=\"button\" style=\"width:" + this.width + "px;\" " + btnClass + " " + onclick + " " + value + " " + title + " />");
		
		return val;
	}
	
	
	private void writeToJsp(String text) throws JspException {
		try {
			JspWriter writer = getPageContext().getOut();
			writer.print(text);
		} catch (IOException ioe) {
			throw new JspException("IOException encountered while writing multiselect box tag to the JspWriter.", ioe);
		} 
	}
	
	
	public Integer getWidth() {
		return this.width;
	}
	
	public void setWidth(Integer width) {
		this.width = width;
	}
	
	public Boolean getMoznostVse() {
		return this.moznostVse;
	}
	
	public void setMoznostVse(Boolean moznostVse) {
		this.moznostVse = moznostVse;
	}
	
	public List<Object> getSelectedShortValues() {
		return this.selectedShortValues;
	}
	
	public void setSelectedShortValues(List<Object> selectedShortValues) {
		this.selectedShortValues = selectedShortValues;
	}
	
	public List<Object> getSelectedLongValues() {
		return this.selectedLongValues;
	}
	
	public void setSelectedLongValues(List<Object> selectedLongValues) {
		this.selectedLongValues = selectedLongValues;
	}
}
