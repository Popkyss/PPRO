package com.popkyss.sweetShop.setting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class LocalesBean implements Serializable {
	private static final long serialVersionUID = 9201223840913590634L;
	private Collection<LocaleBean> locales = new ArrayList<LocaleBean>();

	public LocalesBean() {
		byte b;
		int i;
		String[] arrayOfString;
		for (i = (arrayOfString = WebAppSettings.getSupportedLanguages()).length, b = 0; b < i;) {
			String language = arrayOfString[b];
			this.locales.add(new LocaleBean(language, language.toUpperCase()));

			b++;
		}

		this.selectedLocale = "cs";
	}

	private String selectedLocale;

	public Collection<LocaleBean> getLocales() {
		return this.locales;
	}

	public void setLocales(Collection<LocaleBean> locales) {
		this.locales = locales;
	}

	public String getSelectedLocale() {
		return this.selectedLocale;
	}

	public void setSelectedLocale(String selectedLocalel) {
		if (selectedLocalel != null) {
			this.selectedLocale = selectedLocalel;
		}
	}

	public String getHtml() {
		StringBuilder html = new StringBuilder(200);
		html.append(
				"<select id=\"language\" name=\"jazyky\" size=\"1\" onchange=\"setLocale(this.options[this.selectedIndex].value);\"> \n");

		for (LocaleBean lb : this.locales) {
			html.append("<option value=\"" + lb.getLocale() + "\"");
			if (this.selectedLocale.equals(lb.getLocale())) {
				html.append(" selected=\"selected\"");
			}
			html.append(">" + lb.getTitle() + "</option> \n");
		}
		html.append("</select> \n");

		return html.toString();
	}

	public static class LocaleBean implements Serializable {
		private static final long serialVersionUID = 2042537202305419757L;
		private String locale;
		private String title;

		public LocaleBean(String locale, String title) {
			this.locale = locale;
			this.title = title;
		}

		public LocaleBean() {
		}

		public String getLocale() {
			return this.locale;
		}

		public void setLocale(String locale) {
			this.locale = locale;
		}

		public String getTitle() {
			return this.title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}
}
