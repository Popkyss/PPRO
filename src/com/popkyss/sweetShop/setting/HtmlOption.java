package com.popkyss.sweetShop.setting;

import java.io.Serializable;

public class HtmlOption<Key, Value> implements Serializable {
	private static final long serialVersionUID = 1044474671755131103L;
	private Key key;
	private Value value;
	private boolean selected = false;

	public HtmlOption(Key key, Value value) {
		this.key = key;
		this.value = value;
	}

	public HtmlOption(Key key, Value value, boolean selected) {
		this.key = key;
		this.value = value;
		this.selected = selected;
	}

	public boolean isSelected() {
		return this.selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Key getKey() {
		return this.key;
	}

	public Value getValue() {
		return this.value;
	}

	public String getValueHtmlSafe() {
		return StringUtils.stringToHtml(this.value.toString());
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public void setValue(Value value) {
		this.value = value;
	}
}