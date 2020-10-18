package com.popkyss.sweetShop.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.popkyss.sweetShop.dao.IKosikDao;
import com.popkyss.sweetShop.dao.IPolozkaDao;
import com.popkyss.sweetShop.dao.IProduktDao;
import com.popkyss.sweetShop.dao.IZakaznikDao;
import com.popkyss.sweetShop.dao.SweetShopDaoFactory;
import com.popkyss.sweetShop.entity.Kosik;
import com.popkyss.sweetShop.entity.Polozka;
import com.popkyss.sweetShop.entity.Produkt;
import com.popkyss.sweetShop.entity.Zakaznik;
import com.popkyss.sweetShop.model.PrepravkaKosik;
import com.popkyss.sweetShop.model.PrepravkaPolozky;
import com.popkyss.sweetShop.model.PrepravkaProdukt;
import com.popkyss.sweetShop.service.IZbozi;
import com.popkyss.sweetShop.setting.BasicServiceException;


public class ZboziImpl implements IZbozi {

	private final IZakaznikDao zakaznikDao = SweetShopDaoFactory.getZakaznikDao();
	private final IPolozkaDao polozkaDao = SweetShopDaoFactory.getPolozkaDao();
	private final IProduktDao produktDao = SweetShopDaoFactory.getProduktDao();
	private final IKosikDao kosikDao = SweetShopDaoFactory.getKosikDao();
	
	
	public PrepravkaKosik najdiKosikZakaznika(Integer idZakaznika) {
		Zakaznik zak = zakaznikDao.find(idZakaznika);
		Kosik ko = zak.getIdKosik();
		PrepravkaKosik prepravka = new PrepravkaKosik();
		prepravka.setIdKosik(ko.getIdKosik());
		
		Long pocet = polozkaDao.spocitejPocetPolozek(zak.getIdZakaznik());
		
		prepravka.setIdZakaznik(zak.getIdZakaznik());
		if(pocet != null) {
			prepravka.setPocet(pocet.intValue());			
		} else {
			prepravka.setPocet(0);
		}
		return prepravka;
	}
	
	
	public List<PrepravkaPolozky> najdiPolozkyKosiku(Integer idKosik){
		List<Polozka> polozky = polozkaDao.najdiPolozkyKosiku(idKosik);
		List<PrepravkaPolozky> list = new ArrayList<PrepravkaPolozky>();
		
		for(Polozka p:polozky) {
			PrepravkaPolozky prep = new PrepravkaPolozky();
			prep.setIdKosik(idKosik);
			prep.setIdProdukt(p.getIdProdukt().getIdProdukt());
			prep.setNazev(p.getIdProdukt().getNazev());
			prep.setPocet(p.getPocet());
			int cena = p.getIdProdukt().getCena()*p.getPocet();
			prep.setCena(cena);
			prep.setIdPolozka(p.getIdPolozka());
			list.add(prep);
		}
		return list;
	}
	
	
	public boolean odebratPolozku(Integer idPolozka) throws BasicServiceException {
		Polozka pol = polozkaDao.find(idPolozka);
		if(pol == null) {
			throw new BasicServiceException(POL01, "Polozka s id: " + idPolozka + " neexistuje", idPolozka);
		}
		if(pol.getPocet() == 1) {
			polozkaDao.delete(pol);
			return true;
		} else if(pol.getPocet() > 1) {
			pol.setPocet(pol.getPocet()-1);
			return false;
		}
		return true;
	}
	
	
	public List<PrepravkaProdukt> najdiProdukty() {
		List<Produkt> produkty = produktDao.findAll();
		List<PrepravkaProdukt> list = new ArrayList<PrepravkaProdukt>();
		
		for(Produkt p : produkty) {
			PrepravkaProdukt prep = new PrepravkaProdukt();
			prep.setIdProdukt(p.getIdProdukt());
			prep.setNazev(p.getNazev());
			prep.setCena(p.getCena());
			list.add(prep);
		}
		return list;
	}
	
	public PrepravkaProdukt najdiProdukt(Integer idProdukt) throws BasicServiceException {
		Produkt p = produktDao.find(idProdukt);
		if(p == null) {
			throw new BasicServiceException(PROD01, "Produkt s id: " + idProdukt + " neexistuje.", idProdukt);
		}
		
		PrepravkaProdukt prep = new PrepravkaProdukt();
		prep.setIdProdukt(p.getIdProdukt());
		prep.setNazev(p.getNazev());
		prep.setCena(p.getCena());
		
		return prep;
	}
	
	public boolean pridatDoKosiku(Integer idKosik, PrepravkaProdukt produkt) throws BasicServiceException {
		Kosik k = kosikDao.find(idKosik);
		if(k == null) {
			throw new BasicServiceException(KOS01, "Kosik s id: " + idKosik + " neexistuje.", idKosik);
		}

		List<Polozka> polozky = polozkaDao.najdiPolozkyKosiku(idKosik);
		
		for(Polozka p : polozky) {
			if(p.getIdProdukt().getIdProdukt() == produkt.getIdProdukt()) {
				p.setPocet(p.getPocet() + 1);
				return false;
			}
		}
		
		Polozka p = new Polozka();
		p.setIdKosik(k);
		Produkt prod = produktDao.find(produkt.getIdProdukt());
		p.setIdProdukt(prod);
		p.setCena(produkt.getCena());
		p.setPocet(1);
		
		polozkaDao.persist(p);
		return true;
		
	}
	
}
