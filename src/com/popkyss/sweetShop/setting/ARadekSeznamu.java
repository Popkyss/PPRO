package com.popkyss.sweetShop.setting;

public abstract class ARadekSeznamu implements IRadekSeznamu {
	private static final long serialVersionUID = 1L;
	public static final byte NOT_FOUND = 0;
	public static final byte APPROXIMATELY_FOUND = 1;
	public static final byte FOUND = 2;
	protected int poradi;
	protected byte find = 0;

	public int getPoradi() {
		return this.poradi;
	}

	public void setPoradi(int poradi) {
		this.poradi = poradi;
	}

	public boolean isEven() {
		return (this.poradi % 2 == 0);
	}

	public boolean isFound() {
		return (this.find == 2);
	}

	public boolean isApFound() {
		return (this.find == 1);
	}

	public byte getFind() {
		return this.find;
	}

	public void setFind(byte find) {
		this.find = find;
	}
}