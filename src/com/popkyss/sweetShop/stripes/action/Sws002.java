package com.popkyss.sweetShop.stripes.action;


import org.apache.commons.lang3.StringUtils;

import com.popkyss.sweetShop.service.IOpravneni;
import com.popkyss.sweetShop.service.SweetShopServiceFactory;
import com.popkyss.sweetShop.setting.ASessionActionSupport;
import com.popkyss.sweetShop.setting.BasicServiceException;
import com.popkyss.sweetShop.stripes.bean.Sws002Bean;

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
 * Registrace zakazniku
 * @author janpokorny
 *
 */

@UrlBinding("/sws002.action")
public class Sws002 extends ASessionActionSupport<Sws002Bean> {

	private static final String CURRENT_JSP = "/jsp/sweetShop/sws002.jsp";

	private final IOpravneni opravneni = SweetShopServiceFactory.getOpravneni();

	@ValidateNestedProperties({
			@Validate(field = "novyZakaznik.mesto", maxlength = 50, required = true, on = "zkontroulujAdresu"),
			@Validate(field = "novyZakaznik.ulice", maxlength = 100, required = true, on = "zkontroulujAdresu"),
			@Validate(field = "novyZakaznik.psc", maxlength = 5, minlength = 5, required = true, on = "zkontroulujAdresu"),
			@Validate(field = "novyZakaznik.cp", minvalue = 1, required = true, on = "zkontroulujAdresu"),
			@Validate(field = "novyZakaznik.email", maxlength = 100, required = true, on = "ulozZakaznika"),
			@Validate(field = "novyZakaznik.username", maxlength = 30, required = true, on = "ulozZakaznika"),
			@Validate(field = "novyZakaznik.heslo", maxlength = 30, required = true, on = "ulozZakaznika"),
			@Validate(field = "novyZakaznik.heslo2", maxlength = 30, required = true, on = "ulozZakaznika"),
			@Validate(field = "novyZakaznik.jmeno", maxlength = 30, required = true, on = "ulozZakaznika"),
			@Validate(field = "novyZakaznik.prijmeni", maxlength = 50, required = true, on = "ulozZakaznika"),
			@Validate(field = "novyZakaznik.telefon", maxlength = 13, minlength = 9) 
			})

	
	private Sws002Bean actionBean;

	
	@DefaultHandler
	public Resolution resolution() {
		return new ForwardResolution(CURRENT_JSP);
	}

	public Resolution zkontroulujAdresu() {
			actionBean.setAdresaAdded(true);			
		return new RedirectResolution(getClass());
	}
	
	public Resolution zpetNaAdresu() {
		actionBean.setAdresaAdded(false);			
	return new RedirectResolution(getClass());
	}

	public Resolution ulozZakaznika() {
		try {
			opravneni.vytvorZakaznika(actionBean.getNovyZakaznik());
			msgI("zakaznik.ulozen");
			actionBean = new Sws002Bean();
		} catch (BasicServiceException e) {
			msgE(e.getId(), e.getParams());
		}
		return new RedirectResolution(getClass());
	}

	
	@ValidationMethod(on = "zkontroulujAdresu")
	public void kontrolaAdresyValidation(ValidationErrors err) {
		try {
			Integer psc = Integer.parseInt(actionBean.getNovyZakaznik().getPsc());
			if(psc == null || psc < 10000) {
				err.add("actionBean.novyZakaznik.psc", new LocalizableError("chyba.psc", actionBean.getNovyZakaznik().getPsc()));
			}			
		} catch (NumberFormatException e) {
			err.add("actionBean.novyZakaznik.psc", new LocalizableError("chyba.psc", actionBean.getNovyZakaznik().getPsc()));
		}
			
		if(!StringUtils.isAlpha(actionBean.getNovyZakaznik().getMesto().replaceAll(" ", ""))) {
			err.add("actionBean.novyZakaznik.mesto", new LocalizableError("chyba.mesto", actionBean.getNovyZakaznik().getMesto()));
		}
		if(!StringUtils.isAlpha(actionBean.getNovyZakaznik().getUlice().replaceAll(" ", ""))) {
			err.add("actionBean.novyZakaznik.ulice", new LocalizableError("chyba.ulice", actionBean.getNovyZakaznik().getUlice()));
		}		
	}


	@ValidationMethod(on = "ulozZakaznika")
	public void ulozitValidation(ValidationErrors err) {
		if(opravneni.existujeZakaznikUsername(actionBean.getNovyZakaznik().getUsername())) {
			err.add("actionBean.novyZakaznik.username", new LocalizableError("chyba.username", actionBean.getNovyZakaznik().getUsername()));
		}
		if (!actionBean.getNovyZakaznik().getHeslo().equals(actionBean.getNovyZakaznik().getHeslo2())) {
			err.add("actionBean.novyZakaznik.heslo2", new LocalizableError("chyba.heslo", actionBean.getNovyZakaznik().getUsername()));
		}
		if(!isValidEmailAddress(actionBean.getNovyZakaznik().getEmail())) {
			err.add("actionBean.novyZakaznik.email", new LocalizableError("chyba.email", actionBean.getNovyZakaznik().getEmail()));
		}
		if(opravneni.existujeZakaznikEmail(actionBean.getNovyZakaznik().getEmail())) {
			err.add("actionBean.novyZakaznik.email", new LocalizableError("chyba.email2", actionBean.getNovyZakaznik().getEmail()));
		}
		
		
		if(!StringUtils.isAlpha(actionBean.getNovyZakaznik().getJmeno())) {
			err.add("actionBean.novyZakaznik.jmeno", new LocalizableError("chyba.jmeno", actionBean.getNovyZakaznik().getJmeno()));
		}
		if(!StringUtils.isAlpha(actionBean.getNovyZakaznik().getPrijmeni())) {
			err.add("actionBean.novyZakaznik.prijmeni", new LocalizableError("chyba.prijmeni", actionBean.getNovyZakaznik().getPrijmeni()));
		}
		String phone = actionBean.getNovyZakaznik().getTelefon();
		if(phone != null) {
			if(!phone.matches("[+]?[()/0-9. -]{9,}$") || !phone.matches("(\\+420)? ?[1-9][0-9]{2} ?[0-9]{3} ?[0-9]{3}$")) {
				err.add("actionBean.novyZakaznik.telefon", new LocalizableError("chyba.telefon", actionBean.getNovyZakaznik().getTelefon()));			
			}
			if(opravneni.existujeZakaznikTelefon(actionBean.getNovyZakaznik().getTelefon())) {
				err.add("actionBean.novyZakaznik.telefon", new LocalizableError("chyba.telefon2", actionBean.getNovyZakaznik().getTelefon()));
			}
		}
	}
	
	private boolean isValidEmailAddress(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	      return email.matches(regex);
	}

	
	public Sws002Bean getActionBean() {
		return actionBean;
	}

	public void setActionBean(Sws002Bean actionBean) {
		this.actionBean = actionBean;
	}

}