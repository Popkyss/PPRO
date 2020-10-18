package com.popkyss.sweetShop.stripes.bean;


import com.popkyss.sweetShop.model.PrepravkaUzivatel;
import com.popkyss.sweetShop.setting.AbstractBean;


public class Sws004Bean extends AbstractBean {
	
	private static final long serialVersionUID = 1L;


	private String noveHeslo;
	private String noveHeslo2;
	private String telCislo;
	private boolean zmenitAdresu = false;
	private boolean zmenitHeslo = false;
	private PrepravkaUzivatel uzivatel = new PrepravkaUzivatel();

	public PrepravkaUzivatel getUzivatel() {
		return uzivatel;
	}

	public void setUzivatel(PrepravkaUzivatel uzivatel) {
		this.uzivatel = uzivatel;
	}

	public boolean isZmenitAdresu() {
		return zmenitAdresu;
	}

	public void setZmenitAdresu(boolean zmenitAdresu) {
		this.zmenitAdresu = zmenitAdresu;
	}

	public boolean isZmenitHeslo() {
		return zmenitHeslo;
	}

	public void setZmenitHeslo(boolean zmenitHeslo) {
		this.zmenitHeslo = zmenitHeslo;
	}

	public String getNoveHeslo() {
		return noveHeslo;
	}

	public void setNoveHeslo(String noveHeslo) {
		this.noveHeslo = noveHeslo;
	}

	public String getNoveHeslo2() {
		return noveHeslo2;
	}

	public void setNoveHeslo2(String noveHeslo2) {
		this.noveHeslo2 = noveHeslo2;
	}

	public String getTelCislo() {
		return telCislo;
	}

	public void setTelCislo(String telCislo) {
		this.telCislo = telCislo;
	}


}
