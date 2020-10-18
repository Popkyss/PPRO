package com.popkyss.sweetShop.setting;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

public abstract class CellStyle {
	public static final int ALIGN_LEFT = 0;
	public static final int ALIGN_RIGHT = 2;
	public static final int ALIGN_CENTER = 1;
	public static final BaseColor DEFAULT_HEADER_FILL = BaseColor.LIGHT_GRAY;

	protected boolean wrapped;

	protected int zarovnaniBunky;
	protected Font fontBunky;
	protected float grayFill = 1.0F;
	protected BaseColor vyplnBunky = BaseColor.WHITE;
	protected BaseColor barvaOhraniceniTabulky = DEFAULT_HEADER_FILL;

	public int getZarovnaniBunky() {
		return this.zarovnaniBunky;
	}

	public void setZarovnaniSloupce(int zarovnaniSloupce) {
		this.zarovnaniBunky = zarovnaniSloupce;
	}

	public Font getFontBunky() {
		return this.fontBunky;
	}

	public void setFontBunky(Font fontBunky) {
		this.fontBunky = fontBunky;
	}

	public boolean isWrapped() {
		return this.wrapped;
	}

	public void setWrapped(boolean wrapped) {
		this.wrapped = wrapped;
	}

	public float getGrayFill() {
		return this.grayFill;
	}

	public void setGrayFill(float grayFill) {
		this.grayFill = grayFill;
	}

	public BaseColor getVyplnBunky() {
		return this.vyplnBunky;
	}

	public void setVyplnBunky(BaseColor vyplnBunky) {
		this.vyplnBunky = vyplnBunky;
	}

	public void setZarovnaniBunky(int zarovnaniBunky) {
		this.zarovnaniBunky = zarovnaniBunky;
	}

	public BaseColor getBarvaOhraniceniTabulky() {
		return this.barvaOhraniceniTabulky;
	}

	public void setBarvaOhraniceniTabulky(BaseColor barvaOhraniceniTabulky) {
		this.barvaOhraniceniTabulky = barvaOhraniceniTabulky;
	}
}
