package com.popkyss.sweetShop.setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZahlaviZapati extends AHlavickaTabulky {
	private List<BunkaTabulky> bunky = new ArrayList<BunkaTabulky>();
	private Map<Integer, ZahlaviZapatiTyp> typy = new HashMap<Integer, ZahlaviZapatiTyp>();

	public ZahlaviZapati(int pocetDatovychSloupcu) {
		super(pocetDatovychSloupcu);
		this.bunky = new ArrayList<BunkaTabulky>(pocetDatovychSloupcu);
	}

	public void addBunka(BunkaTabulky bunka) {
		this.bunky.add(bunka);
	}

	public void addBunka(ZahlaviZapatiTyp typ) {
		BunkaTabulky bunka = BunkaTabulky.defaultBunka("");
		this.bunky.add(bunka);
		this.typy.put(Integer.valueOf(this.bunky.size() - 1), typ);
	}

	public List<BunkaTabulky> getBunky() {
		return this.bunky;
	}

	public void setBunky(List<BunkaTabulky> bunky) {
		this.bunky = bunky;
	}

	public Map<Integer, ZahlaviZapatiTyp> getTypy() {
		return this.typy;
	}

	public void setTypy(Map<Integer, ZahlaviZapatiTyp> typy) {
		this.typy = typy;
	}
}