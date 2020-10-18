package com.popkyss.sweetShop.stripes.action;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.StringUtils;

import com.popkyss.sweetShop.service.IOpravneni;
import com.popkyss.sweetShop.service.SweetShopServiceFactory;
import com.popkyss.sweetShop.setting.ASessionActionSupport;
import com.popkyss.sweetShop.setting.BasicServiceException;
import com.popkyss.sweetShop.stripes.bean.Sws003Bean;

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
 * Registrace zamestnancu
 * @author janpokorny
 *
 */
@UrlBinding("/sws003.action")
public class Sws003 extends ASessionActionSupport<Sws003Bean> {

	private static final String CURRENT_JSP = "/jsp/sweetShop/sws003.jsp";

	private final IOpravneni opravneni = SweetShopServiceFactory.getOpravneni();

	@ValidateNestedProperties({
			@Validate(field = "novyZamestnanec.mesto", maxlength = 50, required = true, on = "zkontroulujAdresu"),
			@Validate(field = "novyZamestnanec.ulice", maxlength = 100, required = true, on = "zkontroulujAdresu"),
			@Validate(field = "novyZamestnanec.psc", maxlength = 5, minlength = 5, required = true, on = "zkontroulujAdresu"),
			@Validate(field = "novyZamestnanec.cp", minvalue = 1, required = true, on = "zkontroulujAdresu"),
			@Validate(field = "novyZamestnanec.email", maxlength = 100, required = true, on = "ulozZamestnance"),
			@Validate(field = "novyZamestnanec.username", maxlength = 30, required = true, on = "ulozZamestnance"),
			@Validate(field = "novyZamestnanec.heslo", maxlength = 30, required = true, on = "ulozZamestnance"),
			@Validate(field = "novyZamestnanec.heslo2", maxlength = 30, required = true, on = "ulozZamestnance"),
			@Validate(field = "novyZamestnanec.jmeno", maxlength = 30, required = true, on = "ulozZamestnance"),
			@Validate(field = "novyZamestnanec.prijmeni", maxlength = 50, required = true, on = "ulozZamestnance"),
			@Validate(field = "novyZamestnanec.telefon", maxlength = 13, minlength = 9) 
			})

	private Sws003Bean actionBean;

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

	public Resolution ulozZamestnance() {
		try {
			opravneni.vytvorZamestnance(actionBean.getNovyZamestnanec());
			msgI("zakaznik.ulozen");
			actionBean = new Sws003Bean();
		} catch (BasicServiceException e) {
			msgE(e.getId(), e.getParams());
		}
		return new RedirectResolution(getClass());
	}

	
	@ValidationMethod(on = "zkontroulujAdresu")
	public void kontrolaAdresyValidation(ValidationErrors err) {
		try {
			Integer psc = Integer.parseInt(actionBean.getNovyZamestnanec().getPsc());
			if(psc == null || psc < 10000) {
				err.add("actionBean.novyZamestnanec.psc", new LocalizableError("chyba.psc", actionBean.getNovyZamestnanec().getPsc()));
			}			
		} catch (NumberFormatException e) {
			err.add("actionBean.novyZamestnanec.psc", new LocalizableError("chyba.psc", actionBean.getNovyZamestnanec().getPsc()));
		}
			
		if(!StringUtils.isAlpha(actionBean.getNovyZamestnanec().getMesto().replaceAll(" ", ""))) {
			err.add("actionBean.novyZamestnanec.mesto", new LocalizableError("chyba.mesto", actionBean.getNovyZamestnanec().getMesto()));
		}
		if(!StringUtils.isAlpha(actionBean.getNovyZamestnanec().getUlice().replaceAll(" ", ""))) {
			err.add("actionBean.novyZamestnanec.ulice", new LocalizableError("chyba.ulice", actionBean.getNovyZamestnanec().getUlice()));
		}		
	}


	@ValidationMethod(on = "ulozZamestnance")
	public void ulozitValidation(ValidationErrors err) {
		if(opravneni.existujeZamestnanecUsername(actionBean.getNovyZamestnanec().getUsername())) {
			err.add("actionBean.novyZamestnanec.username", new LocalizableError("chyba.username", actionBean.getNovyZamestnanec().getUsername()));
		}
		if (!actionBean.getNovyZamestnanec().getHeslo().equals(actionBean.getNovyZamestnanec().getHeslo2())) {
			err.add("actionBean.novyZamestnanec.heslo2", new LocalizableError("chyba.heslo", actionBean.getNovyZamestnanec().getUsername()));
		}
		if(!isValidEmailAddress(actionBean.getNovyZamestnanec().getEmail())) {
			err.add("actionBean.novyZamestnanec.email", new LocalizableError("chyba.email", actionBean.getNovyZamestnanec().getEmail()));
		}
		if(opravneni.existujeZamestnanecEmail(actionBean.getNovyZamestnanec().getEmail())) {
			err.add("actionBean.novyZamestnanec.email", new LocalizableError("chyba.email2", actionBean.getNovyZamestnanec().getEmail()));
		}
		
		
		if(!StringUtils.isAlpha(actionBean.getNovyZamestnanec().getJmeno())) {
			err.add("actionBean.novyZamestnanec.jmeno", new LocalizableError("chyba.jmeno", actionBean.getNovyZamestnanec().getJmeno()));
		}
		if(!StringUtils.isAlpha(actionBean.getNovyZamestnanec().getPrijmeni())) {
			err.add("actionBean.novyZamestnanec.prijmeni", new LocalizableError("chyba.prijmeni", actionBean.getNovyZamestnanec().getPrijmeni()));
		}
		String phone = actionBean.getNovyZamestnanec().getTelefon();
		
		if(phone != null) {
			if(!phone.matches("[+]?[()/0-9. -]{9,}$") || !phone.matches("(\\+420)? ?[1-9][0-9]{2} ?[0-9]{3} ?[0-9]{3}$")) {
				err.add("actionBean.novyZamestnanec.telefon", new LocalizableError("chyba.telefon", actionBean.getNovyZamestnanec().getTelefon()));			
			}
			if(opravneni.existujeZakaznikTelefon(actionBean.getNovyZamestnanec().getTelefon())) {
				err.add("actionBean.novyZamestnanec.telefon", new LocalizableError("chyba.telefon2", actionBean.getNovyZamestnanec().getTelefon()));
			}
		}
	}
	
	
	private boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	
	public Sws003Bean getActionBean() {
		return actionBean;
	}

	public void setActionBean(Sws003Bean actionBean) {
		this.actionBean = actionBean;
	}

}