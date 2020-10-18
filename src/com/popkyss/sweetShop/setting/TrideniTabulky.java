package com.popkyss.sweetShop.setting;

import org.apache.log4j.Logger;

public class TrideniTabulky {
	static Logger log = Logger.getLogger(TrideniTabulky.class);
	private int poradi;
	private boolean ascending;

	public TrideniTabulky(int poradi, boolean ascending) {
		this.poradi = Math.abs(poradi);
		this.ascending = ascending;
	}

	public boolean hasTrideni() {
		if (this.poradi > 0) {
			return true;
		}
		return false;
	}

	public int getPoradiTrideni() {
		return this.poradi;
	}

	public String toString() {
		String s = "";
		if (this.poradi > 0) {
			s = String.valueOf(s) + String.valueOf(this.poradi);
			if (this.ascending) {
				s = String.valueOf(s) + "↑";
			} else {
				s = String.valueOf(s) + "↓";
			}
		}
		return s;
	}

	public boolean isAscending() {
		return this.ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}
}