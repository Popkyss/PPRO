package com.popkyss.sweetShop.setting;

import java.io.Serializable;

import net.sourceforge.stripes.action.UrlBinding;

public class Sloupec implements Serializable {
	private static final long serialVersionUID = -2699081988544694271L;
	private String sloupec;
	private int poradiTrideni = 0;

	private boolean ascending = false;

	private boolean trideni = false;

	private String beanNumber = null;

	private SerializableComparator comparator = null;

	public Sloupec() {
	}

	public Sloupec(String sloupec) {
		this.sloupec = sloupec;
	}

	private String getLinkText() {
		String linkText;
		boolean jeJedenRadek = PropertyReader
				.bundleExists(String.valueOf(PropertyReader.getLNG_PREFIX()) + this.beanNumber, "head." + this.sloupec);

		if (!jeJedenRadek) {
			linkText = "";
			for (int i = 1; i < 10; i++) {
				String tempText = PropertyReader.bundle(
						String.valueOf(PropertyReader.getLNG_PREFIX()) + this.beanNumber,
						"head." + this.sloupec + "." + i);
				if (!tempText.equalsIgnoreCase("???head." + this.sloupec + "." + i + "???")) {
					if (i > 1) {
						linkText = String.valueOf(linkText) + "<br />";
					}
					linkText = String.valueOf(linkText) + tempText;
				} else {
					if (linkText.equals("")) {
						linkText = String.valueOf(linkText) + tempText;
					}
					break;
				}
			}
		} else {
			linkText = PropertyReader.bundle(String.valueOf(PropertyReader.getLNG_PREFIX()) + this.beanNumber,
					"head." + this.sloupec);
		}
		return linkText;
	}

	public String getText() {
		StringBuilder outputText = new StringBuilder(200);
		outputText.append("\n");

		String text = getLinkText();
		if (isTrideni()) {
			outputText.append(
					"<a href=\"" + getBeanUrl() + "?asc=&amp;sloupecName=" + this.sloupec + "\">" + text + "</a>");
		} else {
			outputText.append(
					"<a href=\"" + getBeanUrl() + "?addNew=&amp;sloupecName=" + this.sloupec + "\">" + text + "</a>");
		}
		return outputText.toString();
	}

	public String getStaticText() {
		return getLinkText();
	}

	private String getBeanUrl() {
		String beanUrl = "";
		String className = "com.popkyss." + WebAppSettings.getAppFolderName() + ".stripes.action."
				+ WebAppSettings.getBeanPrefix() + this.beanNumber;
		try {
			Class<?> beanClass = Class.forName(className);
			UrlBinding ub = beanClass.<UrlBinding>getAnnotation(UrlBinding.class);
			beanUrl = ub.value();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		beanUrl = beanUrl.substring(1);
		return beanUrl;
	}

	public String getTrideniHtml() {
		StringBuilder outputText = new StringBuilder(200);
		outputText.append("\n");

		if (isTrideni()) {
			outputText.append("<a href=\"" + getBeanUrl() + "?remove=&amp;sloupecName=" + this.sloupec + "\">"
					+ getPoradiTrideni() + ".</a> ");
			if (isAscending()) {

				outputText.append("<img src=\"./images/sys_table-sort-up1.png\" alt=\"asc\"/>");
			} else {
				outputText.append("<img src=\"./images/sys_table-sort-down1.png\" alt=\"desc\"/>");
			}
		} else {
			outputText.append("<a href=\"" + getBeanUrl() + "?add=&amp;sloupecName=" + this.sloupec + "\">+</a> ");
		}
		return outputText.toString();
	}

	public String getStaticTrideniHtml() {
		StringBuilder outputText = new StringBuilder(200);
		outputText.append("\n");
		if (isTrideni()) {
			outputText.append(String.valueOf(getPoradiTrideni()) + ". ");
			if (isAscending()) {
				outputText.append("<img src=\"./images/sys_table-sort-up1.png\" alt=\"asc\"/>");
			} else {
				outputText.append("<img src=\"./images/sys_table-sort-down1.png\" alt=\"desc\"/>");
			}
		}
		return outputText.toString();
	}

	public String getOutputText() {
		StringBuilder outputText = new StringBuilder(200);
		outputText.append("\n");

		String text = getLinkText();
		if (isTrideni()) {
			outputText.append("<a href=\"" + getBeanUrl() + "?remove=&amp;sloupecName=" + this.sloupec + "\">"
					+ getPoradiTrideni() + ".</a> ");
			outputText.append(
					"<a href=\"" + getBeanUrl() + "?asc=&amp;sloupecName=" + this.sloupec + "\">" + text + "</a>");
			if (isAscending()) {

				outputText.append("<img src=\"./images/sys_table-sort-up1.png\" alt=\"asc\"/>");
			} else {

				outputText.append("<img src=\"./images/sys_table-sort-down1.png\" alt=\"desc\"/>");
			}
		} else {
			outputText.append("<a href=\"" + getBeanUrl() + "?add=&amp;sloupecName=" + this.sloupec + "\">+</a> ");
			outputText.append(
					"<a href=\"" + getBeanUrl() + "?addNew=&amp;sloupecName=" + this.sloupec + "\">" + text + "</a>");
		}
		return outputText.toString();
	}

	public String getStaticOutputText() {
		StringBuilder outputText = new StringBuilder(200);
		outputText.append("\n");

		String text = getLinkText();
		if (isTrideni()) {
			outputText.append(String.valueOf(getPoradiTrideni()) + ". ");
			outputText.append(text);
			if (isAscending()) {
				outputText.append("<img src=\"./images/sys_table-sort-up1.png\" alt=\"asc\"/>");
			} else {
				outputText.append("<img src=\"./images/sys_table-sort-down1.png\" alt=\"desc\"/>");
			}
		} else {
			outputText.append(text);
		}
		return outputText.toString();
	}

	public String getSloupec() {
		return this.sloupec;
	}

	public void setSloupec(String sloupec) {
		this.sloupec = sloupec;
	}

	public int getPoradiTrideni() {
		return this.poradiTrideni;
	}

	public void setPoradiTrideni(int poradiTrideni) {
		this.poradiTrideni = poradiTrideni;
	}

	public boolean isAscending() {
		return this.ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	public boolean isTrideni() {
		return this.trideni;
	}

	public void setTrideni(boolean trideni) {
		this.trideni = trideni;
	}

	public String getBeanNumber() {
		return this.beanNumber;
	}

	public void setBeanNumber(String beanNumber) {
		this.beanNumber = beanNumber;
	}

	public SerializableComparator getComparator() {
		return this.comparator;
	}

	public void setComparator(SerializableComparator comparator) {
		this.comparator = comparator;
	}
}