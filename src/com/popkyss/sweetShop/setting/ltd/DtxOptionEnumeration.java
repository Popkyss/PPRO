package com.popkyss.sweetShop.setting.ltd;

import java.util.Locale;
import javax.servlet.jsp.JspException;
import net.sourceforge.stripes.exception.StripesJspException;
import net.sourceforge.stripes.localization.LocalizationUtility;
import net.sourceforge.stripes.tag.InputOptionsCollectionTag;
import net.sourceforge.stripes.util.ReflectUtil;
import net.sourceforge.stripes.util.bean.BeanUtil;
import net.sourceforge.stripes.util.bean.ExpressionException;


public class DtxOptionEnumeration
extends InputOptionsCollectionTag
{
	private String className;
	private String zkratka;
	private String ordinals;
	
	public void setEnum(String name) {
		this.className = name;
	}
	
	
	public String getEnum() {
		return this.className;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public int doStartTag() throws JspException {
		Class<Enum> clazz = null;
		try {
			clazz = ReflectUtil.findClass(this.className);
		}
		catch (Exception e) {
			
			
			try {
				int last = this.className.lastIndexOf('.');
				if (last > 0) {
					String n2 = (new StringBuilder(this.className)).replace(last, last + 1, "$").toString();
					clazz = ReflectUtil.findClass(n2);
				}
				
			}
			catch (Exception e2) {
				throw new StripesJspException(
						"Could not process class [" + this.className + "]. Attribute 'enum' on " + 
								"tag options-enumeration must be the fully qualified name of a " + 
								"class which is a java 1.5 enum.", e);
			} 
		} 
		
		if (clazz == null || !clazz.isEnum()) {
			throw new StripesJspException(
					"The class name supplied, [" + this.className + "], does not appear to be " + 
					"a JDK1.5 enum class.");
		}
		
		Enum[] enums = clazz.getEnumConstants();
		
		try {
			Locale locale = getPageContext().getRequest().getLocale();
			
			DtxMultiSelectBox selectTag = (DtxMultiSelectBox)getParentTag(DtxMultiSelectBox.class);
			if (selectTag == null) {
				throw new StripesJspException(
						"Option tags must always be contained inside a DtxMultiSelectBox tag.");
			}
			boolean vse = true; byte b; int i; Enum[] arrayOfEnum;
			for (i = (arrayOfEnum = enums).length, b = 0; b < i; ) { Enum item = arrayOfEnum[b];
			Object label = null;
			String packageName = (clazz.getPackage() == null) ? "" : clazz.getPackage().getName();
			String simpleName = LocalizationUtility.getSimpleName(clazz);
			
			
			label = LocalizationUtility.getLocalizedFieldName(String.valueOf(simpleName) + "." + item.name(), 
					packageName, 
					null, 
					locale);
			if (label == null) {
				if (getLabel() != null) {
					label = BeanUtil.getPropertyValue(getLabel(), item) + " (" + 
							BeanUtil.getPropertyValue(this.zkratka, item) + ")";
				} else {
					
					label = item.toString();
				} 
			}
			
			Object group = null;
			if (getGroup() != null) {
				group = BeanUtil.getPropertyValue(getGroup(), item);
			}
			if (drawOption(item.ordinal())) {
				addEntry(item, label, item, group);
				
				if (selectTag.isOptionSelected(item.name(), false)) {
					selectTag.getSelectedShortValues().add(BeanUtil.getPropertyValue(this.zkratka, item));
					selectTag.getSelectedLongValues().add(BeanUtil.getPropertyValue(getLabel(), item));
				} else {
					vse = false;
				} 
			}  b++; }
			
			if (vse) {
				selectTag.getSelectedShortValues().clear();
				String vseLabel = LocalizationUtility.getLocalizedFieldName("select.options.vse", null, null, locale);
				selectTag.getSelectedShortValues().add(vseLabel);
			}
			
		} catch (ExpressionException ee) {
			throw new StripesJspException("A problem occurred generating an options-enumeration. Most likely either the class [" + 
					getEnum() + "] is not an enum or, [" + 
					getLabel() + "] is not a valid property of the enum.", ee);
		} 
		
		return 0;
	}
	
	
	private boolean drawOption(int ordinal) {
		if (this.ordinals == null || this.ordinals.isEmpty()) {
			return true;
		}
		String[] ords = this.ordinals.split(","); byte b; int i; String[] arrayOfString1;
		for (i = (arrayOfString1 = ords).length, b = 0; b < i; ) { String ord = arrayOfString1[b];
		int o = Integer.parseInt(ord);
		if (o == ordinal)
			return true; 
		b++; }
		
		return false;
	}
	
	
	public String getZkratka() {
		return this.zkratka;
	}
	
	public void setZkratka(String zkratka) {
		this.zkratka = zkratka;
	}
	
	public String getOrdinals() {
		return this.ordinals;
	}
	
	public void setOrdinals(String ordinals) {
		this.ordinals = ordinals;
	}
}
