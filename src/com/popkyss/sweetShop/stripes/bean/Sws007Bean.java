package com.popkyss.sweetShop.stripes.bean;


import com.popkyss.sweetShop.setting.AbstractBean;


public class Sws007Bean extends AbstractBean {
	
	private static final long serialVersionUID = 1L;


	private String username;
	private String heslo;
	private String email;
	private boolean zmenHeslo = false;
	
	
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getHeslo() {
		return heslo;
	}
	
	public void setHeslo(String heslo) {
		this.heslo = heslo;
	}
	
	public boolean isZmenHeslo() {
		return zmenHeslo;
	}
	
	public void setZmenHeslo(boolean zmenHeslo) {
		this.zmenHeslo = zmenHeslo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
