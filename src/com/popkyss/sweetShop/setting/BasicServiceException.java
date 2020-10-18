package com.popkyss.sweetShop.setting;

public class BasicServiceException extends Exception {
	private static final long serialVersionUID = 1L;
	private String id;
	private Object[] params;

	public BasicServiceException(String id, String message, Object... params) {
		super(message);
		this.id = id;
		this.params = params;
	}

	public BasicServiceException(String id, String[] params, String message) {
		this(id, message, (Object[]) params);
	}

	public static BasicServiceException create(String id, String[] params, String message) {
		return new BasicServiceException(id, message, (Object[]) params);
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object[] getParams() {
		return this.params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
}