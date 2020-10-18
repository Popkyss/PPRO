package com.popkyss.sweetShop.setting;

import java.util.List;
import java.util.Map;

import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;

public abstract class ASeznamAction<SeznamBean extends ASeznamBean<R>, R extends IRadekSeznamu>
		extends ASessionActionSupport<SeznamBean> {
	@DontValidate
	public Resolution asc() {
		boolean setrideno = false;
		try {
			getSeznamBean().sort(request().getParameter("sloupecName"));
			setrideno = true;
			return findForward();
		} finally {
			if (!setrideno) {
				getSeznamBean().deleteAllSloupceTrideni();
			}
		}
	}

	@DontValidate
	public Resolution remove() {
		getSeznamBean().removeSortCriteria(request().getParameter("sloupecName"));
		return findForward();
	}

	@SuppressWarnings("rawtypes")
	@DontValidate
	public Resolution add() {
		boolean pridano = false;
		try {
			getSeznamBean().addSortCriteria(request().getParameter("sloupecName"));
			getSeznamBean().sort();
			pridano = true;
			return findForward();
		} finally {
			if (!pridano) {
				((ASeznamBean) getActionBean()).deleteAllSloupceTrideni();
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@DontValidate
	public Resolution addNew() {
		boolean pridano = false;
		try {
			getSeznamBean().sort(request().getParameter("sloupecName"));
			pridano = true;
			return findForward();
		} finally {
			if (!pridano) {
				((ASeznamBean) getActionBean()).deleteAllSloupceTrideni();
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<R> getRadkySeznamu() {
		return ((ASeznamBean) getActionBean()).getStrankovanySeznam().getRadkySeznamu();
	}

	@DontValidate
	public Resolution nastaveniStrankyAction() throws NumberFormatException {
		String stranka = request().getParameter("nextPage");
		getSeznamBean().nastaveniStrankyAction(stranka);
		return findForward();
	}

	@DontValidate
	public Resolution poctyRadkuNaStranceAction() {
		String pocetRadku = request().getParameter("pocetRadku");
		getSeznamBean().getStrankovanySeznam().getPoctyRadkuNaStrance().setSelected(pocetRadku);
		return findForward();
	}

	public SeznamBean getSeznamBean() {
		return getActionBean();
	}

	public void setSeznamBean(SeznamBean seznamBean) {
		setActionBean(seznamBean);
	}

	public Map<String, Sloupec> getSloupecSeznam() {
		return getSeznamBean().getSloupecSeznam();
	}

	public Resolution findForward() {
		getSeznamBean().setNovyVyber(false);
		return (Resolution) new RedirectResolution(getClass());
	}
	
}