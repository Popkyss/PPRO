package com.popkyss.sweetShop.stripes.action;

import com.popkyss.sweetShop.service.IOpravneni;
import com.popkyss.sweetShop.service.SweetShopServiceFactory;
import com.popkyss.sweetShop.setting.ASessionActionSupport;
import com.popkyss.sweetShop.stripes.bean.Sws001Bean;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;


/**
 * Evidence zamestnancu
 * @author janpokorny
 *
 */

@UrlBinding("/sws001.action")
public class Sws001 extends ASessionActionSupport<Sws001Bean> {
	
	private static final String CURRENT_JSP = "/jsp/sweetShop/sws001.jsp";
	
	private final IOpravneni opravneni = SweetShopServiceFactory.getOpravneni();
	
	private Sws001Bean actionBean;
	
	
	@DefaultHandler
	public Resolution resolution() {
		actionBean.setUzivatele(opravneni.najdiVsechnyZamestnance());			
		return new ForwardResolution(CURRENT_JSP);
	}	
	
	
	public Sws001Bean getActionBean() {
		return actionBean;
	}
	
	
	public void setActionBean(Sws001Bean actionBean) {
		this.actionBean = actionBean;
	}
	
}