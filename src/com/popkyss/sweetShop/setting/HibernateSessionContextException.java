package com.popkyss.sweetShop.setting;

public class HibernateSessionContextException extends Exception {
	private static final long serialVersionUID = 1L;

	public HibernateSessionContextException() {
	}

	public HibernateSessionContextException(String message, Throwable cause) {
		super(message, cause);
	}

	public HibernateSessionContextException(String message) {
		super(message);
	}

	public HibernateSessionContextException(Throwable cause) {
		super(cause);
	}
}
