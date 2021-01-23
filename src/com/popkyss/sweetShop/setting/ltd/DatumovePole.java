package com.popkyss.sweetShop.setting.ltd;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.popkyss.sweetShop.setting.StringUtils;

import net.sourceforge.stripes.tag.InputTextTag;


public class DatumovePole
extends InputTextTag
{
	private AtomicInteger poradi = new AtomicInteger(0);
	
	private Boolean showOtherMonths;
	
	private Boolean changeMonth;
	
	private Boolean changeYear;
	private Boolean showButtons;
	private String onCloseEvent;
	
	public int doEndInputTag() throws JspException {
		boolean idSet = trySetId();
		int tmp = super.doEndInputTag();
		
		try {
			JspWriter out = getPageContext().getOut();
			out.append(getHtml());
		} catch (IOException e) {
			throw new RuntimeException(e);
		} 
		
		if (idSet) {
			getAttributes().remove("id");
		}
		
		return tmp;
	}
	
	
	protected String getHtml() {
		return constructScript(toPlainBoolean(getShowOtherMonths(), true), toPlainBoolean(getChangeMonth(), false), 
				toPlainBoolean(getChangeYear(), false), toPlainBoolean(getShowButtons(), false), getOnCloseEvent());
	}
	
	
	protected boolean toPlainBoolean(Boolean value, boolean defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		return value.booleanValue();
	}
	
	
	protected String constructScript(boolean otherMonths, boolean chMonth, boolean chYear, boolean shButtons, String onClose) {
		StringBuilder sb = new StringBuilder(100);
		sb.append("<script type=\"text/javascript\">");
		sb.append("jQuery( \"#" + get("id") + "\" )." + getDialogMethodName() + "({ ");
		if (onClose != null && !onClose.isEmpty()) {
			sb.append("onClose: function(dateText, inst) {" + onClose + "}, ");
		}
		sb.append("showOtherMonths: " + otherMonths + ", ");
		sb.append("changeMonth: " + chMonth + ", ");
		sb.append("changeYear: " + chYear + ", ");
		sb.append("showButtonPanel: " + shButtons + " ");
		sb.append("});");
		sb.append("</script>");
		
		return sb.toString();
	}
	
	
	protected String getDialogMethodName() {
		return "datepicker";
	}
	
	
	
	protected boolean trySetId() {
		if (StringUtils.isEmpty(get("id"))) {
			String id = String.format("date%08x%04d", new Object[] { Integer.valueOf(hashCode()), Integer.valueOf(this.poradi.incrementAndGet()) });
			set("id", id);
			return true;
		} 
		return false;
	}
	
	
	public void setChangeYear(Boolean changeYear) {
		this.changeYear = changeYear;
	}
	
	
	public Boolean getChangeYear() {
		return this.changeYear;
	}
	
	
	public void setChangeMonth(Boolean changeMonth) {
		this.changeMonth = changeMonth;
	}
	
	
	public Boolean getChangeMonth() {
		return this.changeMonth;
	}
	
	
	public void setShowOtherMonths(Boolean showOtherMonths) {
		this.showOtherMonths = showOtherMonths;
	}
	
	
	public Boolean getShowOtherMonths() {
		return this.showOtherMonths;
	}
	
	
	public void setShowButtons(Boolean showButtons) {
		this.showButtons = showButtons;
	}
	
	
	public Boolean getShowButtons() {
		return this.showButtons;
	}
	
	
	public void setOnCloseEvent(String onCloseEvent) {
		this.onCloseEvent = onCloseEvent;
	}
	
	
	public String getOnCloseEvent() {
		return this.onCloseEvent;
	}
}