package com.popkyss.sweetShop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.text.RandomStringGenerator;

import com.popkyss.sweetShop.dao.IAdresaDao;
import com.popkyss.sweetShop.dao.IKosikDao;
import com.popkyss.sweetShop.dao.ILoggerDao;
import com.popkyss.sweetShop.dao.IPolozkaDao;
import com.popkyss.sweetShop.dao.IZakaznikDao;
import com.popkyss.sweetShop.dao.IZamestnanecDao;
import com.popkyss.sweetShop.dao.SweetShopDaoFactory;
import com.popkyss.sweetShop.entity.Adresa;
import com.popkyss.sweetShop.entity.Kosik;
import com.popkyss.sweetShop.entity.Logger;
import com.popkyss.sweetShop.entity.Polozka;
import com.popkyss.sweetShop.entity.Zakaznik;
import com.popkyss.sweetShop.entity.Zamestnanec;
import com.popkyss.sweetShop.model.PrepravkaUzivatel;
import com.popkyss.sweetShop.model.Uzivatel;
import com.popkyss.sweetShop.service.IOpravneni;
import com.popkyss.sweetShop.setting.BaseServiceFactory;
import com.popkyss.sweetShop.setting.BasicServiceException;
import com.popkyss.sweetShop.setting.IDateTime;


public class OpravneniImpl implements IOpravneni {

	private final IZamestnanecDao zamestnanecDao = SweetShopDaoFactory.getZamestnanecDao();
	private final IZakaznikDao zakaznikDao = SweetShopDaoFactory.getZakaznikDao();
	private final IKosikDao kosikDao = SweetShopDaoFactory.getKosikDao();
	private final IAdresaDao adresaDao = SweetShopDaoFactory.getAdresaDao();
	private final ILoggerDao loggerDao = SweetShopDaoFactory.getLoggerDao();
	private final IDateTime dateTime = BaseServiceFactory.getDateTime();
	private final IPolozkaDao polozkaDao = SweetShopDaoFactory.getPolozkaDao();

	
	@Override
	public boolean existujeUzivatel(String username, String password) {
		return loggerDao.existUser(username, password);
	}
	
	@Override
	public boolean existujeZakaznikEmail(String email) {
		return zakaznikDao.existujeEmail(email);
	}
	
	@Override
	public boolean existujeZakaznikTelefon(String telefon) {
		return zakaznikDao.existujeTelefon(telefon);
	}
	
	@Override
	public boolean existujeZakaznikUsername(String username) {
		return zakaznikDao.existujeUsername(username);
	}
	
	@Override
	public boolean existujeZamestnanecEmail(String email) {
		return zamestnanecDao.existujeEmail(email);
	}
	
	@Override
	public boolean existujeZamestnanecTelefon(String telefon) {
		return zamestnanecDao.existujeTelefon(telefon);
	}
	
	@Override
	public boolean existujeZamestnanecUsername(String username) {
		return zamestnanecDao.existujeUsername(username);
	}
	
	@Override
	public Uzivatel najdiUzivatele(String uname, String pass) {
		return loggerDao.najdiUserLogin(uname, pass);
	}
	
	@Override
	public List<Uzivatel> najdiVsechnyZakazniky() {
		List<Zakaznik> zakaznikS = zakaznikDao.findAll();
		List<Uzivatel> uzivatele = new ArrayList<Uzivatel>();
		for (Zakaznik zak : zakaznikS) {
			Uzivatel u = new Uzivatel();
			u.setJmeno(zak.getJmeno() + " " + zak.getPrijmeni());
			u.setUsername(zak.getUsername());
			u.setTelefon(zak.getTelefon());
			u.setEmail(zak.getEmail());
			u.setIdZakaznik(zak.getIdZakaznik());
			uzivatele.add(u);
		}
		return uzivatele;
	}
	
	@Override
	public List<Uzivatel> najdiVsechnyZamestnance() {
		List<Zamestnanec> zamestnanecS = zamestnanecDao.findAll();
		List<Uzivatel> uzivatele = new ArrayList<Uzivatel>();
		for (Zamestnanec zam : zamestnanecS) {
			Uzivatel u = new Uzivatel();
			u.setJmeno(zam.getJmeno() + " " + zam.getPrijmeni());
			u.setUsername(zam.getUsername());
			u.setTelefon(zam.getTelefon());
			u.setEmail(zam.getEmail());
			u.setIdZamestnanec(zam.getIdZamestnanec());
			uzivatele.add(u);
		}
		return uzivatele;
	}
	
	@Override
	public PrepravkaUzivatel najdiZakaznika(Integer idZakaznik) throws BasicServiceException {
		Zakaznik zak = zakaznikDao.find(idZakaznik);
		Logger login = loggerDao.najdiUzivatele(zak.getUsername());
		
		if(login.getIdZakaznik() == null) {
			throw new BasicServiceException(ZAK01, "Zakaznik s username: " + zak.getUsername() +" nema platny login", zak.getUsername());
		}
		return nasetujZakaznika(zak, login);
	}
		
	private PrepravkaUzivatel nasetujZakaznika(Zakaznik zak, Logger login) {
		PrepravkaUzivatel uziv = new PrepravkaUzivatel();
		uziv.setIdUzivatel(zak.getIdZakaznik());
		uziv.setUsername(zak.getUsername());
		uziv.setEmail(zak.getEmail());
		uziv.setJmeno(zak.getJmeno());
		uziv.setPrijmeni(zak.getPrijmeni());
		uziv.setTelefon(zak.getTelefon());
		uziv.setHeslo(login.getHeslo());
		
		uziv.setIdAdresa(zak.getIdAdresa().getIdAdresa());
		if(zak.getIdAdresa().getCp().contains("/")) {
			String[] cp = zak.getIdAdresa().getCp().split("/");
			uziv.setCp(Integer.parseInt(cp[0]));
			uziv.setCp2(Integer.parseInt(cp[1]));
		} else {
			uziv.setCp(Integer.parseInt(zak.getIdAdresa().getCp()));			
		}		
		uziv.setMesto(zak.getIdAdresa().getMesto());
		uziv.setUlice(zak.getIdAdresa().getUlice());
		uziv.setPsc(zak.getIdAdresa().getPsc()+"");
		
		return uziv;
	}
	
	@Override
	public PrepravkaUzivatel najdiZamestnance(Integer idZamestnance) throws BasicServiceException {
		Zamestnanec zam = zamestnanecDao.find(idZamestnance);
		Logger login = loggerDao.najdiUzivatele(zam.getUsername());
		
		if(login.getIdZamestnanec() == null) {
			throw new BasicServiceException(ZAM01, "Zamestnanec s username: " + zam.getUsername() +" nema platny login", zam.getUsername());
		}
		return nasetujZamestnance(zam, login);
	}
		
	private PrepravkaUzivatel nasetujZamestnance(Zamestnanec zam, Logger login) {	
		PrepravkaUzivatel uziv = new PrepravkaUzivatel();
		uziv.setIdUzivatel(zam.getIdZamestnanec());
		uziv.setUsername(zam.getUsername());
		uziv.setEmail(zam.getEmail());
		uziv.setJmeno(zam.getJmeno());
		uziv.setPrijmeni(zam.getPrijmeni());
		uziv.setTelefon(zam.getTelefon());
		uziv.setHeslo(login.getHeslo());

		uziv.setIdAdresa(zam.getIdAdresa().getIdAdresa());		
		if(zam.getIdAdresa().getCp().contains("/")) {
			String[] cp = zam.getIdAdresa().getCp().split("/");
			uziv.setCp(Integer.parseInt(cp[0]));
			uziv.setCp2(Integer.parseInt(cp[1]));
		} else {
			uziv.setCp(Integer.parseInt(zam.getIdAdresa().getCp()));			
		}		
		uziv.setMesto(zam.getIdAdresa().getMesto());
		uziv.setUlice(zam.getIdAdresa().getUlice());
		uziv.setPsc(zam.getIdAdresa().getPsc()+"");
		
		return uziv;
	}
	
	@Override
	public void smazatZakaznika(Integer idZakaznika) throws BasicServiceException {
		Zakaznik zak = zakaznikDao.find(idZakaznika);
		if(zak == null) {
			throw new BasicServiceException(ZAK02, "Zakaznik nebyl nalezen", idZakaznika);
		}
		Adresa adr = zak.getIdAdresa();
		if(adr == null) {
			throw new BasicServiceException(ADR01, "Adresa neexistuje");
		}
		Logger log = loggerDao.najdiUzivatele(zak.getUsername());
		if(log == null) {
			throw new BasicServiceException(USER02, "Uzivatel s username: " + zak.getUsername() +" nema platny login", zak.getUsername()); 
		}
		
		Kosik kos = kosikDao.find(zak.getIdKosik().getIdKosik());
		Set<Polozka> polozky = kos.getPolozkas();
		for(Polozka p : polozky) {
			polozkaDao.delete(p);
		}
		kosikDao.delete(kos);
		loggerDao.delete(log);
		zakaznikDao.delete(zak);
		adresaDao.delete(adr);
	}
	
	@Override
	public void smazatZamestnance(Integer idZamestnanec) throws BasicServiceException {
		Zamestnanec zam = zamestnanecDao.find(idZamestnanec);
		if(zam == null) {
			throw new BasicServiceException(ZAM02, "Zamestnanec nebyl nalezen", idZamestnanec);
		}
		Adresa adr = zam.getIdAdresa();
		if(adr == null) {
			throw new BasicServiceException(ADR01, "Adresa neexistuje");
		}
		Logger log = loggerDao.najdiUzivatele(zam.getUsername());
		if(log == null) {
			throw new BasicServiceException(USER02, "Uzivatel s username: " + zam.getUsername() +" nema platny login", zam.getUsername()); 
		}
		loggerDao.delete(log);
		zamestnanecDao.delete(zam);
		adresaDao.delete(adr);
	}
	
	@Override
	public void ulozitAdresuZakaznika(PrepravkaUzivatel zakaznik) throws BasicServiceException {
		Zakaznik zak = zakaznikDao.find(zakaznik.getIdUzivatel());
		if(zak == null) {
			throw new BasicServiceException(ZAK02, "Zakaznik nebyl nalezen", zakaznik.getIdUzivatel());
		}
		Integer psc = Integer.parseInt(zakaznik.getPsc());
		String cp = zakaznik.getCp().toString();
		if(zakaznik.getCp2() != null) {
			cp += "/" + zakaznik.getCp2(); 
		}
		Adresa adr = zak.getIdAdresa();
		adr.setMesto(zakaznik.getMesto());
		adr.setUlice(zakaznik.getUlice());
		adr.setPsc(psc);
		adr.setCp(cp);		
	}
	
	@Override
	public void ulozitAdresuZamestnance(PrepravkaUzivatel zamestnanec) throws BasicServiceException {
		Zamestnanec zam = zamestnanecDao.find(zamestnanec.getIdUzivatel());
		if(zam == null) {
			throw new BasicServiceException(ZAM02, "Uzivatel nebyl nalezen", zamestnanec.getIdUzivatel());
		}
		Integer psc = Integer.parseInt(zamestnanec.getPsc());
		String cp = zamestnanec.getCp().toString();
		if(zamestnanec.getCp2() != null) {
			cp += "/" + zamestnanec.getCp2(); 
		}
		Adresa adr = zam.getIdAdresa();
		adr.setMesto(zamestnanec.getMesto());
		adr.setUlice(zamestnanec.getUlice());
		adr.setPsc(psc);
		adr.setCp(cp);		
	}
	
	@Override
	public void upravitZakaznika(PrepravkaUzivatel zakaznik) throws BasicServiceException {
		Zakaznik zak = zakaznikDao.find(zakaznik.getIdUzivatel());
		if(zak == null) {
			throw new BasicServiceException(ZAK02, "Zakaznik nebyl nalezen", zakaznik.getIdUzivatel());
		}
		zak.setJmeno(zakaznik.getJmeno());
		zak.setPrijmeni(zakaznik.getPrijmeni());
		zak.setTelefon(zakaznik.getTelefon());
	}
	
	@Override
	public void upravitZamestnance(PrepravkaUzivatel uzivatel) throws BasicServiceException {
		Zamestnanec zam = zamestnanecDao.find(uzivatel.getIdUzivatel());
		if(zam == null) {
			throw new BasicServiceException(ZAM02, "Zamestnanec nebyl nalezen", uzivatel.getIdUzivatel());
		}
		zam.setJmeno(uzivatel.getJmeno());
		zam.setPrijmeni(uzivatel.getPrijmeni());
		zam.setTelefon(uzivatel.getTelefon());
	}

	@Override
	public void vytvorZakaznika(PrepravkaUzivatel zakaznik) throws BasicServiceException {
		if (loggerDao.existUser(zakaznik.getUsername(), zakaznik.getHeslo())) {
			throw new BasicServiceException(USER01, "Uzivatel s username: " + zakaznik.getUsername()+ " jiz existuje", zakaznik.getUsername());
		}				
		Integer idAdresa = ulozAdresu(zakaznik);
		Integer idZakaznik = zalozZakaznika(zakaznik, idAdresa);
		
		Logger login = new Logger();
		login.setHeslo(zakaznik.getHeslo());
		login.setUname(zakaznik.getUsername());
		login.setIdZakaznik(idZakaznik);
		loggerDao.persist(login);
	}
	
	private int zalozZakaznika(PrepravkaUzivatel zakaznik, Integer idAdresa) {
		Adresa adresa = adresaDao.find(idAdresa);
		if(zakaznikDao.existujeUsername(zakaznik.getUsername())) {
			new BasicServiceException(ZAK03, "Zakaznik se zadanym username jiz existuje", zakaznik.getUsername());
		}
		Kosik kosik = new Kosik();
		Zakaznik zak = new Zakaznik();
		zak.setIdAdresa(adresa);
		zak.setEmail(zakaznik.getEmail());
		zak.setUsername(zakaznik.getUsername());
		zak.setJmeno(zakaznik.getJmeno());
		zak.setPrijmeni(zakaznik.getPrijmeni());
		zak.setTelefon(zakaznik.getTelefon());
		
		zak.setNarozeni(dateTime.getCurrentDate());
		zak.setRegistrace(dateTime.getCurrentDate());
		zak.setIdKosik(kosik);
		kosik.setIdZakaznik(zak);
		kosikDao.persist(kosik);
		int id = zakaznikDao.persist(zak);
		return id;
	}
	
	@Override
	public void vytvorZamestnance(PrepravkaUzivatel zamestnanec) throws BasicServiceException {
		if (loggerDao.existUser(zamestnanec.getUsername(), zamestnanec.getHeslo())) {
			throw new BasicServiceException(USER01, "Uzivatel s username: " + zamestnanec.getUsername()+ " jiz existuje", zamestnanec.getUsername());
		}				
		Integer idAdresy = ulozAdresu(zamestnanec);
		Integer idZamestnance = zalozZamestnance(zamestnanec, idAdresy);
		
		Logger login = new Logger();
		login.setHeslo(zamestnanec.getHeslo());
		login.setUname(zamestnanec.getUsername());
		login.setIdZamestnanec(idZamestnance);
		loggerDao.persist(login);
	}
	
	private Integer zalozZamestnance(PrepravkaUzivatel zamestnanec, Integer idAdresa) {
		Adresa adresa = adresaDao.find(idAdresa);
		if(zamestnanecDao.existujeUsername(zamestnanec.getUsername())) {
			new BasicServiceException(ZAM03, "Uzivatel se zadanym username jiz existuje", zamestnanec.getUsername());
		}
		Zamestnanec zam = new Zamestnanec();
		zam.setIdAdresa(adresa);
		zam.setEmail(zamestnanec.getEmail());
		zam.setUsername(zamestnanec.getUsername());
		zam.setJmeno(zamestnanec.getJmeno());
		zam.setPrijmeni(zamestnanec.getPrijmeni());
		zam.setTelefon(zamestnanec.getTelefon());

		int id = zamestnanecDao.persist(zam);
		return id;
	}
	
	private Integer ulozAdresu(PrepravkaUzivatel zak) {
		Integer psc = Integer.parseInt(zak.getPsc());
		String cp = zak.getCp().toString();
		if(zak.getCp2() != null) {
			cp += "/" + zak.getCp2(); 
		}
		Adresa adr = new Adresa();
		adr.setMesto(zak.getMesto());
		adr.setUlice(zak.getUlice());
		adr.setCp(cp);
		adr.setPsc(psc);
		int id = adresaDao.persist(adr);
		return id;
	}
	
	@Override
	public void zmenHesloUzivatele(String username, String heslo) throws BasicServiceException {
		Logger login = loggerDao.najdiUzivatele(username);
		if(login == null) {
			throw new BasicServiceException(USER03, "Uzivatel s username: "+ username + " nebyl nalezen", username);
		}
		login.setHeslo(heslo);
	}
	
	@Override
	public void zmenHesloUzivateleDleEmailu(String email) throws AddressException, MessagingException, BasicServiceException {
		String newPassword = generateRandomSpecialCharacters(10);
		String subject = "Změna hesla na webu SweetShop";
		String text = "Bylo zažádáno o změnu hesla. Vaše nové heslo je: " + newPassword;
		
		if(zamestnanecDao.existujeEmail(email)) {
			Zamestnanec zam = zamestnanecDao.najdiDleEmailu(email);
			Logger login = loggerDao.najdiUzivatele(zam.getUsername());
			login.setHeslo(newPassword);
		}
		if(zakaznikDao.existujeEmail(email)) {
			Zakaznik zak = zakaznikDao.najdiDleEmailu(email);
			Logger login = loggerDao.najdiUzivatele(zak.getUsername());
			if(login == null) {
				throw new BasicServiceException(USER03, "Uzivatel s username: "+ zak.getUsername() + " nebyl nalezen", zak.getUsername());
			}
			login.setHeslo(newPassword);
		}
		sendEmail(email, subject, text);
	}
	
	private void sendEmail(String email, String subject, String textMsg) throws AddressException, MessagingException {
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";		
		final String from = "honzapopkyss@gmail.com";
		final String heslo = "Specialized";
		
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");
		Session session = Session.getDefaultInstance(props, 
				new Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, heslo);
			}});
		
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
		message.setSubject(subject);
		message.setText(textMsg);
		message.setSentDate(new Date());
		Transport.send(message);
		System.out.println("Sent message successfully....");
	}
	
	private String generateRandomSpecialCharacters(int length) {
	    RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(33, 45)
	        .build();
	    return pwdGenerator.generate(length);
	}
}
