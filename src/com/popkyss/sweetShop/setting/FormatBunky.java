package com.popkyss.sweetShop.setting;

public class FormatBunky extends CellStyle {
	private String maskaSloupce = null;

	public FormatBunky() {
		this.wrapped = true;
		this.fontBunky = PDFFont.NORMAL;
		this.zarovnaniBunky = 0;
	}

	public String getMaskaSloupce() {
		return this.maskaSloupce;
	}

	public void setMaskaSloupce(String maskaSloupce) {
		this.maskaSloupce = maskaSloupce;
	}
}