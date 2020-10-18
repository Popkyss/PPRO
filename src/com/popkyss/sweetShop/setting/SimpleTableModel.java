package com.popkyss.sweetShop.setting;

import java.util.List;

public class SimpleTableModel extends AStandardSestava {
	private List<Kriteria> kriteria = null;

	private SimpleDataTable table;

	@Deprecated
	public SimpleTableModel(AHlavickaDatoveTabulky hlavickaTabulky, List<RadekTabulky> radkyTabulky,
			List<Kriteria> kriteria, Popisky popisky) {
		this.kriteria = kriteria;
		this.popisky = popisky;
		this.table = new SimpleDataTable(hlavickaTabulky, radkyTabulky);
	}

	public SimpleTableModel(SimpleDataTable table, List<Kriteria> kriteria, Popisky popisky) {
		this.kriteria = kriteria;
		this.popisky = popisky;
		this.table = table;
	}

	public List<Kriteria> getKriteria() {
		return this.kriteria;
	}

	public List<RadekTabulky> getRadkyTabulky() {
		return this.table.getRadkyTabulky();
	}

	public AHlavickaTabulky getHlavickaTabulky() {
		return this.table.getHlavickaTabulky();
	}

	public int size() {
		return this.table.getHlavickaTabulky().getPocetSloupcu();
	}

	public boolean isRotate() {
		return this.rotate;
	}

	public void setRotate(boolean rotate) {
		this.rotate = rotate;
	}

	public Popisky getPopisky() {
		return this.popisky;
	}

	public SirkaSloupcuSwitcher getSwitcher() {
		return this.table.getSwitcher();
	}

	public void setSwitcher(SirkaSloupcuSwitcher switcher) {
		this.table.setSwitcher(switcher);
	}

	public int getPocetObarvenychRadku() {
		return this.table.getPocetObarvenychRadku();
	}

	public void setPocetObarvenychRadku(int pocetObarvenychRadku) {
		this.table.setPocetObarvenychRadku(pocetObarvenychRadku);
	}

	public float[] getSirkaSloupcuTabulky() {
		return this.table.getSirkaSloupcuTabulky();
	}

	public void setSirkaSloupcuTabulky(float[] sirkaSloupcuTabulky) {
		this.table.setSirkaSloupcuTabulky(sirkaSloupcuTabulky);
	}

	public SimpleDataTable getTable() {
		return this.table;
	}
}