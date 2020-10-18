package com.popkyss.sweetShop.setting;



public interface IHlavickaManipulator {
	HlavickaBean getHlavicka();

	void deleteStrankaNavratSeznam();

	void odstranPolozkuNavratu(String paramString);

	void deletePosledniStrankaNavratSeznam();
}