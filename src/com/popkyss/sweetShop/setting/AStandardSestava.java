package com.popkyss.sweetShop.setting;

import org.apache.log4j.Logger;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;

public abstract class AStandardSestava {
	protected Logger log = Logger.getLogger(getClass());

	protected boolean rotate = false;

	protected Popisky popisky = null;

	protected ZahlaviZapati zahlavi = null;

	protected ZahlaviZapati zapati = null;

	protected Rectangle pageSize = PageSize.A4;

	protected float marginLeft = 30.0F;

	protected float marginRight = 30.0F;

	protected float marginTop = 50.0F;

	protected float marginBottom = 50.0F;

	private boolean indexed = false;

	public boolean isRotate() {
		return this.rotate;
	}

	public void setRotate(boolean rotate) {
		this.rotate = rotate;
	}

	public Popisky getPopisky() {
		return this.popisky;
	}

	public void setPopisky(Popisky popisky) {
		this.popisky = popisky;
	}

	public float getMarginLeft() {
		return this.marginLeft;
	}

	public void setMarginLeft(float marginLeft) {
		this.marginLeft = marginLeft;
	}

	public float getMarginRight() {
		return this.marginRight;
	}

	public void setMarginRight(float marginRight) {
		this.marginRight = marginRight;
	}

	public float getMarginTop() {
		return this.marginTop;
	}

	public void setMarginTop(float marginTop) {
		this.marginTop = marginTop;
	}

	public Rectangle getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(Rectangle pageSize) {
		this.pageSize = pageSize;
	}

	public float getMarginBottom() {
		return this.marginBottom;
	}

	public void setMarginBottom(float marginBottom) {
		this.marginBottom = marginBottom;
	}

	public ZahlaviZapati getZahlavi() {
		return this.zahlavi;
	}

	public void setZahlavi(ZahlaviZapati zahlavi) {
		this.zahlavi = zahlavi;
	}

	public ZahlaviZapati getZapati() {
		return this.zapati;
	}

	public void setZapati(ZahlaviZapati zapati) {
		this.zapati = zapati;
	}

	public boolean isIndexed() {
		return this.indexed;
	}

	public void setIndexed(boolean indexed) {
		this.indexed = indexed;
	}
}
