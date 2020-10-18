package com.popkyss.sweetShop.setting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HtmlSelect<Key, Value> implements Serializable {
	private static final long serialVersionUID = 8787657540322555472L;
	protected List<HtmlOption<Key, Value>> options = new ArrayList<HtmlOption<Key, Value>>();

	protected String nazevVybiranePolozky;

	protected Key selected;

	protected int visibleSize;

	protected boolean disabled;
	protected Map<String, Object> attributes = new LinkedHashMap<String, Object>();

	@Deprecated
	public HtmlSelect() {
	}

	public HtmlSelect(String nazevVybiranePolozky) {
		this(nazevVybiranePolozky, 0, false);
	}

	public HtmlSelect(String nazevVybiranePolozky, int visibleSize, boolean disabled) {
		this.nazevVybiranePolozky = nazevVybiranePolozky;
		this.visibleSize = visibleSize;
		this.disabled = disabled;
	}

	public void add(Key key, Value value) {
		/* 63 */ add(key, value, false);
	}

	public void add(Key key, Value value, boolean selected) {
		this.options.add(new HtmlOption<Key, Value>(key, value, selected));
		if (selected) {
			this.selected = key;
		}
	}

	public void setSelected(Key key) {
		for (int i = 0; i < size(); i++) {
			HtmlOption<Key, Value> o = this.options.get(i);
			if (key == null && o.getKey() == null) {
				o.setSelected(true);
				this.selected = null;
			} else if (o.getKey() == null) {
				o.setSelected(false);
			} else if (o.getKey().equals(key)) {
				o.setSelected(true);
				this.selected = o.getKey();
			} else {
				o.setSelected(false);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Key getSelected() {
		for (int i = 0; i < size(); i++) {
			HtmlOption<Key, Value> o = this.options.get(i);
			if (o.isSelected()) {
				return o.getKey();
			}
		}
		if (size() > 0) {
			return (Key) ((HtmlOption) this.options.get(0)).getKey();
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Key getKey(int index) {
		if (isInRange(index)) {
			return (Key) ((HtmlOption) this.options.get(index)).getKey();
		}
		throw new IndexOutOfBoundsException();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Value getSelectedLabel() {
		for (int i = 0; i < size(); i++) {
			HtmlOption<Key, Value> o = this.options.get(i);
			if (o.isSelected()) {
				return o.getValue();
			}
		}
		if (size() > 0) {
			return (Value) ((HtmlOption) this.options.get(0)).getValue();
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Value getValue(int index) {
		if (isInRange(index)) {
			return (Value) ((HtmlOption) this.options.get(index)).getValue();
		}
		throw new IndexOutOfBoundsException();
	}

	@SuppressWarnings("rawtypes")
	public boolean isSelected(int index) {
		if (isInRange(index)) {
			return ((HtmlOption) this.options.get(index)).isSelected();
		}
		throw new IndexOutOfBoundsException();
	}

	public int size() {
		return this.options.size();
	}

	public String getHtml() {
		if (this.nazevVybiranePolozky != null) {
			StringBuilder sb = new StringBuilder();

			sb.append("<select name=\"");
			sb.append(this.nazevVybiranePolozky);
			sb.append("\" ");

			if (this.visibleSize > 0) {
				sb.append("size=\"");
				sb.append(this.visibleSize);
				sb.append("\" ");
			}

			if (this.disabled) {
				sb.append("disabled=\"disabled\" ");
			}

			for (Map.Entry<String, Object> e : this.attributes.entrySet()) {
				if (e.getValue() != null) {
					sb.append(String.valueOf(e.getKey()) + "=\"");
					sb.append(String.valueOf(e.getValue().toString()) + "\" ");
				}
			}
			sb.append(">\n");

			for (HtmlOption<Key, Value> ho : this.options) {
				sb.append("\t\t\t<option value=\"");
				if (ho.getKey() != null) {
					sb.append(ho.getKey());
				}
				sb.append("\" ");
				if (ho.isSelected()) {
					sb.append(" selected=\"selected\"");
				}
				sb.append(" >");
				if (ho.getValue() != null) {
					sb.append(ho.getValue());
				}
				sb.append("</option>\n");
			}
			sb.append("</select>\n");
			return sb.toString();
		}
		return "";
	}

	private boolean isInRange(int index) {
		return (index > -1 && index < size());
	}

	public List<HtmlOption<Key, Value>> getOptions() {
		return this.options;
	}

	@Deprecated
	public List<HtmlOption<Key, Value>> getOpinions() {
		return this.options;
	}

	public int getVisibleSize() {
		return this.visibleSize;
	}

	public void setVisibleSize(int visibleSize) {
		this.visibleSize = visibleSize;
	}

	public boolean isDisabled() {
		return this.disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public Object setAttribute(String attribute, Object value) {
		return this.attributes.put(attribute, value);
	}

	public Object getAttribute(String attribute) {
		return this.attributes.get(attribute);
	}

	public String toString() {
		String s = "HtmlSelect toString-------START\n";
		for (HtmlOption<Key, Value> o : this.options) {
			s = String.valueOf(s) + o.getKey() + " - " + o.getValue() + "\n";
		}
		s = String.valueOf(s) + "Selected: " + getSelected() + " - " + getSelectedLabel() + "\n";
		s = String.valueOf(s) + "HtmlSelect toString-------END\n";
		return s;
	}
}