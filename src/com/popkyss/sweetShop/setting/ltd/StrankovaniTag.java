package com.popkyss.sweetShop.setting.ltd;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import com.popkyss.sweetShop.setting.ASeznamAction;
import com.popkyss.sweetShop.setting.HtmlSelect;
import com.popkyss.sweetShop.setting.PropertyReader;
import com.popkyss.sweetShop.setting.StrankovanySeznam;

import net.sourceforge.stripes.tag.HtmlTagSupport;
import net.sourceforge.stripes.util.UrlBuilder;



public class StrankovaniTag
extends HtmlTagSupport {
	private ASeznamAction<?, ?> controller;
	
	private enum TypAkceStrankovani {
		PRVNI_STRANKA
		{
			public String getPageString(StrankovanySeznam<?> seznam) {
				return "1";
			}
			
			
			public String getAltText() {
				return PropertyReader.bundle("g.StrankovanySeznamPosuvnikAlt1");
			}
			
			
			public String getImageClass() {
				return "pagingHome";
			}
		},
		PREDCHOZI_STRANKA
		{
			public String getPageString(StrankovanySeznam<?> seznam) {
				return seznam.getPredchazejiciStrankaString();
			}
			
			
			public String getAltText() {
				return PropertyReader.bundle("g.StrankovanySeznamPosuvnikAlt2");
			}
			
			
			public String getImageClass() {
				return "pagingLeft";
			}
		},
		DALSI_STRANKA
		{
			public String getPageString(StrankovanySeznam<?> seznam) {
				return seznam.getNasledujiciStrankaString();
			}
			
			
			public String getAltText() {
				return PropertyReader.bundle("g.StrankovanySeznamPosuvnikAlt3");
			}
			
			
			public String getImageClass() {
				return "pagingRight";
			}
		},
		POSLEDNI_STRANKA
		{
			public String getPageString(StrankovanySeznam<?> seznam) {
				return seznam.getPosledniStrankaString();
			}
			
			
			public String getAltText() {
				return PropertyReader.bundle("g.StrankovanySeznamPosuvnikAlt4");
			}
			
			
			public String getImageClass() {
				return "pagingEnd";
			}
		};
		
		
		public abstract String getPageString(StrankovanySeznam<?> param1StrankovanySeznam);
		
		
		public abstract String getAltText();
		
		
		public abstract String getImageClass();
	}
	
	
	public int doEndTag() throws JspException {
		try {
			StrankovanySeznam<?> strankovani = this.controller.getSeznamBean().getStrankovanySeznam();
			if (strankovani != null) {
				writeNumPagesInfo(strankovani);
				writeNumRowsSelectBox(strankovani);
				writePagingNavigation(strankovani);
			} 
			
			return 6;
		} catch (IOException e) {
			throw new JspException("aaaa - spadlo to", e);
		} 
	}
	
	
	
	public int doStartTag() throws JspException {
		return 0;
	}
	
	
	private void writeNumPagesInfo(StrankovanySeznam<?> strankovani) throws IOException {
		StringBuilder html = new StringBuilder(200);
		html.append("\n<div class=\"tablePagingLeft\"> \n");
		html.append(String.valueOf(PropertyReader.bundle("g.StrankovanySeznamPolozekCelkem1")) + " ");
		html.append("<strong>" + strankovani.getCelkemRadku() + "</strong> ");
		html.append(String.valueOf(PropertyReader.bundle("g.StrankovanySeznamPolozekCelkem2")) + " ( ");
		html.append(String.valueOf(strankovani.getCelkemStranek()) + " " + PropertyReader.bundle("g.StrankovanySeznamPolozekCelkem3") + " )");
		html.append("</div>\n");
		
		getPageContext().getOut().write(html.toString());
	}
	
	
	private void writeNumRowsSelectBox(StrankovanySeznam<?> strankovani) throws IOException {
		StringBuilder html = new StringBuilder(500);
		HtmlSelect<String, String> hs = strankovani.getPoctyRadkuNaStrance();
		
		html.append("<div class=\"tablePagingRight\">");
		html.append(String.valueOf(PropertyReader.bundle("g.StrankovanySeznamZobrazitPolozek1")) + "\n");
		
		html.append("<select name=\"radkyNaStrance\" onchange=\"setRadkyNaStrance(this.options[this.selectedIndex].value);\" >\n");
		for (int i = 0; i < hs.size(); i++) {
			String selected = "";
			if (hs.isSelected(i)) {
				selected = String.valueOf(selected) + " selected=\"selected\" ";
			}
			if (!((String)hs.getKey(i)).equals("ALL")) {
				html.append("\t<option value=\"" + (String)hs.getKey(i) + "\"" + selected + ">");
				html.append(String.valueOf(hs.getValue(i)) + " " + PropertyReader.bundle("g.StrankovanySeznamRadky") + "</option> \n");
			} else {
				html.append("\t<option value=\"" + (String)hs.getKey(i) + "\"" + selected + ">");
				html.append(String.valueOf(PropertyReader.bundle("g.StrankovanySeznamRadkyAll")) + "</option> \n");
			} 
		} 
		html.append("</select> \n");
		html.append("</div> \n");
		
		getPageContext().getOut().write(html.toString());
	}
	
	
	private void writePagingNavigation(StrankovanySeznam<?> strankovani) throws JspException, IOException {
		if (strankovani.isExistPrvniStranka()) {
			writeImageLink(TypAkceStrankovani.PRVNI_STRANKA, strankovani);
		}
		if (strankovani.isExistPredchazejiciStranka())
			writeImageLink(TypAkceStrankovani.PREDCHOZI_STRANKA, strankovani); 
		int i;
		for (i = -3; i < 1; i++) {
			if (strankovani.isExistPredchazejiciStranka(i)) {
				writeLink(String.valueOf(strankovani.getStranka() + i - 1), String.valueOf(strankovani.getStranka() + i - 1), false, (String)null);
			}
		} 
		if (strankovani.getStranka() > 0) {
			getPageContext().getOut().write("<strong>[" + strankovani.getStranka() + "]</strong>&nbsp;&nbsp; \n");
		}
		for (i = 0; i < 4; i++) {
			if (strankovani.isExistNasledujiciStranka(i)) {
				writeLink(String.valueOf(strankovani.getStranka() + i + 1), String.valueOf(strankovani.getStranka() + i + 1), false, (String)null);
			}
		} 
		if (strankovani.isExistNasledujiciStranka()) {
			writeImageLink(TypAkceStrankovani.DALSI_STRANKA, strankovani);
		}
		if (strankovani.isExistPosledniStranka()) {
			writeImageLink(TypAkceStrankovani.POSLEDNI_STRANKA, strankovani);
		}
	}
	
	
	private void writeImageLink(TypAkceStrankovani typAkce, StrankovanySeznam<?> seznam) throws JspException {
		String body = "<img src=\"images/sys_spacer.gif\" alt=\"" + typAkce.getAltText() + "\" class=\"" + 
				typAkce.getImageClass() + "\" />";
		writeLink(typAkce.getPageString(seznam), body, true, typAkce.getAltText());
	}
	
	
	private void writeLink(String paramValue, String body, boolean imageLink, String title) throws JspException {
		try {
			String afterLink = imageLink ? " &nbsp;&nbsp; \n" : " &nbsp; \n";
			String href = getLinkReference("nastaveniStrankyAction", "nextPage", paramValue);
			set("href", href);
			setTitle(title);
			
			writeOpenTag(getPageContext().getOut(), "a");
			getPageContext().getOut().write(body);
			writeCloseTag(getPageContext().getOut(), "a");
			getPageContext().getOut().write(afterLink);
			
			getAttributes().remove("href");
			setTitle(null);
		} catch (IOException e) {
			throw new JspException("Problem pri vytvareni odkazu ve strankovani", e);
		} 
	}
	
	
	private String getLinkReference(String event, String paramName, String paramValue) {
		HttpServletRequest request = (HttpServletRequest)getPageContext().getRequest();
		HttpServletResponse response = (HttpServletResponse)getPageContext().getResponse();
		
		String actionUrl = getActionBeanUrl(this.controller.getClass());
		UrlBuilder builder = new UrlBuilder(this.pageContext.getRequest().getLocale(), actionUrl, false);
		if (event != null) {
			builder.setEvent(event);
		}
		
		builder.addParameter(paramName, new Object[] { paramValue });
		
		String url = builder.toString();
		String contextPath = request.getContextPath();
		if (contextPath.length() > 1) {
			url = String.valueOf(contextPath) + url;
		}
		
		return response.encodeURL(url);
	}
	
	
	public ASeznamAction<?, ?> getController() {
		return this.controller;
	}
	
	
	public void setController(ASeznamAction<?, ?> controller) {
		this.controller = controller;
	}
}
