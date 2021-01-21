package com.popkyss.sweetShop.stripes.action;


import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.popkyss.sweetShop.model.Uzivatel;
import com.popkyss.sweetShop.service.IOpravneni;
import com.popkyss.sweetShop.service.SweetShopServiceFactory;
import com.popkyss.sweetShop.setting.ASessionActionSupport;
import com.popkyss.sweetShop.setting.BasicServiceException;
import com.popkyss.sweetShop.stripes.bean.Sws007Bean;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;



/**
 * Prihlaseni
 * @author janpokorny
 *
 */

@UrlBinding("/sws007.action")
public class Sws007 extends ASessionActionSupport<Sws007Bean> {

	private static final String CURRENT_JSP = "/jsp/sweetShop/sws007.jsp";

	private final IOpravneni opravneni = SweetShopServiceFactory.getOpravneni();

	@ValidateNestedProperties({
			@Validate(field = "username", maxlength = 50, required = true, on = "prihlaseni"),
			@Validate(field = "heslo", maxlength = 30, required = true, on = "prihlaseni"),
			@Validate(field = "email", maxlength = 100, required = true, on = "zapomenuteHeslo")
			})

	private Sws007Bean actionBean;

	@DefaultHandler
	public Resolution resolution() {
		prihlaseni();
		return new ForwardResolution(CURRENT_JSP);
	}
	
	public Resolution prihlaseni() {
		Uzivatel u = opravneni.najdiUzivatele(actionBean.getUsername(),actionBean.getHeslo());
		if(u != null) {	
			return new ForwardResolution("/login").addParameter("uname", actionBean.getUsername()).addParameter("pass", actionBean.getHeslo());
		}
		msgE("chyba.prihlaseni");
		return new RedirectResolution(getClass());
	}

	public Resolution zmenZapomenuteHeslo() {
		actionBean.setZmenHeslo(!actionBean.isZmenHeslo());
		return new RedirectResolution(getClass());
	}
	
	public Resolution zapomenuteHeslo() {
		try {
			opravneni.zmenHesloUzivateleDleEmailu(actionBean.getEmail());
			msgI("007.01");
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (BasicServiceException e) {
			msgE(e.getId(), e.getParams());
		}
		return new RedirectResolution(getClass());
	}
	
	
	@ValidationMethod(on = "prihlaseni")
	public void prihlaseniValidation(ValidationErrors err) {
		
		if(!opravneni.existujeUzivatel(actionBean.getUsername(), actionBean.getHeslo())) {
			err.add("actionBean.username", new LocalizableError("chyba.prihlaseni", actionBean.getUsername()));
		}					
	}
	
	@ValidationMethod(on = "zapomenuteHeslo")
	public void zapHeslpValidation(ValidationErrors err) {
		if(!isValidEmailAddress(actionBean.getEmail())) {
			err.add("actionBean.email", new LocalizableError("chyba.email", actionBean.getEmail()));
		}
		if(!opravneni.existujeZakaznikEmail(actionBean.getEmail()) && !opravneni.existujeZamestnanecEmail(actionBean.getEmail())) {
			err.add("actionBean.novyZakaznik.email", new LocalizableError("chyba.email2", actionBean.getEmail()));
		}		
	}
	
	private boolean isValidEmailAddress(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	      return email.matches(regex);
	}
	
	public Sws007Bean getActionBean() {
		return actionBean;
	}

	public void setActionBean(Sws007Bean actionBean) {
		this.actionBean = actionBean;
	}

}