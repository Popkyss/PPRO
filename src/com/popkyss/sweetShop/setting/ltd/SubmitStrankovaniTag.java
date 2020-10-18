package com.popkyss.sweetShop.setting.ltd;

import static com.popkyss.sweetShop.setting.PropertyReader.*;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.popkyss.sweetShop.setting.ASeznamAction;
import com.popkyss.sweetShop.setting.HtmlSelect;
import com.popkyss.sweetShop.setting.StrankovanySeznam;

import net.sourceforge.stripes.tag.HtmlTagSupport;

/**
 * Veskere akce na strankovaneho seznamu pujdou na kontroler metodou submit (form.submit)
 * Je treba mit spravne metody pro java skript a to tyto: 
 * Jinak cele funguje shodne jako StrankovaniTag:
 * <br>setRadkyNaStranceSubmit
 * <br>nextPageSubmit
 * 
 */
public class SubmitStrankovaniTag extends HtmlTagSupport {
	private static enum TypAkceStrankovani {
		PRVNI_STRANKA {
			@Override
			public String getPageString(StrankovanySeznam<?> seznam) {
				return "1";
			}

			@Override
			public String getAltText() {
				return bundle("g.StrankovanySeznamPosuvnikAlt1");
			}

			@Override
			public String getImageClass() {
				return "pagingHome";
			}
		},
		PREDCHOZI_STRANKA {
			@Override
			public String getPageString(StrankovanySeznam<?> seznam) {
				return seznam.getPredchazejiciStrankaString();
			}

			@Override
			public String getAltText() {
				return bundle("g.StrankovanySeznamPosuvnikAlt2");
			}

			@Override
			public String getImageClass() {
				return "pagingLeft";
			}
		},
		DALSI_STRANKA {
			@Override
			public String getPageString(StrankovanySeznam<?> seznam) {
				return seznam.getNasledujiciStrankaString();
			}

			@Override
			public String getAltText() {
				return bundle("g.StrankovanySeznamPosuvnikAlt3");
			}

			@Override
			public String getImageClass() {
				return "pagingRight";
			}
		},
		POSLEDNI_STRANKA {
			@Override
			public String getPageString(StrankovanySeznam<?> seznam) {
				return seznam.getPosledniStrankaString();
			}

			@Override
			public String getAltText() {
				return bundle("g.StrankovanySeznamPosuvnikAlt4");
			}

			@Override
			public String getImageClass() {
				return "pagingEnd";
			}
		};
		
		public abstract String getPageString(StrankovanySeznam<?> seznam);
		public abstract String getAltText();
		public abstract String getImageClass();
		
	}
	
	
	private ASeznamAction<?, ?> controller;
	
	
	@Override
	public int doEndTag() throws JspException {
		try {
			if(controller == null) {
				throw new JspException("atribut controller nesmi byt null");
			}
			StrankovanySeznam<?> strankovani = controller.getSeznamBean().getStrankovanySeznam();
			if (strankovani != null) {
				writeNumPagesInfo(strankovani);
				writeNumRowsSelectBox(strankovani);
				writePagingNavigation(strankovani);
			}
			release();

			return EVAL_PAGE;
		} catch (IOException e) {
			throw new JspException("chyba pri vykreslovani tagu", e);
		}
	}
	
	
	@Override
	public void release() {
		super.release();
		controller = null;
	}
	
	
	@Override
	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}
	

	private void writeNumPagesInfo(StrankovanySeznam<?> strankovani) throws IOException {
		StringBuilder html = new StringBuilder(200);
		html.append("\n<div class=\"tablePagingLeft\"> \n");
		html.append(bundle("g.StrankovanySeznamPolozekCelkem1") + " ");
		html.append("<strong>" + strankovani.getCelkemRadku() + "</strong> ");
		html.append(bundle("g.StrankovanySeznamPolozekCelkem2") + " ( ");
		html.append(strankovani.getCelkemStranek() + " " + bundle("g.StrankovanySeznamPolozekCelkem3") + " )");
		html.append("</div>\n");

		getPageContext().getOut().write(html.toString());
	}


	private void writeNumRowsSelectBox(StrankovanySeznam<?> strankovani) throws IOException {
		StringBuilder html = new StringBuilder(500);
		HtmlSelect<String, String> hs = strankovani.getPoctyRadkuNaStrance();

		html.append("<div class=\"tablePagingRight\">");
		html.append(bundle("g.StrankovanySeznamZobrazitPolozek1") + "\n");
		//html.append(hs.getHtml());
		//musi se vytvorit skriptova meotda setRadkyNaStranceSubmit - ktera odesle cely form
		html.append("<select name=\"radkyNaStrance\" onchange=\"setRadkyNaStranceSubmit(this, this.options[this.selectedIndex].value);\" >\n");
		for (int i = 0; i < hs.size(); i++) {
			String selected = "";
			if (hs.isSelected(i)) {
				selected += " selected=\"selected\" ";
			}
			if (!hs.getKey(i).equals(StrankovanySeznam.RADKY_ALL)) {
				html.append("\t<option value=\"" + hs.getKey(i) + "\"" + selected + ">");
				html.append(hs.getValue(i) + " " + bundle("g.StrankovanySeznamRadky") + "</option> \n");
			} else {
				html.append("\t<option value=\"" + hs.getKey(i) + "\"" + selected + ">");
				html.append(bundle("g.StrankovanySeznamRadkyAll") + "</option> \n");
			}
		}
		html.append("</select> \n");
		html.append("</div> \n");

		getPageContext().getOut().write(html.toString());
	}


	private void writePagingNavigation(StrankovanySeznam<?> strankovani) throws JspException, IOException {
		if(strankovani.isExistPrvniStranka()) {
			writeImageLink(TypAkceStrankovani.PRVNI_STRANKA, strankovani);
		}
		if(strankovani.isExistPredchazejiciStranka()) {
			writeImageLink(TypAkceStrankovani.PREDCHOZI_STRANKA, strankovani);
		}
		for(int i = -3; i < 1; i++) {
			if(strankovani.isExistPredchazejiciStranka(i)) {
				writeLink(String.valueOf(strankovani.getStranka() + i - 1), String.valueOf(strankovani.getStranka()+i-1), false, null);
			}
		}
		if(strankovani.getStranka() > 0) {
			getPageContext().getOut().write("<strong>[" + strankovani.getStranka() + "]</strong>&nbsp;&nbsp; \n");
		}
		for(int i = 0; i < 4; i++) {
			if(strankovani.isExistNasledujiciStranka(i)) {
				writeLink(String.valueOf(strankovani.getStranka() + i + 1), String.valueOf(strankovani.getStranka()+i+1), false, null);
			}
		}
		if(strankovani.isExistNasledujiciStranka()) {
			writeImageLink(TypAkceStrankovani.DALSI_STRANKA, strankovani);
		}
		if(strankovani.isExistPosledniStranka()) {
			writeImageLink(TypAkceStrankovani.POSLEDNI_STRANKA, strankovani);
		}
	}


	private void writeImageLink(TypAkceStrankovani typAkce, StrankovanySeznam<?> seznam) throws JspException {
		String body = "<img src=\"images/sys_spacer.gif\" alt=\"" + typAkce.getAltText() + "\" class=\""
				+ typAkce.getImageClass() + "\" />";
		writeLink(typAkce.getPageString(seznam), body, true, typAkce.getAltText());
	}


	private void writeLink(String paramValue, String body, boolean imageLink, String title) throws JspException {
		try {
			String afterLink = imageLink ? " &nbsp;&nbsp; \n" : " &nbsp; \n";
			//String href = getLinkReference("nastaveniStrankyAction", "nextPage", paramValue);
			set("href", "#");
			setTitle(title);
			setOnclick("nextPageSubmit(this , 'nastaveniStrankyAction', '"+paramValue+"');");
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

	
	public ASeznamAction<?, ?> getController() {
		return controller;
	}
	
	
	public void setController(ASeznamAction<?, ?> controller) {
		this.controller = controller;
	}
}
