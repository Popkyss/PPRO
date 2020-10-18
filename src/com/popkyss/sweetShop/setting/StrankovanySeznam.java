package com.popkyss.sweetShop.setting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StrankovanySeznam<RadekSeznamu> implements Serializable {
	private static final long serialVersionUID = 1L;
	protected List<RadekSeznamu> radkySeznamu = new ArrayList<RadekSeznamu>();
	protected int stranka = 1;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected HtmlSelect<String, String> poctyRadkuNaStrance = new HtmlSelect("radkyNaStrance");

	public static final String RADKY_10 = "10";

	public static final String RADKY_15 = "15";

	public static final String RADKY_20 = "20";

	public static final String RADKY_25 = "25";

	public static final String RADKY_40 = "40";
	public static final String RADKY_80 = "80";
	public static final String RADKY_100 = "100";
	public static final String RADKY_ALL = "ALL";

	public StrankovanySeznam() {
		naplPostyRadku();
	}

	protected void naplPostyRadku() {
		this.poctyRadkuNaStrance.add("10", "10");
		this.poctyRadkuNaStrance.add("15", "15");
		this.poctyRadkuNaStrance.add("20", "20");
		this.poctyRadkuNaStrance.add("25", "25");
		this.poctyRadkuNaStrance.add("40", "40");
		this.poctyRadkuNaStrance.add("80", "80");
		this.poctyRadkuNaStrance.add("100", "100");
		this.poctyRadkuNaStrance.add("ALL", "...");
	}

	public List<RadekSeznamu> getRadkySeznamu() {
		return this.radkySeznamu;
	}

	public void setRadkySeznamu(List<RadekSeznamu> radkySeznamu) {
		this.radkySeznamu = radkySeznamu;
	}

	public int getStranka() {
		if (getCelkemStranek() < this.stranka) {
			this.stranka = getCelkemStranek();
		}
		if (this.stranka == 0 && this.radkySeznamu.size() > 0) {
			this.stranka = 1;
		}
		return this.stranka;
	}

	public void setStranka(int stranka) {
		this.stranka = stranka;
	}

	public int getPrvniRadekSeznamu() {
		int prvniRadekSeznamu = (getStranka() - 1) * getPocetRadkuStranky();
		if (prvniRadekSeznamu < 0) {
			return 0;
		}
		return prvniRadekSeznamu;
	}

	
	public int getPocetRadkuStranky() {
		String selected = (String) this.poctyRadkuNaStrance.getSelected();
		if (selected.equals("ALL")) {
			return getCelkemRadku();
		}
		return (new Integer(selected)).intValue();
	}

	public int getPosledniRadekSeznamu() {
		int posledni = getPrvniRadekSeznamu() + getPocetRadkuStranky() - 1;
		if (posledni > getRadkySeznamu().size() - 1) {
			posledni = getRadkySeznamu().size() - 1;
		}
		if (posledni < 0) {
			return 0;
		}
		return posledni;
	}

	public int getCelkemRadku() {
		return this.radkySeznamu.size();
	}

	public int getCelkemStranek() {
		if (getPocetRadkuStranky() != 0) {
			return getCelkemRadku() / getPocetRadkuStranky()
					+ ((getCelkemRadku() % getPocetRadkuStranky() != 0) ? 1 : 0);
		}
		return 1;
	}

	public boolean isExistPrvniStranka() {
		return (getStranka() > 1);
	}

	public boolean isExistPredchazejiciStranka() {
		return (getStranka() > 1);
	}

	public boolean isExistPredchazejiciStranka(int n) {
		return (getStranka() + n > 1);
	}

	public boolean isExistNasledujiciStranka() {
		return (getStranka() < getCelkemStranek());
	}

	public boolean isExistNasledujiciStranka(int n) {
		return (getStranka() + n < getCelkemStranek());
	}

	public boolean isExistPosledniStranka() {
		return (getStranka() < getCelkemStranek());
	}

	public String getPredchazejiciStrankaString() {
		return String.valueOf(getStranka() - 1);
	}

	public String getNasledujiciStrankaString() {
		return String.valueOf(getStranka() + 1);
	}

	public String getPosledniStrankaString() {
		return String.valueOf(getCelkemStranek());
	}

	public HtmlSelect<String, String> getPoctyRadkuNaStrance() {
		return this.poctyRadkuNaStrance;
	}

	public void setPoctyRadkuNaStrance(HtmlSelect<String, String> poctyRadkuNaStrance) {
		this.poctyRadkuNaStrance = poctyRadkuNaStrance;
	}
}
