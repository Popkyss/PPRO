package com.popkyss.sweetShop.stripes.action;


import com.popkyss.sweetShop.setting.AbstractAction;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/sws000.action")
public class Sws000 extends AbstractAction {
	
	private static final String CURRENT_JSP = "/jsp/sweetShop/sws000.jsp";
	

	@DefaultHandler
	public Resolution resolution() {
		return new ForwardResolution(CURRENT_JSP);
	}
	
}