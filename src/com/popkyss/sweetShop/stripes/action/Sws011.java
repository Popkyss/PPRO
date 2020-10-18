package com.popkyss.sweetShop.stripes.action;

import com.popkyss.sweetShop.service.IZbozi;
import com.popkyss.sweetShop.service.SweetShopServiceFactory;
import com.popkyss.sweetShop.setting.ASessionActionSupport;
import com.popkyss.sweetShop.stripes.bean.Sws011Bean;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;


/**
 * Nabidka produktu
 * @author janpokorny
 *
 */

@UrlBinding("/sws011.action")
public class Sws011 extends ASessionActionSupport<Sws011Bean> {
	
	private static final String CURRENT_JSP = "/jsp/sweetShop/sws011.jsp";
	
	private final IZbozi zbozi = SweetShopServiceFactory.getZbozi();
	
	private Sws011Bean actionBean;
	
	@Validate(encrypted = true)
	private Integer idProdukt;
	
	@DefaultHandler
	public Resolution resolution() {
		actionBean.setProdukty(zbozi.najdiProdukty());			
		return new ForwardResolution(CURRENT_JSP);
	}	

	
	public Sws011Bean getActionBean() {
		return actionBean;
	}
	
	
	public void setActionBean(Sws011Bean actionBean) {
		this.actionBean = actionBean;
	}

	public Integer getIdProdukt() {
		return idProdukt;
	}

	public void setIdProdukt(Integer idProdukt) {
		this.idProdukt = idProdukt;
	}


	
}