package com.popkyss.sweetShop.setting;

import org.apache.poi.ss.usermodel.CellStyle;

public class XlsCellProperties {
	private CellStyle style;
	private int type;

	public XlsCellProperties() {
	}

	public XlsCellProperties(CellStyle style, int type) {
		this.style = style;
		this.type = type;
	}

	public CellStyle getStyle() {
		return this.style;
	}

	public void setStyle(CellStyle style) {
		this.style = style;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}
}