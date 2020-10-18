package com.popkyss.sweetShop.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Login
 */
@Entity
@Table(name = "LOGIN")
public class Logger implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int idLogin;
	
	private String uname;

	private String heslo;

	private Integer idZamestnanec;
	
	private Integer idZakaznik;
	
	public Logger() {
	}

	@Id
	@Column(name = "LOGIN_ID", unique = true, nullable = false, precision = 8, scale = 0)
	public int getIdLogin() {
		return idLogin;
	}


	public void setIdLogin(int idLogin) {
		this.idLogin = idLogin;
	}	
	
	
	@Column(name = "UNAME", unique = true, nullable = false, length=30)
	public String getUname() {
		return uname;
	}


	public void setUname(String uname) {
		this.uname = uname;
	}

	@Column(name = "HESLO", nullable = false, length=30)
	public String getHeslo() {
		return heslo;
	}


	public void setHeslo(String heslo) {
		this.heslo = heslo;
	}
	
	@Column(name = "ZAKAZNIK_ID", nullable = true, precision = 8, scale = 0)
	public Integer getIdZakaznik() {
		return idZakaznik;
	}


	public void setIdZakaznik(Integer idZakaznik) {
		this.idZakaznik = idZakaznik;
	}
	
	@Column(name = "ZAMESTNANEC_ID", nullable = true, precision = 8, scale = 0)
	public Integer getIdZamestnanec() {
		return idZamestnanec;
	}


	public void setIdZamestnanec(Integer idZamestnanec) {
		this.idZamestnanec = idZamestnanec;
		
	}	

}
