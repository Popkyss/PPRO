package com.popkyss.sweetShop.setting.ltd.dto;

import java.io.Serializable;
import java.text.MessageFormat;


public class DialogData
implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String methodOk;
	private String methodStorno;
	private String message;
	private Boolean potvrzeno;
	private ETypDialogovehoOkna typ;
	private Boolean zobrazit;
	
	public enum ETypDialogovehoOkna
	{
		INFO
		{
			public String toString()
			{
				return "i";
			}
		},
		WARNING
		{
			public String toString()
			{
				return "w";
			}
		},
		ERROR
		{
			public String toString()
			{
				return "e";
			}
		};
		
		public int getOrdinal() {
			return ordinal();
		}
	}
	
	public DialogData() {
		this.message = null;
		this.methodOk = "";
		this.methodStorno = "";
		this.potvrzeno = null;
		setZobrazit(Boolean.valueOf(false));
	}
	
	
	public String getMethodOk() {
		return this.methodOk;
	}
	
	
	public String getMethodStorno() {
		return this.methodStorno;
	}
	
	
	public String getMessage() {
		return this.message;
	}
	
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public void show(String metodaOK, String metodaStorno, String message, Object... params) {
		show(ETypDialogovehoOkna.WARNING, metodaOK, metodaStorno, message, params);
	}
	
	public void show(ETypDialogovehoOkna typ, String metodaOK, String metodaStorno, String message, Object... params) {
		this.typ = typ;
		this.methodOk = metodaOK;
		this.methodStorno = metodaStorno;
		if (params.length > 0) {
			try {
				this.message = MessageFormat.format(message, params);
			} catch (IllegalArgumentException e) {
				this.message = message;
			} 
		} else {
			this.message = message;
		} 
		this.potvrzeno = null;
		this.zobrazit = Boolean.valueOf(true);
	}
	
	public void hide() {
		this.message = null;
		this.methodOk = "";
		this.methodStorno = "";
		this.potvrzeno = null;
		this.zobrazit = Boolean.valueOf(false);
	}
	
	public void setPotvrzeno(Boolean potvrzeno) {
		this.potvrzeno = potvrzeno;
	}
	
	
	public Boolean isPotvrzeno() {
		return this.potvrzeno;
	}
	
	
	public ETypDialogovehoOkna getTyp() {
		return this.typ;
	}
	
	
	public void setTyp(ETypDialogovehoOkna typ) {
		this.typ = typ;
	}
	
	
	public void setZobrazit(Boolean zobrazit) {
		this.zobrazit = zobrazit;
	}
	
	
	public Boolean getZobrazit() {
		return this.zobrazit;
	}
}
