package com.popkyss.sweetShop.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.popkyss.sweetShop.model.PrepravkaUzivatel;
import com.popkyss.sweetShop.model.Uzivatel;
import com.popkyss.sweetShop.setting.BasicServiceException;

public interface IOpravneni {

	//Adresa neexistuje
	public static final String ADR01 = "adr.01";
	
	//Zakaznik nema platny login
	public static final String ZAK01 = "zak.01";
	
	//Zakaznik nebyl nalezen
	public static final String ZAK02 = "zak.02";
	
	//Zakaznik jiz existuje
	public static final String ZAK03 = "zak.03";
	
	//Zamestnanec nema platny login
	public static final String ZAM01 = "zam.01";
	
	//Zamestnanec nebyl nalezen
	public static final String ZAM02 = "zam.02";
	
	//Zamestnanec jiz existuje	
	public static final String ZAM03 = "zam.03";
	
	//Uzivatel jiz existuje
	public static final String USER01 = "user.01";
	
	//Uzivatel nema platny login
	public static final String USER02 = "user.02";

	//Uzivatel nebyl nalezen
	public static final String USER03 = "zam.03";
	
	
	/**
	 * Metoda, ktera overuje existenci uzivatele
	 * @param username, prihlasovaci jmeno
	 * @param password, heslo uzivatele
	 * @return true, pokud uzivatel existuje
	 */
	public boolean existujeUzivatel(String username, String password);
	
	/**
	 * Metoda, ktera overi existenci emailu u zakazniku
	 * @param email, testovany email 
	 * @return true, pokud existuje
	 */
	public boolean existujeZakaznikEmail(String email);
	
	/**
	 * Metoda, ktera overi existenci telefonu u zakazniku
	 * @param telefon, telefonni cislo
	 * @return true, pokud existuje
	 */
	public boolean existujeZakaznikTelefon(String telefon);
	
	/**
	 * Metoda, ktera overi existenci username u zakazniku
	 * @param username, prihlasovaci jmeno zakaznika
	 * @return true, pokud existuje
	 */
	public boolean existujeZakaznikUsername(String username);
	
	/**
	 * Metoda, ktera overi existenci emaulu u zamestnancu
	 * @param email, testovany email
	 * @return true, pokud existuje
	 */
	public boolean existujeZamestnanecEmail(String email);
	
	/**
	 * Metoda, ktera overi existenci telefonu u zamestnance
	 * @param telefon, telefonni cislo
	 * @return true, pokud existuje
	 */
	public boolean existujeZamestnanecTelefon(String telefon);
	
	/**
	 * Metoda, ktera overi existenci username u zamestnance
	 * @param username, prihlasovaci jmeno zamestnance
	 * @return true, pokud existuje
	 */
	public boolean existujeZamestnanecUsername(String username);
	
	/**
	 * Metoda, ktera najde uzivatele dle hesla a username
	 * @param username, prihlasovaci jmeno
	 * @param password, heslo uzivatele
	 * @return true, pokud uzivatel existuje
	 */
	public Uzivatel najdiUzivatele(String username, String password);
	
	/**
	 * Metoda, ktera najde vsechny zakazniky
	 * @return prepravka se zakazniky
	 */
	public List<Uzivatel> najdiVsechnyZakazniky();

	/**
	  * Metoda, ktera najde vsechny zamestnance
	  * @return prepravka se zamestnanci
	  */
	public List<Uzivatel> najdiVsechnyZamestnance();

	/**
	 * Metoda, ktera najde zakaznika pro editaci
	 * @param idZakaznik, zakaznik id
	 * @return naplneny zakaznik
	 * @throws BasicServiceException
	 */
	public PrepravkaUzivatel najdiZakaznika(Integer idZakaznik) throws BasicServiceException;
	
	/**
	 * Metoda, ktera najde uzivatele pro editaci
	 * @param idZamestnance, zamestnanec id
	 * @return naplneny zamestnanec
	 * @throws BasicServiceException
	 */
	public PrepravkaUzivatel najdiZamestnance(Integer idZamestnance) throws BasicServiceException;
	
	/**
	 * Metoda, ktera odstrani zakaznika
	 * @param idZakaznika, identifikator zakaznika
	 * @throws BasicServiceException
	 */
	public void smazatZakaznika(Integer idZakaznika) throws BasicServiceException;
	
	/**
	 * Metoda, ktera odstrani zamestnance
	 * @param idZamestnanec, identifikator zamestnance
	 * @throws BasicServiceException
	 */
	public void smazatZamestnance(Integer idZamestnanec) throws BasicServiceException;
	
	/**
	 * Metoda, ktera ulozi adresu zakaznikovi
	 * @param zakaznik, prepravka zakaznika
	 * @throws BasicServiceException
	 */
	public void ulozitAdresuZakaznika(PrepravkaUzivatel zakaznik) throws BasicServiceException;
	
	/**
	 * Metoda, ktera ulozi adresu zamestnanci
	 * @param zamestnanec, prepravka zamestnance
	 * @throws BasicServiceException
	 */
	public void ulozitAdresuZamestnance(PrepravkaUzivatel zamestnanec) throws BasicServiceException;
	
	/**
	 * Metoda, ktera upravi zakaznika
	 * @param zakaznik, prepravka zakaznika
	 * @throws BasicServiceException
	 */
	public void upravitZakaznika(PrepravkaUzivatel zakaznik) throws BasicServiceException;
	
	/**
	 * Metoda, ktera upravi zamestnance
	 * @param zamestnanec, prepravka zamestnance
	 * @throws BasicServiceException
	 */
	public void upravitZamestnance(PrepravkaUzivatel zamestnanec) throws BasicServiceException;
	
	/**
	 * Metoda, ktera vytvori zakaznika a jeho login
	 * @param zakaznik, prepravka s udaji o zakaznikovi
	 * @throws BasicServiceException
	 */
	public void vytvorZakaznika(PrepravkaUzivatel zakaznik) throws BasicServiceException;
	
	/**
	 * Metoda, ktera vytvori zamestnance a jeho login
	 * @param zamestnanec, prepravka s udaji o zamestnanci
	 * @throws BasicServiceException
	 */
	public void vytvorZamestnance(PrepravkaUzivatel zamestnanec) throws BasicServiceException;
	
	/**
	 * Metoda, ktera zmeni uzivateli heslo
	 * @param username, username uzivatele
	 * @param heslo, nove heslo
	 * @throws BasicServiceException
	 */
	public void zmenHesloUzivatele(String username, String heslo) throws BasicServiceException;
	
	/**
	 * Metoda, ktera zmeni heslo uzivateli a odesle ho na jeho mail
	 * @param email, email uzivatele 
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws BasicServiceException 
	 */
	public void zmenHesloUzivateleDleEmailu(String email) throws AddressException, MessagingException, BasicServiceException;
}
