package com.popkyss.sweetShop.setting.ltd;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;

import com.popkyss.sweetShop.setting.DatexCollections;

import net.sourceforge.stripes.tag.HtmlTagSupport;
import net.sourceforge.stripes.util.HtmlUtil;
import net.sourceforge.stripes.util.Log;

public class LongList extends HtmlTagSupport {

	private static final Log log = Log.getInstance(LongList.class);
	
	private int maxItem;
	private List<Object> value;
	private Boolean useTitle;


	@Override
	public int doStartTag() throws JspException {
		List<Object> val = value;
		boolean title = useTitle == null ? true : useTitle;
		if (!DatexCollections.isPrazdnaKolekce(val)) {
			if (val.size() > maxItem && title) {
				int poradi = 1;
				String seznam = "";
				for (Object obj : val) {
					String s = getSafeStringValue(obj);
					seznam += s;
					if (poradi != val.size()) {
						seznam += ", ";
					}
					poradi++;
				}
				setTitle(seznam);
				writeOpenTag(pageContext.getOut(), "span");
			}
		}
		
		return SKIP_BODY;
	}


	@Override
	public int doEndTag() throws JspException {
		List<Object> val = value;
		boolean title = useTitle == null ? true : useTitle;
		if (!DatexCollections.isPrazdnaKolekce(val)) {
			String seznam = "";
			if (val.size() > maxItem) {
				int poradi = 1;
				for (Object obj : val) {
					String s = getSafeStringValue(obj);
					if (poradi <= maxItem ) {
						seznam += s;
						if (poradi == maxItem) {
							seznam += "...";
							break;
						}else{
							seznam += ", ";	
						}
					}
					poradi++;
				}
				writeTextContent(seznam);
				if(title) {
					writeCloseTag(pageContext.getOut(), "span");
				}
			} else {
				seznam = DatexCollections.collectionToString(val, "");
				writeTextContent(seznam);
			}
		}
		release();

		return EVAL_PAGE;
	}


	private void writeTextContent(String text) throws JspException {
		try {
			pageContext.getOut().print(text);
		} catch (IOException e) {
			JspException jspe = new JspException("IOException encountered while writing value '" + text + "'.", e);
			log.warn(jspe.getMessage(), jspe);
			throw jspe;
		}
	}


	private String getSafeStringValue(Object o) {
		if(o == null) {
			return "null";
		}
		//hrozba vypsani napriklad ciziho scriptu
		String value = HtmlUtil.encode(o.toString());
		return value;
	}
	
	
	@Override
	public void release() {
		super.release();
		useTitle = null;
		value = null;
	}


	public List<Object> getValue() {
		return value;
	}


	public void setValue(List<Object> value) {
		this.value = value;
	}


	public void setUseTitle(Boolean useTitle) {
		this.useTitle = useTitle;
	}


	public Boolean getUseTitle() {
		return useTitle;
	}


	public int getMaxItem() {
		return maxItem;
	}


	public void setMaxItem(int maxItem) {
		this.maxItem = maxItem;
	}

}
