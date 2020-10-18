package com.popkyss.sweetShop.setting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RadekTabulky {
	private List<BunkaTabulky> radekTabulky = new ArrayList<BunkaTabulky>();

	public void addBunka(BunkaTabulky bunka) {
		this.radekTabulky.add(bunka);
	}

	public List<BunkaTabulky> getRadekTabulky() {
		return this.radekTabulky;
	}

	public int size() {
		return this.radekTabulky.size();
	}

	public Iterator<BunkaTabulky> iterator() {
		return this.radekTabulky.iterator();
	}

	public BunkaTabulky getBunka(int index) {
		return this.radekTabulky.get(index);
	}
}