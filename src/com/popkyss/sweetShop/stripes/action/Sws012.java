package com.popkyss.sweetShop.stripes.action;


import com.popkyss.sweetShop.model.PrepravkaKosik;
import com.popkyss.sweetShop.service.IZbozi;
import com.popkyss.sweetShop.service.SweetShopServiceFactory;
import com.popkyss.sweetShop.setting.ASessionActionSupport;
import com.popkyss.sweetShop.setting.BasicServiceException;
import com.popkyss.sweetShop.stripes.bean.Sws012Bean;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;



/**
 * Detail zbozi
 * @author janpokorny
 *
 */

@UrlBinding("/sws012.action")
public class Sws012 extends ASessionActionSupport<Sws012Bean> {

	private static final String CURRENT_JSP = "/jsp/sweetShop/sws012.jsp";

	private final IZbozi zbozi = SweetShopServiceFactory.getZbozi();

	private Sws012Bean actionBean;
	
	@Validate(encrypted = true)
	private Integer idProdukt;
	
	
	@DefaultHandler
	public Resolution resolution() {
		return new ForwardResolution(CURRENT_JSP);
	}
	
	public Resolution view() {
		try {
			actionBean.setProdukt(zbozi.najdiProdukt(idProdukt));
		} catch (BasicServiceException e) {
			msgE(e.getId(), e.getParams());
		}
		return new RedirectResolution(getClass());
	}
	
	public Resolution pridatDoKosiku() {
		try {
			PrepravkaKosik k = (PrepravkaKosik)session().getAttribute("kosik");
			boolean pridat = zbozi.pridatDoKosiku(k.getIdKosik(), actionBean.getProdukt());
			if(pridat) {
				k.setPocet(k.getPocet()+1);
				session().setAttribute("kosik", k);
				msgI("012.01");
			} else {
				msgI("012.02");				
			}
		} catch (BasicServiceException e) {
			msgE(e.getId(), e.getParams());
		}
		return new RedirectResolution(getClass());
	}
	
	public Sws012Bean getActionBean() {
		return actionBean;
	}

	public void setActionBean(Sws012Bean actionBean) {
		this.actionBean = actionBean;
	}

	public Integer getIdProdukt() {
		return idProdukt;
	}

	public void setIdProdukt(Integer idProdukt) {
		this.idProdukt = idProdukt;
	}


}