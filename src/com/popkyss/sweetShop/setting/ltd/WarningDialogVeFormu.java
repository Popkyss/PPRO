package com.popkyss.sweetShop.setting.ltd;
import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.popkyss.sweetShop.setting.ltd.dto.DialogData;

import net.sourceforge.stripes.tag.FormTag;
import net.sourceforge.stripes.tag.HtmlTagSupport;


public class WarningDialogVeFormu
extends HtmlTagSupport
{
	private static final String DEFAULT_NAZEV_PREPRAVKY = "dialogData";
	private DialogData dialogData;
	private String nazevPrepravky;
	private String idForm;
	
	public int doStartTag() throws JspException {
		return 0;
	}
	
	
	
	public int doEndTag() throws JspException {
		DialogData data = this.dialogData;
		String nazev = (getNazevPrepravky() == null) ? DEFAULT_NAZEV_PREPRAVKY : getNazevPrepravky();
		if (data.getZobrazit().booleanValue()) {
			try {
				getPageContext().getOut().append(getHtml(data, nazev));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		return 0;
	}
	
	
	protected String getHtml(DialogData data, String nazev) {
		StringBuilder sb = new StringBuilder(200);
		sb.append("<script>warningMessage(\"");
		sb.append(zjistiIdFormulare());
		sb.append("\",\"");
		sb.append(data.getMethodOk());
		sb.append("\", \"");
		sb.append(data.getMessage());
		sb.append("\", \"");
		sb.append(data.getMethodStorno());
		sb.append("\",\"");
		sb.append(nazev);
		sb.append("\", '");
		sb.append(data.getTyp());
		sb.append("');</script>");
		
		return sb.toString();
	}
	
	
	protected String zjistiIdFormulare() {
		if (this.idForm != null) {
			return this.idForm;
		}
		FormTag tag = (FormTag)getParentTag(FormTag.class);
		if (tag != null) {
			return tag.getId();
		}
		throw new RuntimeException("Nepodarilo se zjistit id formulare");
	}
	
	
	
	public DialogData getDialogData() {
		return this.dialogData;
	}
	
	
	public void setDialogData(DialogData dialogData) {
		this.dialogData = dialogData;
	}
	
	
	public String getNazevPrepravky() {
		return this.nazevPrepravky;
	}
	
	
	public void setNazevPrepravky(String nazevPrepravky) {
		this.nazevPrepravky = nazevPrepravky;
	}
	
	
	public void setIdForm(String idForm) {
		this.idForm = idForm;
	}
	
	
	public String getIdForm() {
		return this.idForm;
	}
}
