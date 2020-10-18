package com.popkyss.sweetShop.stripes.action;

import com.popkyss.sweetShop.model.PrepravkaKosik;
import com.popkyss.sweetShop.service.IZbozi;
import com.popkyss.sweetShop.service.SweetShopServiceFactory;
import com.popkyss.sweetShop.setting.ASessionActionSupport;
import com.popkyss.sweetShop.setting.BasicServiceException;
import com.popkyss.sweetShop.stripes.bean.Sws010Bean;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;


/**
 * Kosik
 * @author janpokorny
 *
 */

@UrlBinding("/sws010.action")
public class Sws010 extends ASessionActionSupport<Sws010Bean> {
	
	private static final String CURRENT_JSP = "/jsp/sweetShop/sws010.jsp";
	
	
	private final IZbozi zbozi = SweetShopServiceFactory.getZbozi();
	
	private Sws010Bean actionBean;
	
	@Validate(encrypted = true)
	private Integer idPolozka;
	
	@DefaultHandler
	public Resolution resolution() {
		int kosikID = ((PrepravkaKosik)session().getAttribute("kosik")).getIdKosik();
		actionBean.setPolozky(zbozi.najdiPolozkyKosiku(kosikID));			
		return new ForwardResolution(CURRENT_JSP);
	}	
	
	public Resolution smazat() {
		
		try {
			boolean smazana = zbozi.odebratPolozku(idPolozka);
			if(smazana) {
				PrepravkaKosik k = (PrepravkaKosik) session().getAttribute("kosik");
				k.setPocet(k.getPocet()-1);	
				session().setAttribute("kosik", k);
				msgI("010.01");
			} else {
				msgI("010.02");
			}
		} catch (BasicServiceException e) {
			msgE(e.getId(), e.getParams());
		}
		return new RedirectResolution(getClass());
	}
	
	
	public Sws010Bean getActionBean() {
		return actionBean;
	}
	
	
	public void setActionBean(Sws010Bean actionBean) {
		this.actionBean = actionBean;
	}

	public Integer getIdPolozka() {
		return idPolozka;
	}

	public void setIdPolozka(Integer idPolozka) {
		this.idPolozka = idPolozka;
	}

	
}