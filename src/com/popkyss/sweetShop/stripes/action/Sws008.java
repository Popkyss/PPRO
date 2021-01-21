package com.popkyss.sweetShop.stripes.action;


import org.apache.commons.lang3.StringUtils;

import com.popkyss.sweetShop.model.Uzivatel;
import com.popkyss.sweetShop.service.IOpravneni;
import com.popkyss.sweetShop.service.SweetShopServiceFactory;
import com.popkyss.sweetShop.setting.ASessionActionSupport;
import com.popkyss.sweetShop.setting.BasicServiceException;
import com.popkyss.sweetShop.stripes.bean.Sws008Bean;

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
 * Editace zakaznika
 * @author janpokorny
 *
 */

@UrlBinding("/sws008.action")
public class Sws008 extends ASessionActionSupport<Sws008Bean> {

	private static final String CURRENT_JSP = "/jsp/sweetShop/sws008.jsp";

	private final IOpravneni opravneni = SweetShopServiceFactory.getOpravneni();

	@ValidateNestedProperties({
			@Validate(field = "uzivatel.mesto", maxlength = 50, required = true, on = "ulozitAdresu"),
			@Validate(field = "uzivatel.ulice", maxlength = 100, required = true, on = "ulozitAdresu"),
			@Validate(field = "uzivatel.psc", maxlength = 5, minlength = 5, required = true, on = "ulozitAdresu"),
			@Validate(field = "uzivatel.cp", minvalue = 1, required = true, on = "ulozitAdresu"),

			@Validate(field = "uzivatel.jmeno", maxlength = 30, required = true, on = "ulozit"),
			@Validate(field = "uzivatel.prijmeni", maxlength = 50, required = true, on = "ulozit"),
			@Validate(field = "uzivatel.telefon", maxlength = 13, minlength = 9), 
			
			@Validate(field = "uzivatel.heslo2", maxlength = 30, required = true, on = "ulozitHeslo"),
			@Validate(field = "noveHeslo", maxlength = 30, required = true, on = "ulozitHeslo"),
			@Validate(field = "noveHeslo2", maxlength = 30, required = true, on = "ulozitHeslo"),
			})

	private Sws008Bean actionBean;
	
	@DefaultHandler
	public Resolution resolution() {
		String username = (String) session().getAttribute("username");
		Boolean nasetovany = (Boolean)session().getAttribute("nasetovany");
		if(username != null && !nasetovany) {
			prvotniNasetovani(username);
		} else {
			return new RedirectResolution(Sws000.class);
		}
		return new ForwardResolution(CURRENT_JSP);
	}
	
	private void prvotniNasetovani(String username) {
		try {
			Uzivatel uziv = opravneni.najdiUzivatele(username, (String)(session().getAttribute("password")));
			if(uziv.getIdZakaznik() != null) {
				actionBean.setUzivatel(opravneni.najdiZakaznika(uziv.getIdZakaznik()));
				actionBean.setTelCislo(actionBean.getUzivatel().getTelefon());
				session().setAttribute("nasetovanvy", true);
			}
			else if(uziv.getIdZamestnanec() != null) {
				actionBean.setUzivatel(opravneni.najdiZamestnance(uziv.getIdZamestnanec()));
				session().setAttribute("nasetovanvy", true);
			}
		} catch (BasicServiceException e) {
			msgE(e.getId(), e.getParams());
		}
	}

	public Resolution editovatAdresu() {
		actionBean.setZmenitAdresu(!actionBean.isZmenitAdresu());
		return new RedirectResolution(getClass());
	}
	
	public Resolution editovatHeslo() {
		actionBean.setZmenitHeslo(!actionBean.isZmenitHeslo());
		return new RedirectResolution(getClass());
	}
	
	public Resolution ulozitAdresu() {
		try {
			opravneni.ulozitAdresuZakaznika(actionBean.getUzivatel());
			msgI("008.02");
			editovatAdresu();
		} catch (BasicServiceException e) {
			msgE(e.getId(), e.getParams());
		}
		return new RedirectResolution(getClass());
	}
	
	public Resolution ulozitHeslo() {
		try {
			session().removeAttribute("password");
			opravneni.zmenHesloUzivatele(actionBean.getUzivatel().getUsername(), actionBean.getNoveHeslo());
			session().setAttribute("password", actionBean.getNoveHeslo());
			actionBean.getUzivatel().setHeslo(actionBean.getNoveHeslo());
			editovatHeslo();
			msgI("008.03");
		} catch (BasicServiceException e) {
			msgE(e.getId(), e.getParams());
		}			
		return new RedirectResolution(getClass());
	}
	
	public Resolution ulozit() {
		try {
			opravneni.upravitZakaznika(actionBean.getUzivatel());
			msgI("008.01");
		} catch (BasicServiceException e) {
			msgE(e.getId(), e.getParams());
		}
		return new RedirectResolution(getClass());
	}
	
	public Resolution smazat() {
		try {
			opravneni.smazatZakaznika(actionBean.getUzivatel().getIdUzivatel());
			msgI("008.04");
		} catch (BasicServiceException e) {
			msgE(e.getId(), e.getParams());
		}
		return new ForwardResolution("/logout");
	}


	@ValidationMethod(on = "ulozit")
	public void ulozitValidation(ValidationErrors err) {
		
		if(!StringUtils.isAlpha(actionBean.getUzivatel().getPrijmeni())) {
			err.add("actionBean.uzivatel.prijmeni", new LocalizableError("chyba.prijmeni", actionBean.getUzivatel().getPrijmeni()));
		}
		if(!StringUtils.isAlpha(actionBean.getUzivatel().getJmeno())) {
			err.add("actionBean.uzivatel.jmeno", new LocalizableError("chyba.jmeno", actionBean.getUzivatel().getJmeno()));
		}
		String phone = actionBean.getUzivatel().getTelefon();
		if(phone != null) {
			if(!phone.matches("[+]?[()/0-9. -]{9,}$") || !phone.matches("(\\+420)? ?[1-9][0-9]{2} ?[0-9]{3} ?[0-9]{3}$")) {
				err.add("actionBean.uzivatel.telefon", new LocalizableError("chyba.telefon", actionBean.getUzivatel().getTelefon()));			
			}
			if(!actionBean.getUzivatel().getTelefon().equals(actionBean.getTelCislo())) {
				if(opravneni.existujeZakaznikTelefon(actionBean.getUzivatel().getTelefon())) {
					err.add("actionBean.uzivatel.telefon", new LocalizableError("chyba.telefon2", actionBean.getUzivatel().getTelefon()));
				}				
			}
		}
	}

	@ValidationMethod(on = "ulozitHeslo")
	public void ulozitHesloValidation(ValidationErrors err) {
		if(!actionBean.getUzivatel().getHeslo2().equals(actionBean.getUzivatel().getHeslo())) {
			err.add("actionBean.uzivatel.heslo2", new LocalizableError("chyba.heslo1"));
		}

		if (!actionBean.getNoveHeslo().equals(actionBean.getNoveHeslo2())) {
			err.add("actionBean.noveHeslo2", new LocalizableError("chyba.heslo"));
		}
	}
	
	@ValidationMethod(on = "ulozitAdresu")
	public void kontrolaAdresyValidation(ValidationErrors err) {
		try {
			Integer psc = Integer.parseInt(actionBean.getUzivatel().getPsc());
			if(psc == null || psc < 10000) {
				err.add("actionBean.uzivatel.psc", new LocalizableError("chyba.psc", actionBean.getUzivatel().getPsc()));
			}			
		} catch (NumberFormatException e) {
			err.add("actionBean.uzivatel.psc", new LocalizableError("chyba.psc", actionBean.getUzivatel().getPsc()));
		}
			
		if(!StringUtils.isAlpha(actionBean.getUzivatel().getMesto().replaceAll(" ", ""))) {
			err.add("actionBean.uzivatel.mesto", new LocalizableError("chyba.mesto", actionBean.getUzivatel().getMesto()));
		}
		if(!StringUtils.isAlpha(actionBean.getUzivatel().getUlice().replaceAll(" ", ""))) {
			err.add("actionBean.uzivatel.ulice", new LocalizableError("chyba.ulice", actionBean.getUzivatel().getUlice()));
		}		
	}
	
	public Sws008Bean getActionBean() {
		return actionBean;
	}

	public void setActionBean(Sws008Bean actionBean) {
		this.actionBean = actionBean;
	}


}