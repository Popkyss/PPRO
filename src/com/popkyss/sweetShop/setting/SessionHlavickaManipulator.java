package com.popkyss.sweetShop.setting;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

public class SessionHlavickaManipulator implements IHlavickaManipulator {
	public HlavickaBean getHlavicka() {
		HlavickaBean hb = (HlavickaBean) getSession().getAttribute("hlavickaBean");
		if (hb == null) {
			hb = new HlavickaBean();
		}
		return hb;
	}

	public void deleteStrankaNavratSeznam() {
		HlavickaBean hb = getHlavicka();
		StrankaNavratBean snb = null;
		Iterator<StrankaNavratBean> iterator = hb.getNavratSeznam().iterator();
		if (iterator.hasNext()) {
			StrankaNavratBean snbTemp = iterator.next();
			snb = snbTemp;
		}

		if (snb != null) {
			hb.setStranka(snb.getStranka());
		}
		getSession().setAttribute("hlavickaBean", hb);
	}

	private HttpSession getSession() {
		return RequestContext.getHttpSession();
	}

	public void odstranPolozkuNavratu(String stranka) {
		if (stranka == null) {
			return;
		}

		HlavickaBean hb = getHlavicka();
		StrankaNavratBean posledni = null;
		for (Iterator<StrankaNavratBean> it = hb.getNavratSeznam().iterator(); it.hasNext();) {
			StrankaNavratBean snb = it.next();
			if (snb.getStranka().equals(stranka)) {
				it.remove();
				break;
			}
			posledni = snb;
		}

		if (!hb.getNavratSeznam().isEmpty() && posledni != null) {
			hb.setStranka(posledni.getStranka());
		}

		getSession().setAttribute("hlavickaBean", hb);
	}

	public void deletePosledniStrankaNavratSeznam() {
		HlavickaBean hb = getHlavicka();
		if (hb.getNavratSeznam().size() > 0) {
			List<StrankaNavratBean> list = hb.getNavratSeznam();
			if (list.size() > 0) {
				StrankaNavratBean snb = list.get(list.size() - 1);
				hb.setStranka(snb.getStranka());
				getSession().setAttribute("hlavickaBean", hb);
			}
		}
	}
}