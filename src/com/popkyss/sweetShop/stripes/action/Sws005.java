package com.popkyss.sweetShop.stripes.action;

import com.popkyss.sweetShop.service.IOpravneni;
import com.popkyss.sweetShop.service.SweetShopServiceFactory;
import com.popkyss.sweetShop.setting.ASessionActionSupport;
import com.popkyss.sweetShop.stripes.bean.Sws005Bean;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;


/**
 * Evidence zakazniku
 * @author janpokorny
 *
 */

@UrlBinding("/sws005.action")
public class Sws005 extends ASessionActionSupport<Sws005Bean> {
	
	private static final String CURRENT_JSP = "/jsp/sweetShop/sws005.jsp";
	
	private final IOpravneni opravneni = SweetShopServiceFactory.getOpravneni();
	
	private Sws005Bean actionBean;
	
	
	@DefaultHandler
	public Resolution resolution() {
		actionBean.setUzivatele(opravneni.najdiVsechnyZakazniky());			
		return new ForwardResolution(CURRENT_JSP);
	}	
	
	
	public Sws005Bean getActionBean() {
		return actionBean;
	}
	
	
	public void setActionBean(Sws005Bean actionBean) {
		this.actionBean = actionBean;
	}
	
}